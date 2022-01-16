package com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.AccessLevel
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.ContentType
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.Difficulty
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun <T> List<T>.update(index: Int, item: T): List<T> =
    toMutableList().apply { this[index] = item }

fun View.isKeyboardOpen(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect);
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom;
    return keypadHeight > screenHeight * 0.15
}

fun Modifier.clearFocusOnKeyboardDismiss(isKeyboardOpen: Boolean): Modifier = composed {

    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        val focusManager = LocalFocusManager.current
        LaunchedEffect(isKeyboardOpen) {
            if (isKeyboardOpen) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

fun TutorialData.isFilteredOut(state: State): Boolean {
    // First we filter by the options in the filter menu.
    if (state.filterDifficulty != Difficulty.ANY) {
        if (this.attributes.difficulty != state.filterDifficulty.name.lowercase()) {
            return true
        }
    }

    if (state.filterContentType != ContentType.ANY) {
        if (this.attributes.content_type != state.filterContentType.name.lowercase()) {
            return true
        }
    }

    if (state.filterAccessLevel != AccessLevel.ANY) {
        if (this.attributes.free && state.filterAccessLevel == AccessLevel.PRO) {
            return true
        } else if (!this.attributes.free && state.filterAccessLevel == AccessLevel.FREE) {
            return true
        }
    }

    return false
}

fun TutorialData.convertDateToSimpleDateFormat(): Date? {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(this.attributes.released_at)
}

/**
 * This is a bit spicy. But basically so you can do the following on Modifiers:
 * .`if` (!isFixed) {
 *    clickable(onClick = { onCellSelected() })
 *   }
 *   Got it from here: https://proandroiddev.com/add-modifier-conditionally-without-sacrificing-its-fluent-api-a572cc085fb2
 */
fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }
