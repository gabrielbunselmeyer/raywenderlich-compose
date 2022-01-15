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
    val lightPeach = Color(0xffe1a054)
    val deepPeach = Color(0xffc37822)
    val lightBurgundy = Color(0xff89529d)
    val deepBlueish = Color(0xff484cf4)
}

private object LightColors {
    val primaryPurple = Color(0xff35186a)
    val variantPurple = GeneralColors.deepPurple
    val secondaryOffWhite = Color(0xfffaf9ff)
    val backgroundWhite = Color.White
    val surfaceOffWhite = Color(0xfff2f4ff)
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

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}