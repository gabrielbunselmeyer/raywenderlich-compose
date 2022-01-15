package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model

sealed class Action {

    object FetchContent : Action()

    data class KeyboardStatusChanged(val isOpen: Boolean) : Action()
    data class SearchQueryChanged(val searchQuery: String) : Action()
}

typealias Dispatcher = (Action) -> Unit