/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.voc.honkaistargazer.BuildKonfig
import components.HeaderData
import components.HomePageBlock1x1
import components.HomePageBlock2x1
import components.HomePageBlocks
import components.UIButton
import components.UIButtonSize
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AccountLogin
import files.Logout
import files.ModifyHomePage
import files.PlayerLevel
import files.Res
import files.Setting
import files.donate_ad_bg
import files.ic_rounded_option_btn
import files.test_char_1
import files.test_char_2
import files.test_char_3
import files.test_char_4
import files.test_char_5
import files.test_char_6
import files.test_char_7
import files.test_char_8
import org.jetbrains.compose.resources.painterResource
import types.Constants.Companion.HOME_PAGE_ITEMS
import types.UserAccount
import utils.BlackAlpha30
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormalLarge24
import utils.ProgressLevelBackground
import utils.ProgressLevelPrimary
import utils.TextColorLevel
import utils.TextColorNormal
import utils.TextColorNormalDim
import utils.UtilTools
import utils.checkHasErrorLogFromLastCrash
import utils.navigation.Screen
import utils.navigation.navControllerInstance

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, headerData: HeaderData = defaultHeaderData) {
    val threeDotDialogDisplay = remember { mutableStateOf(false) }
    val threeDotDialogPos = remember { mutableStateOf<Offset>(Offset(0f, 0f)) }
    val hazeState = remember { HazeState() }

    checkHasErrorLogFromLastCrash()
    if(!arrayListOf("PRODUCTION", "RELEASE").contains(BuildKonfig.appProfile) ){
        BetaVersionBox(BuildKonfig.appProfile)
    }
    Box(modifier = Modifier
        .statusBarsPadding()
        .haze(hazeState)
    ) {
        Column {
            HomePageHeader(navController = navController, threeDotDialogPos = threeDotDialogPos, threeDotDialogDisplay = threeDotDialogDisplay)
            HomePageMenuScrollView(navController = navController)
        }
    }

    ThreeDotsDialog(navController = navController, threeDotDialogPos = threeDotDialogPos, hazeState = hazeState, threeDotDialogDisplay = threeDotDialogDisplay)

}

@Composable
fun UserHelpTeamIcon(icon: Painter, modifier: Modifier = Modifier,navController: NavController) {
    Image(
        painter = icon, contentDescription = "", contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(30.dp)
            .background(Color(0xFFD9D9D9), CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
            .padding()
    )
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun HomePageHeader(
    modifier: Modifier = Modifier,
    navController: NavController,
    threeDotDialogPos: MutableState<Offset>,
    threeDotDialogDisplay: MutableState<Boolean>,
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = UserAccount.INSTANCE.uid,
                modifier = Modifier
                    .background(BlackAlpha30, CircleShape)
                    .padding(all = 8.dp)
                    .wrapContentSize(),
                color = TextColorNormal,
                style = FontSizeNormal14(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box {
                Row(Modifier.height(72.dp)) {
                    val context = LocalPlatformContext.current
                    val imageRequest =  remember {
                        ImageRequest.Builder(context)
                            .data(UserAccount.INSTANCE.icon)
                            .networkCachePolicy(CachePolicy.ENABLED)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build()
                    }
                    val imageLoader = remember {
                        UtilTools().newImageLoader(context = context)
                    }

                    // User Avatar
                    AsyncImage(
                        model = imageRequest,
                        imageLoader = imageLoader,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color(0xFFCAB89E), CircleShape)
                            .clip(CircleShape)
                            .border(1.dp, Color(0x66907C54), CircleShape)
                            .clickable {
                                if(UserAccount.INSTANCE.isLogin){
                                    navController.navigate(Screen.UserInfoPageScreen.route)
                                }
                            }
                    )
                    // User Name & Helping Team
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f)
                    ) {
                        //User Name - Hmm interesting Kt
                        Text(
                            text = UserAccount.INSTANCE.username,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            color = TextColorNormal,
                            style = FontSizeNormalLarge24(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        //Helping Team
                        LazyRow(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_1), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_2), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_3), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_4), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_5), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_6), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_7), navController = navController)
                                UserHelpTeamIcon(painterResource(resource = Res.drawable.test_char_8), navController = navController)
                            }
                        }
                    }
                    Column(
                        Modifier
                            .padding(top = 12.dp)
                            .fillMaxHeight(), horizontalAlignment = Alignment.End
                    ) {
                        //Three Dots
                        IconButton(
                            modifier = Modifier
                                .height(23.dp)
                                .width(45.dp)
                                .onGloballyPositioned {
                                    threeDotDialogPos.value = it.positionInRoot()
                                },
                            onClick = {
                                threeDotDialogDisplay.value = !threeDotDialogDisplay.value
                            }
                        ) {
                            Image(painterResource(resource = Res.drawable.ic_rounded_option_btn), "")
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "${UtilTools().removeStringResDoubleQuotes(Res.string.PlayerLevel)} ${UserAccount.INSTANCE.level}",
                            color = TextColorLevel,
                            style = FontSizeNormal14(),
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = (UserAccount.INSTANCE.level / 60f),
                Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                backgroundColor = ProgressLevelBackground,
                color = ProgressLevelPrimary,
            )
        }
    }
}


