package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent

object GeneralColors {
    val deepPurple = Color(0xff26144a)
    val lightBurgundy = Color(0xff89529d)
    val deepBlueish = Color(0xff484cf4)
    val shimmerColor = LightColors.textLightGray.copy(alpha = 0.15f)
}

private object LightColors {
    val primaryPurple = Color(0xff35186a)
    val variantPurple = GeneralColors.deepPurple
    val secondaryOffWhite = Color(0xfffaf9ff)
    val backgroundWhite = Color.White
    val surfaceCream = Color(0xFFECEAF3)

    val textPurpleBlackish = Color(0xff06013a)
    val textOffWhite = Color(0xfff4f2f5)
    val textLightGray = Color(0xff181529)
}

object GradientColorList {
    val forTutorialCard = listOf(
        Transparent,
        LightColors.surfaceCream,
        Color(0xffE2DBF5),
        Color(0xffDFD7F5),
        Color(0xffDCD3F5),
        Color(0xffD9CFF4),
        Color(0xffD7CCF4),
    )

    val forAccessLevelBadge = listOf(
        GeneralColors.lightBurgundy,
        GeneralColors.deepBlueish
    )
}

private val LightColorPalette = lightColors(
    primary = LightColors.primaryPurple,
    primaryVariant = LightColors.variantPurple,
    secondary = LightColors.secondaryOffWhite,
    background = LightColors.backgroundWhite,
    surface = LightColors.surfaceCream,
    onPrimary = LightColors.textOffWhite,
    onSecondary = LightColors.textPurpleBlackish,
    onBackground = LightColors.textPurpleBlackish,
    onSurface = LightColors.textLightGray,
)

@Composable
fun RayWenderlichComposeViewerTheme(
    content: @Composable () -> Unit
) {
    // Due to time constraints I'm not doing a dark theme.
    // Sacrilegious, I know!
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
