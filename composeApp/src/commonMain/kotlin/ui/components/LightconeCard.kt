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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import files.Res
import files.SuperimposeLvl
import files.SuperimposeNotEquipped
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import types.Lightcone
import ui.navigation.LightconeInfoRoute
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import utils.app.Constants.Companion.CHAR_CARD_HEIGHT
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.LC_CARD_HEIGHT
import utils.app.Constants.Companion.LC_CARD_WIDTH
import utils.app.Constants.Companion.LOST_IMAGE_DRAWABLE
import utils.app.Constants.Companion.MATERIAL_CARD_TITLE_HEIGHT
import utils.app.Constants.Companion.RELIC_CARD_WIDTH
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal12
import utils.app.TextColorNormalDim
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.app.newImageLoader
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.replaceStrRes

@Composable
fun LightconeCard(
    lightcone: Lightcone,
    displayName: String? = lightcone.displayName,
    onClick: () -> Unit = { navigatorInstance.navigateLimited(
        LightconeInfoRoute(
            lcName = lightcone.registName!!,
            fileName = lightcone.fileName!!,
            path = lightcone.path.name
        )
    ) },
    overrideNameComponent: @Composable (() -> Unit)? = null,
    isDisplayCombatPath: Boolean = true,
    isDisplayName : Boolean = true,
    isDisplayLevel : Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .widthIn(LC_CARD_WIDTH, LC_CARD_WIDTH*2)
            .wrapContentHeight()
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
                    .widthIn(LC_CARD_WIDTH, LC_CARD_WIDTH*2)
                    .aspectRatio(LC_CARD_WIDTH/ LC_CARD_WIDTH)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 15.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                            bottomStart = 4.dp
                        )
                    ).clickable(
                        onClick = { onClick() },
                        indication = ripple(),
                        interactionSource = interactionSource
                    )
            ) {
                /*
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
                 */

                AsyncImage(
                    model = newImageRequest(
                        LocalPlatformContext.current,
                        getAssetsURLByFileName(
                            ImageFolder.LC_ICON,
                            getImageNameByRegistName(lightcone.registName!!)
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            Brush.verticalGradient(
                                colors = getCardBgColorByRare(lightcone.rarity)
                            )
                        ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Lightcone Icon"
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = lightcone.displayName!!,
                    textAlign = TextAlign.Center,
                    style = FontSizeNormal12(),
                    color = TextColorNormalDim,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(LC_CARD_WIDTH, LC_CARD_WIDTH *2).wrapContentHeight()
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
        Column(modifier = Modifier.padding(4.dp)) {
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