package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.Action
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.Ordering
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.convertDateToSimpleDateFormat
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.isFilteredOut
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import kotlinx.coroutines.launch

@Composable
fun TutorialListScreen(state: State, dispatcher: (Action) -> Unit) {

    // Only fetch the contents when FeedScreen is composed.
    dispatcher(Action.FetchContent(isFirstFetch = true))

    // We aren't using any of the special Scaffold thingies.
    // But in a more complex application, this would have more to show.
    Scaffold {
        TutorialList(state, dispatcher)
    }
}

/**
 * The main list for showing the tutorials.
 * It's composed by a [LazyColumn] containing the filtered [TutorialData] and a header toolbar
 * displaying the [SearchBar] and [FilterButton].
 *
 * Reloading of the [LazyColumn] happens in a few different scenarios through a [LaunchedEffect],
 * so filtering and searching is always done in a background thread using Coroutines.
 *
 * Additionally, I'm using Google's [SwipeRefresh] from their Accompanist lib, which is currently
 * a release candidate for Jetpack Compose itself.
 */
@Composable
private fun TutorialList(state: State, dispatcher: (Action) -> Unit) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshingTutorialList)
    var filteredAndOrderedTutorials by remember { mutableStateOf(listOf<TutorialData>()) }
    var filteredAllTutorials = false

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
            // Lastly, we sort the data by newest or oldest.
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

            if (filteredTutorials.isEmpty()) {
                filteredAllTutorials = true
                filteredAndOrderedTutorials = emptyList()
            } else {
                filteredAndOrderedTutorials =
                    if (state.currentSortingRule == Ordering.NEWEST) {
                        filteredTutorials.sortedByDescending { it.convertDateToSimpleDateFormat() }
                    } else {
                        filteredTutorials.sortedBy { it.convertDateToSimpleDateFormat() }
                    }
                filteredAllTutorials = false
            }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
                        filteredAndOrderedTutorials = emptyList()
                        dispatcher(Action.FetchContent(isFirstFetch = false))
                    },
        refreshTriggerDistance = 180.dp
    ) {
        LazyColumn(
            contentPadding = Dimens.TutorialList.contentPadding
        ) {
            item(key = "listToolBar") {
                ListToolbar(state, filteredAndOrderedTutorials.size, dispatcher)
            }

            if (filteredAndOrderedTutorials.isEmpty() && !filteredAllTutorials) {
                items(count = 5) {
                    TutorialCard(null)
                }
            } else {
                // It's important to have a key set over here as TutorialCard has a couple of
                // stateful Composables as children. We could also hoist their state, of course.
                itemsIndexed(
                    items = filteredAndOrderedTutorials,
                    key = { _, item -> item.id }
                ) { _, item ->
                    TutorialCard(item)
                }
            }

        }
    }
}

/**
 * Simple check for string fuzzy match using the FuzzyWuzzy lib: https://mvnrepository.com/artifact/com.willowtreeapps/fuzzywuzzy-kotlin-js
 * The ratio is an arbitrary number based on testing the data.
 */
private fun String.fuzzlyMatches(stringToMatch: String): Boolean {
    val desiredRatio = 80
    return FuzzySearch.partialRatio(stringToMatch.lowercase(), this.lowercase()) >= desiredRatio
}
