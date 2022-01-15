package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import android.view.ViewTreeObserver
import android.widget.SearchView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.R
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.Action
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.MainViewModel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Shapes
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.clearFocusOnKeyboardDismiss
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.isKeyboardOpen
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.ToolbarTextFont
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Typography

@Composable
fun ListToolbar(state: State, filteredCardNumber: Int, dispatcher: (Action) -> Unit) {
    Column(
        modifier = Modifier
            .padding(Dimens.ListToolbar.toolbarPadding)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            SearchBar(
                state = state,
                dispatcherCallback = { dispatcher(it) }
            )

            FilterMenu()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Dimens.ListToolbar.bottomTextPadding)
        ) {
            Text(
                text = "$filteredCardNumber Tutorials",
                style = ToolbarTextFont
            )

            Text(
                text = "\u2022",
                style = ToolbarTextFont,
                modifier = Modifier.padding(Dimens.ListToolbar.bottomTextDividerPadding)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "Icon for selecting the sorting order.",
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.70f),
                    modifier = Modifier
                        .size(Dimens.ListToolbar.sortIconSize)
                        .padding(Dimens.ListToolbar.sortIconPadding)
                )

                Text(
                    text = "Newest",
                    style = ToolbarTextFont
                )
            }
        }
    }
}

@Composable
private fun FilterMenu() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Icon for opening the tutorial list filter menu.",
            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.50f),
            modifier = Modifier.size(Dimens.ListToolbar.filterIconSize)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
    state: State,
    dispatcherCallback: (Action) -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        var textFieldValue by remember { mutableStateOf(TextFieldValue(state.searchQuery)) }

        // Here we listen for keyboard opening and closing, and dispatch an update to the
        // relevant state in the ViewModel. It's used to clear focus from the TextField whenever
        // the keyboard is closed.
        val view =  LocalView.current
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            dispatcherCallback(Action.KeyboardStatusChanged(view.isKeyboardOpen()))
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = FocusRequester()

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
            leadingIcon = { Icon(Icons.Filled.Search, "Search icon inside the search text field.") },
            modifier = Modifier
                .fillMaxWidth(Dimens.ListToolbar.searchTextFieldWidth)
                .focusRequester(focusRequester)
                .clearFocusOnKeyboardDismiss(state.isKeyboardOpen)
        )
    }
}
