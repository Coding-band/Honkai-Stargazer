/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.components

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import files.AppStatusLostConnect
import files.Res
import files.phorphos_cake_fill
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import ui.navigation.Screen
import ui.navigation.hazeStateRoot
import ui.navigation.navigateLimited
import ui.screens.globalHazeBlur
import utils.annotation.DoItLater
import utils.app.BlackAlpha80
import utils.app.DefaultZIndex
import utils.app.DialogPopUpZIndex
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormalLarge24
import utils.app.GradientHomeButton
import utils.app.HazeBlurDp10
import utils.app.HazeBlurDp20
import utils.app.HazeBlurDp20Alpha
import utils.app.HomePageBtnZIndex
import utils.app.TextColorNormal
import utils.app.WhiteAlpha10
import utils.app.hazeEffectSG3
import utils.app.removeStrQuote
import utils.app.showFunctionIsDevelopingToast

class HomePageBlocks {
    val HOME_PAGE_BLOCK_WIDTH_1x1 = 80.dp
    val HOME_PAGE_BLOCK_WIDTH_2x1 = 180.dp
    val HOME_PAGE_BLOCK_HEIGHT = 90.dp

    class HomePageBlockItem(
        var itemId: String ,
        var itemTitle: String? = null,
        var itemTitleRId: StringResource? = null,
        var itemIconId: DrawableResource = Res.drawable.phorphos_cake_fill,
        var itemType: HomePageBlockItemType = HomePageBlockItemType.W1H1,

        var itemOnClickAction: ((count : MutableState<Int>) -> Unit)? = null,
        var itemOnClickToNavigate: Screen? = null,
    ) {
        var itemTopHighlight: String? = ""
        var itemTop: String? = ""
        var itemBottom: String? = ""
        var refresh: (() -> Unit)? = null
        val itemOnClickCount = mutableStateOf(0)

        enum class HomePageBlockItemType(val width: Int, val height: Int) {
            W1H1(1, 1), W2H1(2, 1)
        }

        override fun toString(): String {
            return "HomePageBlockItem(itemTitle='$itemTitle', itemTitleRId='$itemTitleRId', itemIconId=$itemIconId, itemType=$itemType, itemTopHightlight='$itemTopHighlight', itemTop='$itemTop', itemBottom='$itemBottom')"
        }

        fun onRefresh(action : (self : HomePageBlockItem) -> Unit): HomePageBlockItem {
            refresh = { action(this) }
            return this
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
@DoItLater("Check if v1.4.0 fixes this lot-of-instance-blur-laggy issue")
fun HomePageBlock1x1(
    blockData: HomePageBlocks.HomePageBlockItem,
    modifier: Modifier = Modifier,
    navigator: NavHostController
) {
    LaunchedEffect(blockData.itemOnClickCount.value){
        blockData.refresh?.invoke()
    }

    OutlinedButton(
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        onClick = {
            if (blockData.itemOnClickToNavigate !== null) {
                println("Ok I'm Navigating to " + blockData.itemOnClickToNavigate)
                navigator.navigateLimited(blockData.itemOnClickToNavigate!!.route)
            }

            if(blockData.itemOnClickAction == null && blockData.itemOnClickToNavigate == null){
                showFunctionIsDevelopingToast()
            }

            blockData.itemOnClickAction?.invoke(blockData.itemOnClickCount)
        },
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .background(GradientHomeButton, RoundedCornerShape(6.dp))
            .background(WhiteAlpha10, RoundedCornerShape(6.dp))
            .defaultMinSize(
                HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_1x1,
                HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT
            )
            //.aspectRatio(HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_1x1 / HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT)
            .fillMaxSize()
            .let {
                return@let if(globalHazeBlur.value){
                    it.hazeSource(
                        hazeStateRoot,
                        zIndex = HomePageBtnZIndex
                    ).hazeEffect(
                        state = hazeStateRoot,
                        style = HazeBlurDp20Alpha
                    )
                }else{
                    it
                }
            }
        ,
        border = BorderStroke(1.dp, Color(0x66907C54))
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
                    removeStrQuote(Res.string.AppStatusLostConnect)
                }else if(blockData.itemTitleRId !== null){
                    removeStrQuote(blockData.itemTitleRId!!)
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
@DoItLater("Check if v1.4.0 fixes this lot-of-instance-blur-laggy issue")
fun HomePageBlock2x1(
    blockData: HomePageBlocks.HomePageBlockItem,
    modifier: Modifier = Modifier,
    navigator: NavHostController
) {
    blockData.refresh?.invoke()

    LaunchedEffect(blockData.itemOnClickCount.value){
        blockData.refresh?.invoke()
    }


    OutlinedButton(
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        onClick = {
            if (blockData.itemOnClickToNavigate !== null) {
                navigator.navigateLimited(blockData.itemOnClickToNavigate!!.route)
            }
            if (blockData.itemOnClickAction == null && blockData.itemOnClickToNavigate == null) {
                showFunctionIsDevelopingToast()
            }

            blockData.itemOnClickAction?.invoke(blockData.itemOnClickCount)
        },
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .background(GradientHomeButton, RoundedCornerShape(6.dp))
            .background(WhiteAlpha10, RoundedCornerShape(6.dp))
            .defaultMinSize(
                HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_1x1,
                HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT
            ).fillMaxSize()
            .let {
                return@let if(globalHazeBlur.value){
                    it.hazeSource(
                        hazeStateRoot,
                        zIndex = HomePageBtnZIndex
                    ).hazeEffect(
                        state = hazeStateRoot,
                        style = HazeBlurDp20Alpha
                    )
                }else{
                    it
                }
            }
            //.aspectRatio(HomePageBlocks().HOME_PAGE_BLOCK_WIDTH_2x1 / HomePageBlocks().HOME_PAGE_BLOCK_HEIGHT)
            /* Check if v1.4.0 fixes this lot-of-instance-laggy issue
            .let {
                return@let if(globalHazeBlur.value){
                    it.hazeChild(
                        backgroundScreenHazeState,
                        shape = RoundedCornerShape(6.dp),
                        style = HazeStyle(
                            Color.Unspecified,
                            if (globalHazeBlur.value) 20.dp else 0.1.dp,
                            Float.MIN_VALUE
                        )
                    )
                }else{
                    it
                }
            }
             */
            ,
        border = BorderStroke(1.dp, Color(0x66907C54))
    ) {
        Row {
            //icon & name
            Spacer(Modifier.width(10.dp))
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
                        removeStrQuote(Res.string.AppStatusLostConnect)
                    } else if (blockData.itemTitleRId !== null) {
                        removeStrQuote(blockData.itemTitleRId!!)
                    } else {
                        blockData.itemTitle!!
                    },
                    Modifier
                        .align(Alignment.CenterHorizontally),
                    color = TextColorNormal,
                    style = FontSizeNormal12(),
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    Modifier.height(32.dp).wrapContentWidth().align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = TextColorNormal,
                    style = FontSizeNormal12(),
                )
            }
        }
    }
}