package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

object Dimens {

    object FeedScreen {
        val contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    }

    object TutorialCard {
        val padding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 14.dp)
        val height = 160.dp
        val elevation = 20.dp

        val contentPadding = PaddingValues(all = 16.dp)
        val titleTextPadding = PaddingValues(start = 12.dp)
        val cardTitleHeight = 60.dp
        val cardImageMaxSize = 96.dp

        // We want this very specific value for the text to align with the end of
        // the thumbnail image.
        val subtitleSize = 13.dp

        val descriptionBoxPadding = PaddingValues(top = 14.dp)
        val descriptionInfoTextPadding = PaddingValues(start = 4.dp)
    }

    object TutorialAccessLevel {
        val badgeWidth = 36.dp
        val badgeHeight = 20.dp
    }
}