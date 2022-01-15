package com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun <T> List<T>.update(index: Int, item: T): List<T> =
    toMutableList().apply { this[index] = item }