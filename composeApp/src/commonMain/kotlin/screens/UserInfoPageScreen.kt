package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.voc.honkai_stargazer.component.CharacterCard
import com.voc.honkai_stargazer.component.CharacterLcInfoDisplay
import components.AppDialog
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.PlayerLevel
import files.ProducedByStargazer
import files.PublicChars
import files.Res
import files.Switch
import files.UserInfoGameAchievements
import files.UserInfoGameActiveDays
import files.UserInfoGameOpenedChests
import files.UserInfoLastOnlineTime
import files.UserInfoOwnedCharacters
import files.ic_arrow_to_down
import files.ic_list_isolate_pretty
import files.phorphos_question_fill
import files.ui_icon_share
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.UserAccount
import utils.FontSizeNormal12
import utils.FontSizeNormal16
import utils.FontSizeNormalLarge24
import utils.LongStringXML
import utils.UtilTools
import utils.annotation.DoItLater
import utils.navigation.Screen
import utils.navigation.navigateLimited
import utils.showSuccessToast
import kotlin.math.min

@DoItLater("Get User Data from Database / API")
@Composable
fun UserInfoPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
) {
    val hazeState = remember { HazeState() }
    val context = LocalPlatformContext.current
    val uid = backStackEntry.query<String>("uid")!!

    val userAccount by remember { mutableStateOf(
        if(UserAccount.INSTANCE.uid == uid){
            UserAccount.INSTANCE
        } else {
            UserAccount.UIDSEARCH
        }
    ) }

    println(Json.encodeToString(userAccount))

    val lazyGridState = rememberLazyGridState()
    val isListScrolling  by remember {
        derivedStateOf {
            // whatever logic you need
            lazyGridState.canScrollBackward
        }
    }
    val isDisplayFullList = remember { mutableStateOf(false) }
    val isDisplayLcInfo = remember { mutableStateOf(false) }
    val showPopup = remember { mutableStateOf(false) }

    val helperListOrigin = userAccount.characterList.filter { it.characterStatus?.isHelper == true }

    val helperList = helperListOrigin.ifEmpty { userAccount.characterList.slice(0..min(7, userAccount.characterList.size-1)) }
    val finalCharList = userAccount.characterList.filter { it !in helperList }

    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = lazyGridState,
            modifier = Modifier.padding(
                start = Constants.SCREEN_SAVE_PADDING,
                end = Constants.SCREEN_SAVE_PADDING
            ).haze(hazeState).navigationBarsPadding()
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) { Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT)) }
            item(span = { GridItemSpan(maxLineSpan) }) { UserInfoBioUI(context, userAccount) }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                        Text(
                            UtilTools().removeStringResDoubleQuotes(Res.string.PublicChars),
                            modifier = Modifier.padding(end = 8.dp),
                            style = FontSizeNormal16(),
                            color = Color.White
                        )
                        Image(
                            painter = painterResource(Res.drawable.phorphos_question_fill),
                            contentDescription = "Button to display how it works",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(16.dp).clip(CircleShape).align(Alignment.CenterVertically).clickable { showPopup.value = !showPopup.value }
                        )
                        Spacer(Modifier.weight(1f))

                        //這裏不用刷新，是基於當前的狀態短期内不會改變
                        Text(
                            UtilTools().removeStringResDoubleQuotes(Res.string.Switch),
                            modifier = Modifier.padding(end = 8.dp).clip(CircleShape).clickable { isDisplayLcInfo.value = !isDisplayLcInfo.value },
                            style = FontSizeNormal16(),
                            color = Color.White
                        )
                    }
                }
            }

            for (character in helperList) {
                item {
                    CharacterCard(
                        character = character,
                        overrideNameComponent = { CharacterLcInfoDisplay(character) },
                        isDisplayName = !isDisplayLcInfo.value,
                        isDisplayCombatPath = false,
                        onClick = { navigator.navigateLimited("${Screen.UserCharacterPageScreen.route}?uid=${uid}&charId=${character.officialId}") }
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Image(
                    painter = painterResource(Res.drawable.ic_list_isolate_pretty),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    contentScale = ContentScale.Fit,
                    contentDescription = "List Pretty Isolator",
                )
            }

            if(isDisplayFullList.value){
                for (character in finalCharList) {
                    item {
                        CharacterCard(
                            character = character,
                            overrideNameComponent = { CharacterLcInfoDisplay(character) },
                            isDisplayName = !isDisplayLcInfo.value,
                            isDisplayCombatPath = false ,
                            onClick = { navigator.navigateLimited("${Screen.UserCharacterPageScreen.route}?uid=${uid}&charId=${character.officialId}") }
                        )
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.ic_arrow_to_down),
                        modifier = Modifier.size(24.dp).clip(CircleShape).padding(4.dp)
                            .rotate(if (isDisplayFullList.value) 180f else 0f)
                            .clickable { isDisplayFullList.value = !isDisplayFullList.value },
                        colorFilter = ColorFilter.tint(Color(0x66F3F9FF)),
                        contentDescription = "Expand / Collapse List",

                        )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) { UserInfoBioUI2(context, userAccount) }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Box {
                    Text(
                        text = UtilTools().removeStringResDoubleQuotes(Res.string.ProducedByStargazer),
                        textAlign = TextAlign.Center,
                        style = FontSizeNormal12(),
                        color = Color.White,
                        modifier = Modifier.wrapContentSize().padding(8.dp).align(Alignment.Center),
                        )
                }
            }
        }

        PageHeader(
            headerData = headerData,
            navigator = navigator,
            forwardIconId = Res.drawable.ui_icon_share,
            onForward = { /* TODO : Share Function*/ },
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL
        )

        /*

        PageHeaderAlpha(
            navController = navController,
            forwardIconId = Res.drawable.ui_icon_share,
            onForward = { /* TODO : Share Function*/ },
            hazeState = hazeState,
            isListScrolling = isListScrolling ,
        ){
            Box(Modifier
                .fillMaxSize()
            ){
                Text(
                    UtilTools().removeStringResDoubleQuotes(Res.string.UserInfoGameData),
                    modifier = Modifier
                        .background(Color(0x33FFFFFF),RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .hazeChild(
                            state = hazeState!!,
                            shape = RoundedCornerShape(16.dp),
                            style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE)
                        )
                        .padding(top = 6.dp, bottom = 6.dp, start = 12.dp, end = 12.dp)
                        .align(Alignment.Center),
                    color = Color.White,
                    style = FontSizeNormal16(),
                )
            }
        }
         */
    }

    if(showPopup.value){
        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = UtilTools().removeStringResDoubleQuotes(Res.string.PublicChars),
                hazeState = hazeState,
                components = {
                    Text(
                        LongStringXML().PublicCharDesc(),
                        style = FontSizeNormal16(),
                        color = Color(0xFF666666)
                    )
                },
                isPopupShow = showPopup
            )
        }
    }
}

