package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

object Dimens {

    object FeedScreen {
        val contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    }

    object ContentItem {
        val padding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
        val height = 200.dp
        val imageSize = 64.dp

        val titleRowPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp)
    }
}