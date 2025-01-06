package ui.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple
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
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import files.Res
import files.SuperimposeLvl
import files.SuperimposeNotEquipped
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import utils.app.Constants.Companion.CHAR_CARD_HEIGHT
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.LOST_IMAGE_DRAWABLE
import utils.app.Constants.Companion.MATERIAL_CARD_TITLE_HEIGHT
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal12
import utils.app.TextColorNormalDim
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.app.removeStrQuote
import utils.app.replaceStrRes

@Composable
fun CharacterCard(
    character: Character,
    displayName: String? = character.displayName,
    onClick: () -> Unit = { navigatorInstance.navigateLimited(
        Screen.CharacterInfoPage.route
                + "/${character.registName!!.replace(" ","_")}"
                + "?fileName=${character.fileName}"
                + "&combatType=${character.combatType}"
                + "&path=${character.path}"
                + "&charId=${character.officialId}"

    ) }, //按下後會做甚麼
    overrideNameComponent: @Composable (() -> Unit)? = null,
    isDisplayCombatPath: Boolean = true,
    isDisplayName : Boolean = true,
    isDisplayLevel : Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    //UI of the Character Card
    //Container of the Character Card
    Box(
        modifier = Modifier
            .widthIn(CHAR_CARD_WIDTH, CHAR_CARD_WIDTH*2)
            .aspectRatio(CHAR_CARD_WIDTH/ CHAR_CARD_HEIGHT)
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
                indication = ripple(),
                interactionSource = interactionSource
            )
    ) {
        //Character Icon & Name / Level
        Column(modifier = Modifier.fillMaxSize()) {
            Box {
                AsyncImage(
                    model = Character.getCharacterImageFromFileName(
                        ImageFolder.CHAR_ICON,
                        getImageNameByRegistName(character.registName!!)
                    ),
                    contentDescription = "Character Icon",
                    modifier = Modifier
                        .widthIn(CHAR_CARD_WIDTH, CHAR_CARD_WIDTH*2)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                )


                if(character.characterStatus != null &&
                    character.characterStatus!!.characterLevel != -1 &&
                    !isDisplayLevel
                ) {
                    Text(
                        text = "Lv ${character.characterStatus!!.characterLevel}",
                        color = TextColorNormalDim,
                        style = FontSizeNormal12(),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 4.dp)
                            .background(Color(0xCC222222), RoundedCornerShape(42.dp))
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth().background(Color(0xFF222222)).height(MATERIAL_CARD_TITLE_HEIGHT),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if(!isDisplayName && overrideNameComponent != null){
                    overrideNameComponent()
                }else if (!isDisplayName && isDisplayLevel && character.characterStatus != null && character.characterStatus!!.characterLevel != -1){
                    Text(
                        text = "Lv ${character.characterStatus!!.characterLevel}",
                        textAlign = TextAlign.Center,
                        color = TextColorNormalDim,
                        fontSize = FontSizeNormal12().fontSize,
                        maxLines = 1
                    )

                }else {
                    Text(
                        text = displayName!!,
                        textAlign = TextAlign.Center,
                        color = TextColorNormalDim,
                        fontSize = FontSizeNormal12().fontSize,
                        maxLines = 1
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }

        if(isDisplayCombatPath){
            Column (modifier = Modifier.padding(4.dp)){
                Image(
                    painter = painterResource(resource = character.combatType.iconColor),
                    contentDescription = "Character Combat Type Icon",
                    modifier = Modifier
                        .requiredWidth(20.dp)
                        .requiredHeight(20.dp)
                        .background(Color(0x66000000), CircleShape)
                        .padding(2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
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

        if(character.characterStatus != null){
            Column(
                modifier = Modifier.padding(6.dp)
                    .align(Alignment.TopEnd)
                    .background(Color(0xFFF3F9FF), CircleShape)
                    .requiredSize(16.dp)
            ) {
                Text(
                    text = "${character.characterStatus!!.eidolon}",
                    color = Color(0xFF393A5C),
                    textAlign = TextAlign.Center,
                    style = FontSizeNormal12(),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
@Composable
fun CharacterLcInfoDisplay(character: Character){
    Row(Modifier.fillMaxWidth().wrapContentHeight().background(Color(0xFF413A31))) {
        Column(Modifier.weight(1f)) {
            AsyncImage(
                model =
                if(character.characterStatus != null && character.characterStatus!!.equippingLightcone != null){
                    getAssetsURLByFileName(
                        ImageFolder.LC_ICON,
                        getImageNameByRegistName(character.characterStatus!!.equippingLightcone!!.registName!!)
                    )
                } else {
                    LOST_IMAGE_DRAWABLE
                },
                contentDescription = "Lightcone Icon",
                modifier = Modifier
                    .requiredWidth(36.dp)
                    .requiredHeight(36.dp)
                    .padding(2.dp)
            )
        }
        Column(Modifier.weight(1f).align(Alignment.CenterVertically).padding(end = 4.dp)) {
            val level = character.characterStatus?.equippingLightcone?.level
            val superimposition = character.characterStatus?.equippingLightcone?.superimposition
            Text(
                text = "${if(level == null) "" else "Lv "}${level ?: removeStrQuote(
                    Res.string.SuperimposeNotEquipped)
                }",
                color = TextColorNormalDim,
                style = FontSizeNormal12(),
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            if(superimposition != null && superimposition > 0){
                Text(
                    text = removeStrQuote(Res.string.SuperimposeLvl).replaceStrRes(superimposition.toString(),1),
                    color = TextColorNormalDim,
                    style = FontSizeNormal12(),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }



        }
    }
}