/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import files.AppStatusLostConnect
import files.Res
import files.phorphos_cake_fill
import getIsLandscape
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import screens.backgroundScreenHazeState
import utils.BlackAlpha20
import utils.BlackAlpha80
import utils.FontSizeNormal12
import utils.FontSizeNormal16
import utils.FontSizeNormalLarge24
import utils.TextColorNormal
import utils.UtilTools
import utils.navigation.Screen
import utils.navigation.navigateLimited
import utils.showFunctionIsDevelopingToast

class HomePageBlocks {
    val HOME_PAGE_BLOCK_WIDTH_1x1 = 80.dp
    val HOME_PAGE_BLOCK_WIDTH_2x1 = 180.dp
    val HOME_PAGE_BLOCK_HEIGHT = 90.dp

    class HomePageBlockItem(
        var itemTitle: String? = null,
        var itemTitleRId: StringResource? = null,
        var itemIconId: DrawableResource = Res.drawable.phorphos_cake_fill,
        var itemType: HomePageBlockItemType = HomePageBlockItemType.W1H1,
        var itemTopHighlight: String? = "",
        var itemTop: String? = "",
        var itemBottom: String? = "",
        var itemOnClickAction: (() -> Unit)? = null,
        var itemOnClickToNavigate: Screen? = null,
    ) {
        enum class HomePageBlockItemType(val width: Int, val height: Int) {
            W1H1(1, 1), W2H1(2, 1)
        }

        override fun toString(): String {
            return "HomePageBlockItem(itemTitle='$itemTitle', itemTitleRId='$itemTitleRId', itemIconId=$itemIconId, itemType=$itemType, itemTopHightlight='$itemTopHighlight', itemTop='$itemTop', itemBottom='$itemBottom')"
        }
    }
}

val gradient = Brush.verticalGradient(
    colors = listOf(
        BlackAlpha80,
        Color.Transparent,
    )
)

@Composable
fun HomePageBlock1x1(
    blockData: HomePageBlocks.HomePageBlockItem,
    modifier: Modifier = Modifier,
    navigator: Navigator
) {
    var isLandscape = getIsLandscape();

    OutlinedButton(
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        onClick = {
            blockData.itemOnClickAction

            if (blockData.itemOnClickToNavigate !== null) {
                println("Ok I'm Navigating to " + blockData.itemOnClickToNavigate)
                navigator.navigateLimited(blockData.itemOnClickToNavigate!!.route)
            }

            if(blockData.itemOnClickAction == null && blockData.itemOnClickToNavigate == null){
                showFunctionIsDevelopingToast()
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .background(BlackAlpha20, RoundedCornerShape(8.dp))
            .defaultMinSize(
                HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_1x1,
                HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT
            )
            .hazeChild(
                backgroundScreenHazeState,
                shape = RoundedCornerShape(8.dp),
                style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE)
            )
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color(0x66907C54))
    ) {
        Column {
            Image(
                painter = painterResource(resource = blockData.itemIconId),
                contentDescription = blockData.itemTitle,
                Modifier
                    .size(36.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text =
                if (blockData.itemTitle === null && blockData.itemTitleRId === null) {
                    UtilTools().removeStringResDoubleQuotes(Res.string.AppStatusLostConnect)
                }else if(blockData.itemTitleRId !== null){
                    UtilTools().removeStringResDoubleQuotes(blockData.itemTitleRId!!)
                }else {
                    blockData.itemTitle!!
                },
                Modifier
                    .align(Alignment.CenterHorizontally),
                color = TextColorNormal,
                style = FontSizeNormal12(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun HomePageBlock2x1(
    blockData: HomePageBlocks.HomePageBlockItem,
    modifier: Modifier = Modifier,
    navigator: Navigator
) {
    var isLandscape = getIsLandscape();

    OutlinedButton(
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        onClick = {
            blockData.itemOnClickAction;

            if (blockData.itemOnClickToNavigate !== null) {
                println("Ok I'm Navigating to " + blockData.itemOnClickToNavigate)
                navigator.navigateLimited(blockData.itemOnClickToNavigate!!.route)
            }
            if(blockData.itemOnClickAction == null && blockData.itemOnClickToNavigate == null){
                showFunctionIsDevelopingToast()
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .background(BlackAlpha20, RoundedCornerShape(8.dp))
            .defaultMinSize(
                HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_1x1,
                HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT
            )
            .hazeChild(
                backgroundScreenHazeState,
                shape = RoundedCornerShape(8.dp),
                style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE)
            )
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color(0x66907C54))
    ) {
        Row {
            //icon & name
            Column {
                Image(
                    painter = painterResource(resource = blockData.itemIconId),
                    contentDescription = blockData.itemTitle,
                    Modifier
                        .size(36.dp)
                        .align(Alignment.CenterHorizontally),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text =
                    if (blockData.itemTitle === null && blockData.itemTitleRId === null) {
                        stringResource(Res.string.AppStatusLostConnect).removePrefix("\"").removeSuffix("\"")
                    }else if(blockData.itemTitleRId !== null){
                        stringResource(blockData.itemTitleRId!!).removePrefix("\"").removeSuffix("\"")
                    }else {
                        blockData.itemTitle!!
                    },
                    Modifier
                        .align(Alignment.CenterHorizontally),
                    color = TextColorNormal,
                    style = FontSizeNormal12(),
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.width(32.dp))
            Column {
                Row(
                    Modifier.height(32.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = blockData.itemTopHighlight!!,
                        Modifier.height(IntrinsicSize.Max),
                        color = TextColorNormal,
                        style = FontSizeNormalLarge24(),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = blockData.itemTop!!,
                        Modifier.height(IntrinsicSize.Max),
                        color = TextColorNormal,
                        style = FontSizeNormal16(),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = blockData.itemBottom!!,
                    Modifier
                        .align(Alignment.CenterHorizontally),
                    color = TextColorNormal,
                    style = FontSizeNormal12(),
                )
            }
        }
    }
}