/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package com.voc.honkai_stargazer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Constants.Companion.getCardBgColorByRare
import types.Relic
import utils.FontSizeNormal12
import utils.TextColorNormalDim
import utils.UtilTools

//Start to End, Top to Bottom
val RELIC_CARD_HEIGHT = 102.dp
val RELIC_CARD_WIDTH = 80.dp
val RELIC_CARD_TITLE_HEIGHT = 20.dp

@Composable
fun RelicCard(
    relic: Relic,
    level: Int? = -1,
    ascensionPhase: Int? = -1, //Rank 突破等級
    displayName: String? = "?",
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .defaultMinSize(RELIC_CARD_WIDTH, RELIC_CARD_HEIGHT)
            .clickable(
                onClick = { /* Ignoring onClick */ },
                indication = rememberRipple(),
                interactionSource = interactionSource
            )
            .clip(
                RoundedCornerShape(
                    topEnd = 15.dp,
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                )
            )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .defaultMinSize(RELIC_CARD_WIDTH, RELIC_CARD_WIDTH)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 15.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                            bottomStart = 4.dp
                        )
                    )
            ) {
                println(relic.registName+" | "+relic.officialId)
                Image(
                    bitmap = Relic.getRelicImageFromJSON(
                        if(relic.officialId!! < 300) UtilTools.ImageFolderType.RELIC_PC_ICON else UtilTools.ImageFolderType.ORMANENT_PC_ICON,
                        relic.registName!!
                    ),
                    contentDescription = "Relic Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            Brush.verticalGradient(
                                colors = getCardBgColorByRare(if(relic.rarity === null) 5 else relic.rarity!!)
                            )
                        ),
                    contentScale = ContentScale.Crop

                )
            }
            Row(
                Modifier
                    .height(RELIC_CARD_TITLE_HEIGHT)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = displayName!!,
                    textAlign = TextAlign.Center,
                    color = TextColorNormalDim,
                    fontSize = FontSizeNormal12().fontSize,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview
@Composable
fun RelicCardPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(80.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(count = 4) {
            RelicCard(
                relic = Relic(
                    officialId = 312,
                    registName = "Penacony, Land of the Dreams",
                    fileName = "312",
                ),
                displayName = "夢想之地匹諾康尼",
            )
        }
    }
}

