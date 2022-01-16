package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

object Animations {
    private const val filterMenuExpandingDuration = 350

    @Composable
    fun filterButtonRotation(isFilterMenuExpanded: Boolean): State<Float> =
        animateFloatAsState(
            targetValue = if (isFilterMenuExpanded) -180f else 0f,
            animationSpec = tween(
                durationMillis = filterMenuExpandingDuration
            )
        )

    @Composable
    fun filterButtonExpandingColor(isFilterMenuExpanded: Boolean): State<Color> =
        animateColorAsState(
            targetValue = if (isFilterMenuExpanded)
                MaterialTheme.colors.primary.copy(alpha = 0.8f)
            else
                MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            animationSpec = tween(
                durationMillis = filterMenuExpandingDuration,
                easing = LinearEasing
            )
        )

}
