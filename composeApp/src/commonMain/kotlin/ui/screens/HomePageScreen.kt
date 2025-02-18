/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.screens

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
import androidx.compose.foundation.layout.requiredSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.voc.stargazer3.BuildKonfig
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AccountLogin
import files.Logout
import files.ModifyHomePage
import files.PlayerLevel
import files.Res
import files.Setting
import files.donate_ad_bg
import files.ic_default_avatar
import files.ic_rounded_option_btn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import types.UserAccount
import types.UserAccount.Companion.INSTANCE
import ui.components.HeaderData
import ui.components.HomePageBlock1x1
import ui.components.HomePageBlock2x1
import ui.components.HomePageBlocks
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.components.defaultHeaderData
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import utils.annotation.DoItLater
import utils.app.BlackAlpha30
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormalLarge24
import utils.app.Language
import utils.app.Preferences
import utils.app.ProgressLevelBackground
import utils.app.ProgressLevelPrimary
import utils.app.TextColorLevel
import utils.app.TextColorNormal
import utils.app.TextColorNormalDim
import utils.app.checkHasErrorLogFromLastCrash
import utils.app.getIconByUserAccountIconValue
import utils.app.newImageRequest
import utils.app.pxToDp
import utils.app.removeStrQuote
import utils.starbase.StarbaseAPI
import kotlin.math.min

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val threeDotDialogDisplay = remember { mutableStateOf(false) }
    val threeDotDialogPos = remember { mutableStateOf<Offset>(Offset(0f, 0f)) }
    val hazeState = remember { HazeState() }
    val userAccount = remember { mutableStateOf(UserAccount.INSTANCE) }
    val homeMenuBlockList = remember { mutableStateOf(Preferences().HomePageMenu.getHomePageMenuArray()) }

    checkHasErrorLogFromLastCrash()
    if(!arrayListOf("PRODUCTION", "RELEASE", "PRODUCTION_GP").contains(BuildKonfig.appProfile) ) {
        BetaVersionBox()
    }

    key(doRecompose.value){
        println("RECOMPOSED !")
        Box(modifier = Modifier
            .statusBarsPadding()
            .haze(hazeState)
        ) {
            Column {
                HomePageHeader(
                    navigator = navigator,
                    threeDotDialogPos = threeDotDialogPos,
                    threeDotDialogDisplay = threeDotDialogDisplay,
                    userAccount = userAccount
                )

                HomePageMenuScrollView(
                    navigator = navigator,
                    hazeState = hazeState,
                    homeMenuBlockList = homeMenuBlockList.value
                )
            }
        }

        ThreeDotsDialog(navigator = navigator, threeDotDialogPos = threeDotDialogPos, hazeState = hazeState, threeDotDialogDisplay = threeDotDialogDisplay, userAccount = userAccount)

    }
}

