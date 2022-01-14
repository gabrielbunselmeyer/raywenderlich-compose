package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Dimens
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.Shapes

@Composable
fun TutorialCard(item: TutorialData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.ContentItem.height)
            .padding(Dimens.ContentItem.padding)
            .background(Color.LightGray)
            .clip(Shapes.medium)
    ) {
        CardTitle(item)
        CardDescription(item)
    }
}

@Composable
private fun CardTitle(item: TutorialData) {
    Row(
        modifier = Modifier
            .padding(Dimens.ContentItem.titleRowPadding)
    ) {
        Column(
            modifier = Modifier.weight(0.65f)
        ) {
            Text(
                text = item.attributes.name
            )

            Text(
                text = item.attributes.technology_triple_string
            )
        }

        Image(
            painter = rememberImagePainter(item.attributes.card_artwork_url),
            contentDescription = item.attributes.description_plain_text,
            modifier = Modifier
                .size(Dimens.ContentItem.imageSize)
                .clip(Shapes.small)
                .weight(0.15f, fill = false)
        )
    }
}

@Composable
private fun CardDescription(item: TutorialData) {
    Text(text = item.attributes.description_plain_text)

    Row {
        Text(text = item.attributes.released_at)
        Text(text = item.attributes.duration.toString())
    }
}