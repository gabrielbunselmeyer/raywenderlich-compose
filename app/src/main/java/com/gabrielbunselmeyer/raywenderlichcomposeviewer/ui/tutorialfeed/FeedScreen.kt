package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import android.os.Debug
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.*
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.convertDateToSimpleDateFormat
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.isFilteredOut
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun FeedScreen(state: State, dispatcher: (Action) -> Unit) {

    // Only fetch the contents when FeedScreen is composed.
    dispatcher(Action.FetchContent(isFirstFetch = true))

    Scaffold {
        TutorialList(state, dispatcher)
    }
}

@Composable
private fun TutorialList(state: State, dispatcher: (Action) -> Unit) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshingTutorialList)
    var filteredAndOrderedTutorials by remember { mutableStateOf(listOf<TutorialData>()) }
    val scope = rememberCoroutineScope()

    // There are quite a few scenarios we want to reload this in.
    LaunchedEffect(
        state.loadedTutorials,
        state.searchQuery,
        state.filterAccessLevel,
        state.filterContentType,
        state.filterDifficulty,
        state.currentSortingRule
    ) {
        scope.launch {
            val lowercaseStringToMatch = state.searchQuery.lowercase()

            // First we check whether or not the tutorial is filtered through the filter menu.
            // If not, we check if there's a search query active.
            // If there is, we check whether or not some of the tutorial's info matches it.
            val filteredTutorials = state.loadedTutorials.mapNotNull { tutorial ->
                if (tutorial.isFilteredOut(state)) {
                    return@mapNotNull null
                } else {
                    if (lowercaseStringToMatch.isNotBlank()) {
                        val hasMatch =
                            tutorial.attributes.name.fuzzlyMatches(lowercaseStringToMatch) ||
                                    tutorial.attributes.technology_triple_string.fuzzlyMatches(lowercaseStringToMatch) ||
                                    tutorial.attributes.contributor_string.fuzzlyMatches(lowercaseStringToMatch) ||
                                    tutorial.attributes.description_plain_text.fuzzlyMatches(lowercaseStringToMatch)

                        if (!hasMatch) return@mapNotNull null
                    }

                    return@mapNotNull tutorial
                }
            }

            filteredAndOrderedTutorials =
                if (state.currentSortingRule == Ordering.NEWEST) {
                    filteredTutorials.sortedByDescending { it.convertDateToSimpleDateFormat() }
                } else {
                    filteredTutorials.sortedBy { it.convertDateToSimpleDateFormat() }
                }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { dispatcher(Action.FetchContent(isFirstFetch = false)) },
        refreshTriggerDistance = 180.dp
    ) {
        LazyColumn(
            contentPadding = Dimens.FeedScreen.contentPadding
        ) {
            item {
                ListToolbar(state, filteredAndOrderedTutorials.size, dispatcher)
            }

            itemsIndexed(filteredAndOrderedTutorials) { _, item ->
                TutorialCard(item)
            }
        }
    }
}

private fun String.fuzzlyMatches(stringToMatch: String): Boolean {
    val desiredRatio = 80
    return FuzzySearch.partialRatio(stringToMatch.lowercase(), this.lowercase()) >= desiredRatio
}
