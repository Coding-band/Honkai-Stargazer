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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import types.Constants
import types.Constants.Companion.RELIC_CARD_HEIGHT
import types.Constants.Companion.RELIC_CARD_WIDTH
import types.Constants.Companion.getCardBgColorByRare
import types.Relic
import utils.FontSizeNormal12
import utils.TextColorNormalDim
import utils.UtilTools
import utils.navigation.Screen
import utils.navigation.navControllerInstance

@Composable
fun RelicCard(
    relic: Relic,
    level: Int? = -1,
    ascensionPhase: Int? = -1, //Rank 突破等級
    onClick: () -> Unit = { navControllerInstance.navigate(
        Screen.RelicInfoPage.route
                + "/${relic.registName!!.replace(" ","_")}"
                + "/?fileName=${relic.fileName}"

    ) }, //按下後會做甚麼
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .defaultMinSize(RELIC_CARD_WIDTH, RELIC_CARD_HEIGHT)
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
                    ).clickable(
                        onClick = { onClick() },
                        indication = rememberRipple(),
                        interactionSource = interactionSource
                    )
            ) {
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
                Modifier.fillMaxWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = relic.displayName!!,
                    textAlign = TextAlign.Center,
                    style = FontSizeNormal12(),
                    color = TextColorNormalDim,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.wrapContentWidth()
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun RelicSmallCard(
    relic: Relic,
    pieceIndex : Int = 1,
    onClick: () -> Unit = { navControllerInstance.navigate(
        Screen.RelicInfoPage.route
                + "/${relic.registName!!.replace(" ","_")}"
                + "/?fileName=${relic.fileName}"

    ) }, //按下後會做甚麼
){
    Box(
        modifier = Modifier.defaultMinSize(
            Constants.RELIC_CARD_WIDTH, Constants.RELIC_CARD_WIDTH
        ).clip(
            RoundedCornerShape(
                topEnd = 15.dp,
                topStart = 4.dp,
                bottomEnd = 4.dp,
                bottomStart = 4.dp
            )
        ).clickable(
            onClick = { },
            indication = rememberRipple(),
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {
        Image(
            bitmap = Relic.getRelicImageFromJSON(
                if (pieceIndex < 5) UtilTools.ImageFolderType.RELIC_ICON else UtilTools.ImageFolderType.ORMANENT_ICON,
                UtilTools().getImageNameByRegistName(relic.registName!!), pieceIndex
            ),
            contentDescription = "Relic Icon",
            modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(
                Brush.verticalGradient(
                    colors = getCardBgColorByRare(relic.rarity!!)
                )
            ).padding(bottom = if(relic.level > -1) 12.dp else 0.dp),
            contentScale = ContentScale.Crop
        )

        if(relic.level > -1){
            Text(
                text = relic.level.toString(),
                modifier = Modifier.align(Alignment.BottomCenter)
                    .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                    .clip(RoundedCornerShape(49.dp)).padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),

                )
        }
    }
}