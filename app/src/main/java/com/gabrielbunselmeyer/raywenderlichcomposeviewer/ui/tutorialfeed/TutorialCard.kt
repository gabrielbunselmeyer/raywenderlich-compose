package com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.tutorialfeed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model.TutorialData
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.ui.theme.*
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.`if`
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.convertDateToSimpleDateFormat
import com.gabrielbunselmeyer.raywenderlichcomposeviewer.utils.update
import com.valentinilk.shimmer.shimmer
import java.text.SimpleDateFormat
import java.util.*

/**
 * This file describes the card containing a tutorial's information. A list of these cards is
 * displayed in [TutorialListScreen].
 * 
 * We accept a nullable [TutorialData] as, if null, we want to show the skeleton while loading the
 * content.
 */
@Composable
fun TutorialCard(item: TutorialData?) {
    Surface(
        elevation = Dimens.TutorialCard.elevation,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.TutorialCard.height)
            .`if`(item == null) {
                height(Dimens.TutorialCard.height + 40.dp)
            }
            .padding(Dimens.TutorialCard.padding)
            .clip(Shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .background(brush = TutorialCardGradient)
                .padding(Dimens.TutorialCard.contentPadding)
        ) {
            CardTitle(item)
            if (item != null) CardDescription(item)
        }
    }
}

@Composable
private fun CardTitle(item: TutorialData?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(Dimens.TutorialCard.cardTitleHeight)
            .`if`(item == null) {
                shimmer()
            }
    ) {
        if (item == null) {
            Box(
                modifier = Modifier
                    .size(Dimens.TutorialCard.cardImageMaxSize)
                    .clip(Shapes.small)
                    .background(color = GeneralColors.shimmerColor)
            )
        } else {
            Image(
                painter = rememberImagePainter(item.attributes.card_artwork_url),
                contentDescription = item.attributes.description_plain_text,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.primary,
                    blendMode = if (android.os.Build.VERSION.SDK_INT >= 29)
                        BlendMode.Color
                    else
                        // Couldn't find a good solution here for API < 29.
                        BlendMode.Lighten
                ),
                modifier = Modifier
                    .sizeIn(
                        maxHeight = Dimens.TutorialCard.cardImageMaxSize,
                        maxWidth = Dimens.TutorialCard.cardImageMaxSize
                    )
                    .clip(Shapes.small)
            )
        }

        if (item == null) {
            Box(
                modifier = Modifier
                    .padding(Dimens.TutorialCard.titleTextPadding)
                    .fillMaxSize()
                    .background(color = GeneralColors.shimmerColor)
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(Dimens.TutorialCard.titleTextPadding)
            ) {
                Text(
                    text = item.attributes.name,
                    style = Typography.h6,
                    fontFamily = RubikFontFamily,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // The technologies string includes versions. I.e: Kotlin 1.3, Android 5.1, Android Studio 3.6
                // We want to display it as Kotlin & Android & Android Studio and remember it for recompositions.
                // We can do rememberSaveable because in the LazyColumn, each Card has a key.
                if (item.attributes.technology_triple_string.isNotEmpty()) {
                    val technologiesSplit = rememberSaveable {
                        item.attributes.technology_triple_string
                            .split(regex = Regex(" [0-9.,]* "))
                            .let {
                                it.update(
                                    it.lastIndex,
                                    it.last().filterNot { char -> char.isDigit() || char == '.' })
                            }
                    }

                    Text(
                        text = technologiesSplit.joinToString(separator = " & "),
                        style = Typography.subtitle2.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        ),
                        overflow = TextOverflow.Visible,
                        modifier = Modifier.height(Dimens.TutorialCard.subtitleSize)
                    )
                }
            }
        }
    }
}

@Composable
private fun CardDescription(item: TutorialData) {

    val formattedDate = rememberSaveable {
        item.convertDateToSimpleDateFormat()?.let {
            SimpleDateFormat("MMM d", Locale.US).format(it)
        }.orEmpty()
    }

    val isVideoCourse = item.attributes.content_type == "collection"
    val isPaidContent = !item.attributes.free

    val descriptionText = rememberSaveable {
        "Posted $formattedDate \u2022 " +
                "${if (isVideoCourse) "Video Course" else "Article"} " +
                "(${item.attributes.duration} ${if (isVideoCourse) "minutes" else "words"})"
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(Dimens.TutorialCard.descriptionBoxPadding)
    ) {
        Text(
            text = item.attributes.description_plain_text,
            style = Typography.body2,
            textAlign = TextAlign.Justify
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isPaidContent) {
                TutorialAccessLevel()
            }

            Text(
                text = descriptionText,
                style = Typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = if (isPaidContent)
                    Modifier.padding(Dimens.TutorialCard.descriptionInfoTextPadding)
                else
                    Modifier
            )
        }
    }
}