@Composable
fun HomePageMenuScrollView(modifier: Modifier = Modifier, navController: NavController) {
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var finalBlockData = HOME_PAGE_ITEMS;
            items(count = HOME_PAGE_ITEMS.size, span = { index ->
                if(finalBlockData[index].itemType.width > (maxCurrentLineSpan - ((index+1) % maxCurrentLineSpan))){
                    val tmpExchange = finalBlockData[index]
                    finalBlockData[index] = finalBlockData[index+1]
                    finalBlockData[index+1] = tmpExchange
                }
                when (finalBlockData[index].itemType) {
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> GridItemSpan(1)
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> GridItemSpan(2)
                }
            }) { index ->
                var blockData: HomePageBlocks.HomePageBlockItem = finalBlockData[index];
                Box(Modifier.layoutId("HomePageItemBox")){
                    when (blockData.itemType) {
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> HomePageBlock1x1(blockData,navController = navController)
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> HomePageBlock2x1(blockData,navController = navController)
                    }
                }
            }
        }
        BottomView()
    }
}

@Composable
fun BottomView(modifier: Modifier = Modifier){
    Box(modifier = Modifier
        .heightIn(64.dp, 100.dp)){
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(resource = Res.drawable.donate_ad_bg),
            contentDescription = "Donate Us",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xCCFFFFFF), BlendMode.Lighten)
        )
    }
}

@Composable
fun BetaVersionBox(appProfile : String){
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.wrapContentSize().background(Color.Black).padding(start = 2.dp, end = 2.dp, top = 12.dp, bottom = 12.dp).rotate(-90f).align(Alignment.TopEnd)){
            Text(appProfile, style = FontSizeNormal12(), color = TextColorNormalDim, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ThreeDotsDialog(
    modifier: Modifier = Modifier,
    navController: NavController = navControllerInstance,
    threeDotDialogPos: MutableState<Offset>,
    hazeState: HazeState = remember { HazeState() },
    threeDotDialogDisplay: MutableState<Boolean>
){
    val density = LocalDensity.current.density
    val showLoginPopUp = remember { mutableStateOf(false) }

    if(threeDotDialogDisplay.value){
        Row {
            Spacer(modifier = Modifier.weight(1f).fillMaxWidth().width(1.dp))
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .wrapContentHeight()
                    .offset(y = UtilTools().pxToDp(threeDotDialogPos.value.y.toInt(), density = density) + 32.dp)
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {})
            ) {
                Box(
                    modifier = Modifier.padding(8.dp)
                        .background(Color(0x96000000), shape = RoundedCornerShape(10.dp)).fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        UIButton(textRes = if(UserAccount.INSTANCE.isLogin) Res.string.Logout else Res.string.AccountLogin, onClick = { threeDotDialogDisplay.value = false; if(UserAccount.INSTANCE.isLogin) UserAccount.resetUserAccount() else showLoginPopUp.value = true }, buttonSize = UIButtonSize.SmallChoice)
                        Spacer(Modifier.height(10.dp))
                        UIButton(textRes = Res.string.ModifyHomePage, onClick = {  }, buttonSize = UIButtonSize.SmallChoice)
                        Spacer(Modifier.height(10.dp))
                        UIButton(textRes = Res.string.Setting, onClick = { threeDotDialogDisplay.value = false; navController.navigate(Screen.SettingScreen.route) }, buttonSize = UIButtonSize.SmallChoice)
                    }
                }
            }
        }
    }

    HoyolabServerRemarksPopup(showPopup = showLoginPopUp, hazeState = hazeState)
}