@Composable
fun UserHelpTeamIcon(
    modifier: Modifier = Modifier,
    character: Character,
    navigator: Navigator,
    uid: String
) {
    AsyncImage(
        model = newImageRequest(
            LocalPlatformContext.current,
            Character.getCharacterImageFromFileName(
                ImageFolder.CHAR_ICON, character.registName!!
            )),
        contentDescription = "Character Helper Icon",
        modifier = Modifier
            .size(30.dp)
            .background(Color(0xFFD9D9D9), CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
            .clickable {
                if (uid != "") {
                    navigator.navigateLimited("${Screen.UserCharacterPageScreen.route}?uid=${uid}&charId=${character.officialId}")
                }
            }
    )
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
fun HomePageHeader(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    threeDotDialogPos: MutableState<Offset>,
    threeDotDialogDisplay: MutableState<Boolean>,
    userAccount: MutableState<UserAccount>,
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = userAccount.value.uid,
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

                    //User Avatar (With Reducing Padding's Scale)
                    Box(Modifier.requiredSize(72.dp)
                        .background(Color(0xFFCAB89E), CircleShape)
                        .clip(CircleShape)
                        .border(1.dp, Color(0x66907C54), CircleShape)
                        .clickable {
                            if (userAccount.value.isLogin) {
                                navigator.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=${userAccount.value.uid}")
                            }
                        }, contentAlignment = Alignment.Center
                    ) {

                        val scale = if(userAccount.value.icon.startsWith("http")) 1.142857f else 1f
                        Box(Modifier.requiredSize(72.dp * scale)) {
                            // User Avatar
                            AsyncImage(
                                modifier = Modifier.size(72.dp * scale),
                                model = newImageRequest(context, getIconByUserAccountIconValue(userAccount.value.icon)),
                                contentDescription = "User Avatar",
                                error = painterResource(Res.drawable.ic_default_avatar),
                            )
                        }
                    }
                    // User Name & Helping Team
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f)
                    ) {
                        //User Name - Hmm interesting Kt
                        Text(
                            text = userAccount.value.username,
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
                                val filterResult = userAccount.value.characterList.filter { it.characterStatus != null && it.characterStatus!!.isHelper }
                                if (filterResult.isNotEmpty()) {
                                    filterResult.forEach { UserHelpTeamIcon(character = it, navigator = navigator, uid = userAccount.value.uid) }
                                }else if(userAccount.value.characterList.size > 0){
                                    for (i in 0 until min(userAccount.value.characterList.size, 6)) {
                                        UserHelpTeamIcon(
                                            character = userAccount.value.characterList[i],
                                            navigator = navigator,
                                            uid = userAccount.value.uid
                                        )
                                    }
                                }else{
                                    Box(modifier = Modifier
                                        .size(30.dp)
                                        .background(Color(0xFFD9D9D9), CircleShape)
                                        .clip(CircleShape)
                                        .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
                                        .padding()
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        Modifier
                            .padding(top = 12.dp)
                            .fillMaxHeight(), horizontalAlignment = Alignment.End
                    ) {
                        Box(modifier = Modifier.size(45.dp, 23.dp).clip(RoundedCornerShape(50.dp))) {
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
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "${removeStrQuote(Res.string.PlayerLevel)} ${userAccount.value.level}",
                            color = TextColorLevel,
                            style = FontSizeNormal14(),
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = (userAccount.value.level / 60f),
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
fun HomePageMenuScrollView(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    hazeState: HazeState,
    homeMenuBlockList: MutableList<HomePageBlocks.HomePageBlockItem>
) {
    val maxItemInRow = remember { mutableStateOf(4) }
    var reorderHomeMenuBlockList by remember { mutableStateOf(reorderHomePageBlock(homeMenuBlockList, maxItemInRow.value)) }

    LaunchedEffect(Unit){
        reorderHomeMenuBlockList = reorderHomePageBlock(homeMenuBlockList, maxItemInRow.value)
    }

    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            columns = GridCells.Adaptive(80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = reorderHomeMenuBlockList.size, span = { index ->

                maxItemInRow.value = maxLineSpan

                when (reorderHomeMenuBlockList[index].itemType) {
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> GridItemSpan(1)
                    HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> GridItemSpan(2)
                }

            }) { index ->
                val blockData: HomePageBlocks.HomePageBlockItem = reorderHomeMenuBlockList[index];
                Box(Modifier.layoutId("HomePageItemBox")){

                    when (blockData.itemType) {
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W1H1 -> HomePageBlock1x1(
                            blockData,
                            navigator = navigator
                        )
                        HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1 -> HomePageBlock2x1(
                            blockData,
                            navigator = navigator
                        )
                    }
                }
            }
        }
        BottomView()
    }
}

// Arrange items to avoid gaps
fun reorderHomePageBlock(items: MutableList<HomePageBlocks.HomePageBlockItem>, columns: Int): List<HomePageBlocks.HomePageBlockItem> {
    val grid = mutableListOf<MutableList<HomePageBlocks.HomePageBlockItem?>>()
    val arrangedItems = mutableListOf<HomePageBlocks.HomePageBlockItem>()

    for (item in items) {
        if (item.itemType == HomePageBlocks.HomePageBlockItem.HomePageBlockItemType.W2H1) {
            // Try to place a 2x1 item
            var placed = false
            for (row in grid) {
                if (row.size <= columns - 2 && row.count { it == null } >= 2) {
                    row.addAll(listOf(item, null))
                    placed = true
                    break
                }
            }
            if (!placed) {
                grid.add(mutableListOf(item, null))
            }
        } else {
            // Place a 1x1 item
            var placed = false
            for (row in grid) {
                if (row.size < columns) {
                    row.add(item)
                    placed = true
                    break
                }
            }
            if (!placed) {
                grid.add(mutableListOf(item))
            }
        }
    }

    // Flatten the grid and remove nulls
    grid.forEach { row -> arrangedItems.addAll(row.filterNotNull()) }
    return arrangedItems
}


@Composable
@DoItLater("Ads function")
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

        Text("恭喜您，看到了一條我發呆寫的廣告", color = Color.LightGray, style = FontSizeNormal16(), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun BetaVersionBox(){
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .background(Color.Black)
            .padding(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 2.dp)
            .align(Alignment.TopEnd)
            .wrapContentSize()
        ){
            Text(
                text = if(BuildKonfig.appProfile == "DEV") {"DEV"} else BuildKonfig.appVersionName,
                style = FontSizeNormal12(),
                color = TextColorNormalDim,
                modifier = Modifier.align(Alignment.Center).wrapContentSize()
            )
        }
    }
}

@Composable
fun ThreeDotsDialog(
    modifier: Modifier = Modifier,
    navigator: Navigator = navigatorInstance,
    threeDotDialogPos: MutableState<Offset>,
    hazeState: HazeState = remember { HazeState() },
    threeDotDialogDisplay: MutableState<Boolean>,
    userAccount: MutableState<UserAccount>,
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
                    .offset(y = pxToDp(threeDotDialogPos.value.y.toInt(), density = density) + 32.dp)
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {})
            ) {
                Box(
                    modifier = Modifier.padding(8.dp)
                        .background(Color(0x96000000), shape = RoundedCornerShape(10.dp)).fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        UIButton(
                            textRes = if (userAccount.value.isLogin) Res.string.Logout else Res.string.AccountLogin,
                            onClick = {
                                threeDotDialogDisplay.value = false;
                                if (userAccount.value.isLogin) {
                                    UserAccount.resetUserAccount()
                                    Preferences().Leaderboard.resetLeaderboard()
                                    userAccount.value = UserAccount.INSTANCE
                                    doRecompose.value = !doRecompose.value
                                } else {
                                    showLoginPopUp.value = true
                                }
                            },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                        Spacer(Modifier.height(10.dp))
                        UIButton(
                            textRes = Res.string.ModifyHomePage,
                            onClick = { },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                        Spacer(Modifier.height(10.dp))
                        UIButton(
                            textRes = Res.string.Setting,
                            onClick = {
                                threeDotDialogDisplay.value =
                                    false; navigator.navigateLimited(Screen.SettingScreen.route)
                            },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                    }
                }
            }
        }
    }

    HoyolabServerRemarksPopup(showPopup = showLoginPopUp, hazeState = hazeState)
}
