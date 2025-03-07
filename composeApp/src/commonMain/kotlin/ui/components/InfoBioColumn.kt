package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import files.Res
import files.UserOwned
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.CombatType
import utils.app.Constants
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.annotation.DoItLater
import utils.app.pxToDp
import utils.app.removeStrQuote

@Composable
fun InfoBioColumn(
    infoJson: JsonElement,
    combatType: CombatType? = null,
    path: types.Path? = null,
    isUserOwned: Boolean = true,
    isFullEidolon: Boolean = false,
    pageSize: Pair<Dp, Dp> = Pair(getScreenSizeInfo().wDP, getScreenSizeInfo().hDP)
    ) {

    val itemName = remember { infoJson.jsonObject["name"]!!.jsonPrimitive.content }
    val itemRarity = remember { infoJson.jsonObject["rarity"]!!.jsonPrimitive.int }
    val itemLocation = remember {
        if(infoJson.jsonObject["archive"] != null && infoJson.jsonObject["archive"]!!.jsonObject["camp"] != null) {
            infoJson.jsonObject["archive"]!!.jsonObject["camp"]!!.jsonPrimitive.content
        } else {
            DoItLater("Please fullfill the case of Lightcone & Relic")
            ""
        }
    }

    Column(modifier = Modifier.size(pageSize.first, pageSize.second)) {
        Box(modifier = Modifier.weight(1f))

        Column(modifier = Modifier
            .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            .fillMaxWidth()
            .wrapContentHeight()
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = itemName,
                    style = FontSizeNormal14(),
                    fontSize = 32.sp,
                    color = Color.White,
                )

                //Database Required
                //UserOwned || FullEidolon

                if (isUserOwned || isFullEidolon) {
                    Box(
                        Modifier.padding(start = 6.dp, top = 6.dp).clip(RoundedCornerShape(34.dp))
                            .background(
                                Color(0xFFF3F9FF)
                            )
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp
                            ),
                            text = removeStrQuote(
                                if (isUserOwned) {
                                    Res.string.UserOwned
                                } else Res.string.UserOwned
                            ),
                            style = FontSizeNormal12(),
                            color = Color(0xFF393A5C),
                        )
                    }
                }
            }

            Box(Modifier.height(8.dp))

            Row {
                Row {
                    repeat(itemRarity) {
                        Image(
                            modifier = Modifier.size(28.dp,28.dp),
                            painter = painterResource(Res.drawable.ui_icon_star),
                            contentScale = ContentScale.FillHeight,
                            contentDescription = "Stars to represent Rarity"
                        )
                    }
                }
                Text(
                    text = itemLocation,
                    style = FontSizeNormal16(),
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }


            Box(Modifier.height(6.dp))

            Row(modifier = Modifier.navigationBarsPadding()) {
                if (path !== null) {
                    Image(
                        painter = painterResource(path.iconWhite),
                        modifier = Modifier.size(24.dp).padding(end = 6.dp).align(Alignment.CenterVertically),
                        contentDescription = "CombatType Icon"
                    )
                    Text(
                        text = removeStrQuote(path.resName),
                        style = FontSizeNormal16(),
                        color = Color.White,
                        textAlign = TextAlign.End,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                if (combatType !== null && path !== null) {
                    Box(modifier = Modifier.width(24.dp))
                }

                if (combatType !== null) {
                    Image(
                        painter = painterResource(combatType.iconColor),
                        modifier = Modifier.size(24.dp).padding(end = 6.dp).align(Alignment.CenterVertically),
                        contentDescription = "CombatType Icon"
                    )
                    Text(
                        text = removeStrQuote(combatType.resName),
                        style = FontSizeNormal16(),
                        color = Color.White,
                        textAlign = TextAlign.End,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

            }

        }
    }
}