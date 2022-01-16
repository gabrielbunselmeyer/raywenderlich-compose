package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui

import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.*

data class State(
    val hasLoadedTutorials: Boolean = false,
    val isRefreshingTutorialList: Boolean = false,
    val loadedTutorials: List<TutorialData> = emptyList(),

    val isKeyboardOpen: Boolean = false,
    val searchQuery: String = "",

    val isSortingPopupOpen: Boolean = false,
    val currentSortingRule: Ordering = Ordering.NEWEST,

    val filterDifficulty: Difficulty = Difficulty.ANY,
    val filterContentType: ContentType = ContentType.ANY,
    val filterAccessLevel: AccessLevel = AccessLevel.ANY
)