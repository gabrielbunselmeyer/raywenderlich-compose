package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.model

sealed class Action {

    data class FetchContent(val isFirstFetch: Boolean) : Action()

    data class KeyboardStatusChanged(val isOpen: Boolean) : Action()
    data class SearchQueryChanged(val searchQuery: String) : Action()

    data class FilterChanged(val selectedFilter: Enum<*>) : Action()

    object SortingPopupToggled : Action()
    data class SortingOrderSelected(val sortingRule: Ordering) : Action()
}

typealias Dispatcher = (Action) -> Unit