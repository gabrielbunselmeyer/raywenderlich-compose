package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

val TutorialCardGradient = Brush.linearGradient(
    colors = GradientColorList.forTutorialCard,
    start = Offset(100f, 50f)
)

val TutorialAccessLevelGradient = Brush.linearGradient(
    colors = GradientColorList.forAccessLevelBadge
)