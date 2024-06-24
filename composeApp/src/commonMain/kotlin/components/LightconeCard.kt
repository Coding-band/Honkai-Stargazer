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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Constants.Companion.getCardBgColorByRare
import types.Lightcone
import types.Path
import utils.TextColorNormalDim
import utils.UtilTools

//Start to End, Top to Bottom
val LC_CARD_HEIGHT = 120.dp
val LC_CARD_WIDTH = 80.dp
val LC_CARD_TITLE_HEIGHT = 20.dp

@Composable
fun LightconeCard(
    lightcone: Lightcone,
    level: Int? = -1,
    ascensionPhase: Int? = -1, //Rank 突破等級
    displayName: String? = "?",
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .defaultMinSize(LC_CARD_WIDTH, LC_CARD_HEIGHT)
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
                    .defaultMinSize(LC_CARD_WIDTH, LC_CARD_WIDTH)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 15.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                            bottomStart = 4.dp
                        )
                    ).clickable(
                        onClick = { /* Ignoring onClick */ },
                        indication = rememberRipple(),
                        interactionSource = interactionSource
                    )
            ) {
                Image(
                    bitmap = Lightcone.getLightconeImageFromJSON(
                        UtilTools.ImageFolderType.LC_ICON,
                        lightcone.registName!!
                    ),
                    contentDescription = "Lightcone Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            Brush.verticalGradient(
                                colors = getCardBgColorByRare(lightcone.rarity)
                            )
                        ),
                    contentScale = ContentScale.Crop

                )
            }
            Row(
                Modifier
                    .height(LC_CARD_TITLE_HEIGHT)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = displayName!!,
                    textAlign = TextAlign.Center,
                    color = TextColorNormalDim,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Column(modifier = Modifier.padding(2.dp)) {
            Image(
                painter = painterResource(resource = lightcone.path.iconWhite),
                contentDescription = "Lightcone Icon",
                modifier = Modifier
                    .requiredWidth(20.dp)
                    .requiredHeight(20.dp)
                    .background(Color(0x66000000), CircleShape)
                    .padding(2.dp)
            )
        }
    }
}

@Preview
@Composable
fun LightconeCardPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(80.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(count = 4) {
            LightconeCard(
                lightcone = Lightcone(
                    officialId = 21018,
                    registName = "Dance! Dance! Dance!",
                    fileName = "21018",
                    rarity = 4,
                    path = Path.Harmony,
                ),
                displayName = "舞！舞！舞！",
            )
        }
    }
}