@Composable
fun UserInfoBioUI(context: PlatformContext, userAccount: UserAccount) {
    Column (Modifier.fillMaxWidth()) {
        //User Avatar (With Reducing Padding's Scale)
        Box(Modifier.requiredSize(72.dp)
            .align(Alignment.CenterHorizontally)
            .background(Color(0xFFCAB89E), CircleShape)
            .clip(CircleShape)
            .border(1.dp, Color(0x66907C54), CircleShape)
            .clickable {
                if (userAccount.signature != "") {
                    //Print Nickname
                    showSuccessToast(message = userAccount.signature)
                }
            }, contentAlignment = Alignment.Center
        ) {

            val scale = if(userAccount.icon.startsWith("http")) 1.142857f else 1f
            Box(Modifier.requiredSize(72.dp * scale)) {
                // User Avatar
                AsyncImage(
                    modifier = Modifier.size(72.dp * scale),
                    model = UtilTools().newImageRequest(context,
                        UtilTools().getIconByUserAccountIconValue(userAccount.icon)
                        ),
                    imageLoader = UtilTools().newImageLoader(context),
                    contentDescription = "",
                )
            }
        }

        Text(
            userAccount.username,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
            style = FontSizeNormalLarge24(),
            color = Color.White
        )

        Text(
            text = "${userAccount.uid}·${UtilTools().removeStringResDoubleQuotes(
                userAccount.server.localeName)}",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                .clip(RoundedCornerShape(49.dp)).padding(8.dp),
            style = FontSizeNormal12(),
            color = Color.White
        )

        Row(Modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.Center) {
            val rowData = arrayListOf(
                "${userAccount.level}" to Res.string.PlayerLevel,
                //"${userAccount.ascLevel}" to Res.string.UserInfoGameWorldLevel,
                "${userAccount.unlockedCharCount}" to Res.string.UserInfoOwnedCharacters,
            )


            for ((index, data) in rowData.withIndex()){
                var textSize by remember { mutableStateOf(24.sp) }

                Column(Modifier.wrapContentHeight()
                    .onSizeChanged { size ->
                        // 根據寬度動態計算字體大小
                        textSize = TextUnit(size.width / 10f, TextUnitType.Sp) } ) {
                    Text(
                        data.first,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                        style = FontSizeNormalLarge24(),
                        fontSize = textSize,
                        color = Color.White
                    )

                    Text(
                        UtilTools().removeStringResDoubleQuotes(data.second),
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 24.dp, end = 24.dp, top = 2.dp , bottom = 2.dp),
                        style = FontSizeNormal12(),
                        color = Color.White
                    )
                }

                if (index < rowData.size - 1) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .background(Color(0x66F3F9FF))
                            .padding(top = 16.dp, bottom = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoBioUI2(context: PlatformContext, userAccount: UserAccount) {
    Row(Modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.Center) {
        val rowData = arrayListOf(
            userAccount.activeDays to Res.string.UserInfoGameActiveDays,
            userAccount.achievements to Res.string.UserInfoGameAchievements,
            userAccount.chestOpened to Res.string.UserInfoGameOpenedChests,
            //"${userAccount.mocProgress}/12" to Res.string.UserInfoGameForgottenHall,
            userAccount.lastLoginTime to Res.string.UserInfoLastOnlineTime,
        )

        for (data in rowData){
            if(data.first == 0 || data.first == 0L) { continue }
            var textSize by remember { mutableStateOf(24.sp) }
            Column(
                Modifier.wrapContentHeight()
                    .weight(1f)
                    .onSizeChanged { size ->
                        // 根據寬度動態計算字體大小
                textSize = min(TextUnit(size.width / 10f, TextUnitType.Sp), 30.sp)
            }) {
                Text(
                    //DoItLater("LastLoginTime")
                    "${data.first}",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                    style = FontSizeNormalLarge24(),
                    fontSize = textSize,
                    color = Color.White
                )

                Text(
                    UtilTools().removeStringResDoubleQuotes(data.second),
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                    style = FontSizeNormal12(),
                    color = Color.White
                )
            }
        }
    }

}

fun max(textUnit: TextUnit, sp: TextUnit): TextUnit {
    return if(textUnit > sp) textUnit else sp
}

fun min(textUnit: TextUnit, sp: TextUnit): TextUnit {
    return if(textUnit < sp) textUnit else sp
}
