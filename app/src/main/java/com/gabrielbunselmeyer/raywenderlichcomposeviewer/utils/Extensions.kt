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
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.State

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
