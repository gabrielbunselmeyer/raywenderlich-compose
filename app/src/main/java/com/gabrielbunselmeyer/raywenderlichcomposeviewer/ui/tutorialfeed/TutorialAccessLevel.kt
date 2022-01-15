package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.GeneralColors
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.TutorialAccessLevelFont
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.TutorialAccessLevelGradient

@Composable
fun TutorialAccessLevel(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
    ) {
        val badgeModifier = Modifier
            .size(
                Dimens.TutorialAccessLevel.badgeWidth,
                Dimens.TutorialAccessLevel.badgeHeight
            )
            .clip(RoundedCornerShape(16.dp))
            .border(
                border = BorderStroke(1.dp, GeneralColors.lightBurgundy.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(16.dp)
            )

        Box(
            contentAlignment = Alignment.Center,
            modifier = badgeModifier.background(TutorialAccessLevelGradient)
        ) {
            Text(
                text = "PRO",
                style = TutorialAccessLevelFont.copy(color = MaterialTheme.colors.secondary)
            )
        }
    }

}