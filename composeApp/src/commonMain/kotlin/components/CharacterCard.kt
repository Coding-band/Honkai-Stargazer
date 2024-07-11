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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Character
import types.CombatType
import types.Constants.Companion.CHAR_CARD_HEIGHT
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.Constants.Companion.getCardBgColorByRare
import types.Path
import utils.FontSizeNormal12
import utils.TextColorNormalDim
import utils.UtilTools
import utils.navigation.Screen
import utils.navigation.navControllerInstance

@Composable
fun CharacterCard(
    character: Character,
    level: Int? = -1,
    ascensionPhase: Int? = -1, //Rank 突破等級
    displayName: String? = character.displayName,
    isMultiDisplay: Boolean = false, //展示推薦隊伍
    onClick: () -> Unit = { navControllerInstance.navigate(
        Screen.CharacterInfoPage.route
                + "/${character.registName!!.replace(" ","_")}"
                + "/?fileName=${character.fileName}"
                + "&combatType=${character.combatType}"
                + "&path=${character.path}"
                + "&charId=${character.officialId}"

    ) }, //按下後會做甚麼
    isDisplayLevel: Boolean = false

) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .defaultMinSize(CHAR_CARD_WIDTH, CHAR_CARD_HEIGHT)
            .clip(
                RoundedCornerShape(
                    topEnd = 15.dp,
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                )
            )
            .background(
                Brush.verticalGradient(
                    colors = getCardBgColorByRare(character.rarity)
                )
            )
            .clickable(
                onClick = onClick,
                indication = rememberRipple(),
                interactionSource = interactionSource
            )
    ) {
        //val path = LocalContext.current.assets.open("images/character_icon/${Character.getCharacterImageNameByRegistName(character.registName!!)}.webp")
        //val painter = rememberAsyncImagePainter(model = path)
        Column(modifier = Modifier.fillMaxSize()) {

            Image(
                bitmap = Character.getCharacterImageFromFileName(
                    UtilTools.ImageFolderType.CHAR_ICON,
                    character.registName!!
                ),
                contentDescription = "Character Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Row(
                Modifier.fillMaxWidth().background(Color(0xFF222222)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if(isDisplayLevel) "Lv ${level.toString()}" else displayName!!,
                    textAlign = TextAlign.Center,
                    color = TextColorNormalDim,
                    fontSize = FontSizeNormal12().fontSize,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Column (modifier = Modifier.padding(2.dp)){
            Image(
                painter = painterResource(resource = character.combatType.iconColor),
                contentDescription = "Character Combat Type Icon",
                modifier = Modifier
                    .requiredWidth(20.dp)
                    .requiredHeight(20.dp)
                    .background(Color(0x66000000), CircleShape)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Image(
                painter = painterResource(resource = character.path.iconWhite),
                contentDescription = "Character Path Icon",
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
fun CharacterCardPreview() {
    Box {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(count = 4) {
                CharacterCard(
                    character = Character(
                        officialId = 1006,
                        registName = "Silver Wolf",
                        fileName = "silverwolf",
                        rarity = 5,
                        path = Path.Nihility,
                        combatType = CombatType.valueOf("Quantum"),
                        gender = Character.Gender.Female,
                    ),
                    displayName = "銀狼",
                )
            }
        }
    }
}

