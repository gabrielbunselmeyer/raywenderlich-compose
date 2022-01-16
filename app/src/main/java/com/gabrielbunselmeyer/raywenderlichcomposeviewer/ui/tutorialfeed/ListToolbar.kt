package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.R
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.*
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.*
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens.TutorialListToolbar.filterMenuItemPadding
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens.TutorialListToolbar.filterSwitchPadding
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.`if`
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.clearFocusOnKeyboardDismiss
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.isKeyboardOpen

@Composable
fun ListToolbar(
    state: State,
    filteredCardNumber: Int,
    dispatcher: (Action) -> Unit
) {

    var isFilterMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(Dimens.TutorialListToolbar.toolbarPadding)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            SearchBar(
                state = state,
                dispatcherCallback = { dispatcher(it) }
            )

            FilterButton(isFilterMenuExpanded) { isFilterMenuExpanded = !isFilterMenuExpanded }
        }

        AnimatedVisibility(
            visible = isFilterMenuExpanded,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = Shapes.medium)
        ) {
            FilterMenu(state) { dispatcher(it) }
        }

        BottomInfoText(
            filteredCardNumber = filteredCardNumber,
            isSortingPopupOpen = state.isSortingPopupOpen,
            currentSortingRule = state.currentSortingRule,
            sortingButtonToggled = { dispatcher(Action.SortingPopupToggled) },
            sortingOrderSelected = { dispatcher(Action.SortingOrderSelected(it)) }
        )
    }
}

@Composable
private fun FilterMenu(
    state: State,
    dispatcher: (Action) -> Unit
) {
    Row {
        Column(
            modifier = Modifier.padding(Dimens.TutorialListToolbar.filterMenuPadding)
        ) {
            FilterSwitch(
                title = "Content Type",
                currentSelection = state.filterContentType.displayName,
                toggleStates = ContentType.values().map { it.displayName }
            ) {
                dispatcher(
                    Action.FilterChanged(
                        ContentType.valueOf(if (it == "Video") "Collection".uppercase() else it.uppercase())
                    )
                )
            }

            FilterSwitch(
                title = "Difficulty",
                currentSelection = state.filterDifficulty.displayName,
                toggleStates = Difficulty.values().map { it.displayName },
            ) { dispatcher(Action.FilterChanged(Difficulty.valueOf(it.uppercase()))) }

            FilterSwitch(
                title = "Access Level",
                currentSelection = state.filterAccessLevel.displayName,
                toggleStates = AccessLevel.values().map { it.displayName },
            ) { dispatcher(Action.FilterChanged(AccessLevel.valueOf(it.uppercase()))) }
        }
    }

}

@Composable
private fun FilterSwitch(
    title: String,
    currentSelection: String,
    toggleStates: List<String>,
    dispatchFilterChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(filterMenuItemPadding)
    ) {
        Text(
            text = title,
            style = Typography.subtitle2.copy(
                letterSpacing = 0.36.sp,
                fontWeight = FontWeight.W400
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(CenterVertically)
        )

        Row(
            modifier = Modifier
                .padding(filterSwitchPadding)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            toggleStates.forEachIndexed { _, toggleState ->
                val isSelected = currentSelection.lowercase() == toggleState.lowercase()

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .widthIn(min = 60.dp)
                        .heightIn(min = 30.dp)
                        .toggleable(
                            value = isSelected,
                            enabled = true,
                            onValueChange = {
                                if (it) dispatchFilterChange(toggleState)
                            }
                        )
                        .`if`(isSelected) { background(TutorialAccessLevelGradient) }
                ) {
                    Text(
                        text = toggleState,
                        style = Typography.body2,
                        color = if (isSelected)
                            MaterialTheme.colors.onPrimary
                        else
                            MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterButton(
    isMenuExpanded: Boolean,
    modifier: Modifier = Modifier,
    buttonClick: () -> Unit
) {
    val filterIconColorAnimation by Animations.filterButtonExpandingColor(isMenuExpanded)
    val filterIconRotation: Float by Animations.filterButtonRotation(isMenuExpanded)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max)
            .clickable { buttonClick() }
    ) {
        Icon(
            painter = rememberImagePainter(data =  R.drawable.ic_filter),
            contentDescription = "Icon for opening the tutorial list filter menu.",
            tint = filterIconColorAnimation,
            modifier = Modifier
                .size(Dimens.TutorialListToolbar.filterIconSize)
                .rotate(filterIconRotation)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
    state: State,
    dispatcherCallback: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        var textFieldValue by remember { mutableStateOf(TextFieldValue(state.searchQuery)) }

        val view = LocalView.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = FocusRequester()

        LaunchedEffect(Unit) {
            // Here we listen for keyboard opening and closing, and dispatch an update to the
            // relevant state in the ViewModel. It's used to clear focus from the TextField whenever
            // the keyboard is closed.
            val viewTreeObserver = view.viewTreeObserver
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                dispatcherCallback(Action.KeyboardStatusChanged(view.isKeyboardOpen()))
            }
            viewTreeObserver.addOnGlobalLayoutListener(listener)
        }

        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                dispatcherCallback(Action.SearchQueryChanged(it.text))
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text("Search...") },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusRequester.freeFocus()
                }
            ),
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    "Search icon inside the search text field."
                )
            },
            modifier = Modifier
                .fillMaxWidth(Dimens.TutorialListToolbar.searchTextFieldWidth)
                .focusRequester(focusRequester)
                .clearFocusOnKeyboardDismiss(state.isKeyboardOpen)
        )
    }
}

@Composable
private fun BottomInfoText(
    filteredCardNumber: Int,
    isSortingPopupOpen: Boolean,
    currentSortingRule: Ordering,
    sortingButtonToggled: () -> Unit,
    sortingOrderSelected: (Ordering) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(Dimens.TutorialListToolbar.bottomTextPadding)
    ) {
        Text(
            text = "$filteredCardNumber Tutorials",
            style = ToolbarTextFont
        )

        Text(
            text = "\u2022",
            style = ToolbarTextFont,
            modifier = Modifier.padding(Dimens.TutorialListToolbar.bottomTextDividerPadding)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                sortingButtonToggled()
            }
        ) {
            Icon(
                painter = rememberImagePainter(data = R.drawable.ic_sort),
                contentDescription = "Icon for selecting the sorting order.",
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.70f),
                modifier = Modifier
                    .size(Dimens.TutorialListToolbar.sortIconSize)
                    .padding(Dimens.TutorialListToolbar.sortIconPadding)
            )

            Text(
                text = currentSortingRule.displayName,
                style = ToolbarTextFont
            )

            OrderingDropdown(
                isExpanded = isSortingPopupOpen,
                dismissRequested = { sortingButtonToggled() },
                orderSelected = { sortingOrderSelected(Ordering.valueOf(it.uppercase())) }
            )
        }
    }
}

@Composable
private fun OrderingDropdown(
    isExpanded: Boolean,
    dismissRequested: () -> Unit,
    orderSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { dismissRequested() }
    ) {
        Ordering.values().forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(
                        width = Dimens.TutorialListToolbar.orderingDropdownWidth,
                        height = Dimens.TutorialListToolbar.orderingDropdownHeight
                    )
                    .clickable {
                        orderSelected(it.displayName)
                        dismissRequested()
                    }
            ) {
                Text(
                    text = it.displayName,
                    style = Typography.button,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
