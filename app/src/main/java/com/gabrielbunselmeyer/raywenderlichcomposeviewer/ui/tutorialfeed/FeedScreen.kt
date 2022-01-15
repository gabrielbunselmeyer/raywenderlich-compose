package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import android.os.Debug
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.text.toLowerCase
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.Action
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
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
    var filteredTutorials by remember { mutableStateOf(listOf<TutorialData>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.loadedTutorials, state.searchQuery) {
        scope.launch {
            val lowercaseStringToMatch = state.searchQuery.lowercase()

            filteredTutorials =
                if (lowercaseStringToMatch.isBlank()) {
                    state.loadedTutorials
                } else {
                    state.loadedTutorials.mapNotNull { tutorial ->
                        val hasMatch =
                            tutorial.attributes.name.fuzzlyMatches(lowercaseStringToMatch) ||
                            tutorial.attributes.technology_triple_string.fuzzlyMatches(lowercaseStringToMatch) ||
                            tutorial.attributes.contributor_string.fuzzlyMatches(lowercaseStringToMatch) ||
                            tutorial.attributes.description_plain_text.fuzzlyMatches(lowercaseStringToMatch)

                        if (hasMatch) tutorial
                        else null
                    }
                }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { dispatcher(Action.FetchContent(isFirstFetch = false)) }
    ) {
        LazyColumn(
            contentPadding = Dimens.FeedScreen.contentPadding
        ) {
            item {
                ListToolbar(state, filteredTutorials.size, dispatcher)
            }

            itemsIndexed(filteredTutorials) { _, item ->
                TutorialCard(item)
            }
        }
    }
}

private fun String.fuzzlyMatches(stringToMatch: String): Boolean {
    val desiredRatio = 80
    return FuzzySearch.partialRatio(stringToMatch.lowercase(), this.lowercase()) >= desiredRatio
}
