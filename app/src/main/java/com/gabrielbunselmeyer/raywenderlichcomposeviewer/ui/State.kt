package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui

import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData

data class State(
    val hasLoadedTutorials: Boolean = false,
    val loadedTutorials: List<TutorialData> = emptyList()
)