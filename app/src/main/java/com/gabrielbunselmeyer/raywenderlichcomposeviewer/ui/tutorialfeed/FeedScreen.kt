package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.MainViewModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens

@Composable
fun FeedScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState(State())

    // Maybe put this in MainActivity? Or maybe in the ViewModel init.
    viewModel.fetchContent()

    Scaffold(
        topBar = { SearchBar() }
    ) {
        TutorialList(state)
    }
}

@Composable
private fun TutorialList(state: State) {
    LazyColumn(
        contentPadding = Dimens.FeedScreen.contentPadding
    ) {
        itemsIndexed(state.loadedTutorials) { _, item ->
            TutorialCard(item)
        }
    }
}