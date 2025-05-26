package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.ActionOrderAddItem
import files.ActionOrderImportedCharData
import files.ActionOrderItemChosen
import files.Res
import files.phorphos_check_regular
import files.ui_icon_back
import files.ui_icon_close
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import types.Attribute
import types.Character
import types.HsrProperties
import types.UserAccount
import ui.components.BackIcon
import ui.components.CharacterCard
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.navigation.ActionOrderSimulatorRoute
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.annotation.DoItLater
import utils.app.Constants
import utils.app.Constants.Companion.CHAR_CARD_HEIGHT
import utils.app.Constants.Companion.CHAR_CARD_TITLE_HEIGHT
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormalLarge24
import utils.app.Preferences
import utils.app.SG3VerticalScrollbar
import utils.app.TextColorNormalDim
import utils.app.rememberMutableStateListJsonOf
import utils.app.removeStrQuote
import utils.app.replaceStrRes
import utils.calculator.TeamListItem
import utils.calculator.TeammateItem

val TEST_LIST = arrayListOf(
    TeamListItem(
        uid = "900033852",
        teamBuildUnix = 1719789540000L,
        teamName = "再見，Stargazer 2；你好，Stargazer 3！\nBye Stargazer 2 and Hi Stargazer 3!",
        teamDataList = arrayListOf(
            TeammateItem(Character.getCharacterItemFromJSON("1310"), 78, 1.0f, 120,100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("8006"), 78, 1.0f, 120,106.0f, 25.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1303"), 78, 1.0f, 120,112.0f, 25.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1217"), 78, 1.0f, 120,108.0f, 25.0f),
        )
    ),

    TeamListItem(
        uid = "900033852",
        teamBuildUnix = 1739937600000L,
        teamName = "來測一下？\n黑塔女士舉世無雙！\n黑塔女士聰明絕頂！\n黑塔女士沉魚落雁！",
        teamDataList = arrayListOf(
            TeammateItem(Character.getCharacterItemFromJSON("1401"), 2, 1.0f, 120,100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1013"), 19, 1.0f, 120,100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("8006"), 3, 1.0f, 120,100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1303"), 5, 1.0f, 120,100.0f, 34.0f),
        )
    )
)

lateinit var actionOrderTeamList : SnapshotStateList<TeamListItem>

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun initActionOrderTeamList(){
    actionOrderTeamList  = rememberMutableStateListJsonOf<TeamListItem>()

    actionOrderTeamList.addAll(
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).async {
                return@async Preferences().ActionOrder.getActionOrderList()
            }
            job.await()
            job.getCompleted()
        }
    )
}

@DoItLater("Allow user to Export and Import TeamList")
@Composable
fun ActionOrderListPageScreen(
    navigator: NavHostController,
    hazeState: HazeState,
    pageHeader: MutableState<@Composable () -> Unit>,
) {
    val isInit = remember { mutableStateOf(false) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val localCharList = rememberSaveable { mutableStateOf<ArrayList<Character>>(arrayListOf()) }

    //Just Testing
    LaunchedEffect(Unit){
        if(!isInit.value){
            localCharList.value.addAll(UserAccount.INSTANCE.characterList)
            charList.value.forEach { char ->
                if(UserAccount.INSTANCE.characterList.none { it.officialId == char.officialId }){
                    localCharList.value.add(char)
                }
            }
            isInit.value = true
        }
    }

    val listState = rememberLazyListState()

    pageHeader.value = {
        PageHeader(
            navigator = navigator,
            headerData = Screen.ActionOrderListPageScreen.headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.phorphos_check_regular,
            onForward = {
                //多選功能
            },
            listState = listState
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier
            .padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
            .hazeSource(state = hazeState, zIndex = DefaultZIndex),
            listState
            //.haze(state = hazeState)
        ) {
            item {
                Spacer(Modifier
                    .statusBarsPadding()
                    .height(PAGE_HEADER_HEIGHT + 12.dp)
                )
            }
            items(actionOrderTeamList.size) { index ->
                TeamListItemCard(actionOrderTeamList[index], index, navigator)
                if(index < actionOrderTeamList.size - 1){
                    Spacer(Modifier.height(12.dp))
                }
            }
            item {
                Spacer(Modifier.navigationBarsPadding().height(64.dp))
            }

        }

        //Button for 添加
        UIButton(
            textRes = Res.string.ActionOrderAddItem,
            buttonSize = UIButtonSize.Normal,
            modifierTmp = Modifier.navigationBarsPadding().align(Alignment.BottomCenter).padding(bottom = 16.dp).wrapContentWidth().widthIn(100.dp, 200.dp).wrapContentHeight(),
            onClick = {
                isPopupOpen.value = true
            }
        )

        //I want the animation looks like a popup pull up from the bottom, with acceration and deceleration
        AnimatedVisibility(
            isPopupOpen.value,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
            modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).fillMaxHeight().align(Alignment.BottomCenter)
        ){
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                TeamSelectPopup(localCharList, isPopupOpen = isPopupOpen) {teamDataList ->
                    val ret = arrayListOf<TeammateItem>()
                    ret.addAll(teamDataList)
                    actionOrderTeamList.add(TeamListItem(teamDataList = ret))
                    isPopupOpen.value = false
                    Preferences().ActionOrder.setActionOrderList()
                }
            }
        }

        SG3VerticalScrollbar(listState = listState)
    }


}

@Composable
fun TeamSelectPopup(
    localCharList: MutableState<ArrayList<Character>>,
    isPopupOpen: MutableState<Boolean>,
    teamDataList: SnapshotStateList<TeammateItem> = remember { mutableStateListOf() },
    finishAction: (teamDataListTmp : SnapshotStateList<TeammateItem>) -> Unit
){
    //UI Part
    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()){
        val scrollableState = rememberScrollableState { 0f }
        Column(modifier = Modifier.scrollable(
            state = scrollableState, orientation = Orientation.Horizontal
        )) {
            //Padding for Status Bar
            Spacer(Modifier
                .height(0.dp)
                .statusBarsPadding()
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
                .background(Color(0xFF222222))
                .padding(16.dp),
            ) {
                //Exit & Confirm Button
                Row {
                    //Exit Button
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(shape = CircleShape)
                            .background(Color(0x33FFFFFF))
                            .clickable {
                                isPopupOpen.value = false
                            }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ui_icon_close),
                            contentDescription = "Exit Without Saving",
                            modifier = Modifier.size(32.dp).align(Alignment.Center),
                            colorFilter = ColorFilter.tint(Color.White),
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(shape = CircleShape)
                            .background(Color(0x33FFFFFF))
                            .clickable {
                                finishAction.invoke(teamDataList)
                            }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ui_icon_back),
                            contentDescription = "Saving Button",
                            modifier = Modifier.size(32.dp).align(Alignment.Center).rotate(180f),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                //Select Teammates
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ){
                    //Current Have Characters
                    items(teamDataList) {
                        CharacterCard(it.character, isDisplayLevel = true, onClick = {
                            //Remove this character from teamList
                            teamDataList.remove(it)

                        })
                    }

                    //1,2,3,4
                    for (i in teamDataList.size until 4){
                        item {
                            Box(
                                modifier = Modifier
                                    .widthIn(CHAR_CARD_WIDTH, CHAR_CARD_WIDTH *2)
                                    .aspectRatio(CHAR_CARD_WIDTH / CHAR_CARD_HEIGHT)
                                    .clip(
                                        RoundedCornerShape(
                                            topEnd = 15.dp,
                                            topStart = 4.dp,
                                            bottomEnd = 4.dp,
                                            bottomStart = 4.dp
                                        )
                                    )
                                    .background(Color(0xFF666666))
                                    .clickable {  }
                            ){
                                Text(
                                    text = "${i + 1}",
                                    style = FontSizeNormalLarge24(),
                                    color = Color(0xCCFFFFFF),
                                    modifier = Modifier.align(Alignment.Center).wrapContentSize()
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                if(UserAccount.INSTANCE.uid !== "000000000"){
                    Text(
                        text = removeStrQuote(Res.string.ActionOrderImportedCharData).replaceStrRes("${UserAccount.INSTANCE.username} (${UserAccount.INSTANCE.uid})"),
                        style = FontSizeNormal16(),
                        color = Color(0xCCFFFFFF),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(16.dp))
                }
                //Owned Characters List

                val lazyGridState = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = lazyGridState,
                    modifier = Modifier.padding(
                        start = SCREEN_SAVE_PADDING,
                        end = SCREEN_SAVE_PADDING
                    )
                ){
                    items(localCharList.value){ it ->
                        CharacterCard(
                            isDisplayLevel = false,
                            character = it,
                            onClick = {
                                if(teamDataList.size < 4 && teamDataList.none { itTeam -> itTeam.character.officialId == it.officialId }){
                                    teamDataList.add(
                                        TeammateItem(
                                            character = it,
                                            level = it.characterStatus?.characterLevel ?: 1,
                                            energyMax = it.characterAttrData?.energy ?: 100,
                                            energyRechargeRate = getSpecificAttrFromChar(it, Attribute.ATTR_SP_RATE)?.valueFinal ?: 1f,
                                            speedBase = getSpecificAttrFromChar(it, Attribute.ATTR_SPD)?.valueFinal ?: it.characterAttrData?.spd ?: 0f,
                                            speedRate = getSpecificAttrFromChar(it, Attribute.ATTR_SPD)?.valueAdd ?: 0f
                                        )
                                    )
                                }
                            },
                            overrideNameComponent = if(teamDataList.filter { itTeam -> itTeam.character.officialId == it.officialId }.isEmpty()) {
                                null
                            } else {
                                {
                                    Text(
                                        text = removeStrQuote(Res.string.ActionOrderItemChosen),
                                        textAlign = TextAlign.Center,
                                        color = TextColorNormalDim,
                                        fontSize = FontSizeNormal12().fontSize,
                                        lineHeight = CHAR_CARD_TITLE_HEIGHT.value.sp,
                                        maxLines = 1
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

fun getSpecificAttrFromChar(char: Character, attr: Attribute) : HsrProperties? {
    return char.characterStatus?.characterProperties?.find { it.attributeExchange.attribute == attr }
}

@DoItLater("Pack this style's card as a Common Component")
@Composable
fun TeamListItemCard(
    teamListItem: TeamListItem,
    index: Int,
    navigator: NavHostController
) {
    val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy.MM.dd") }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clickable {
            navigator.navigateLimited(ActionOrderSimulatorRoute(index))
        }
    ) {
        //Content
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp)) {
            Text(
                text = teamListItem.teamName,
                style = FontSizeNormal16(),
                color = Color(0xFF222222)
            )

            Text(
                text = dateFormat.format(Instant.fromEpochMilliseconds(teamListItem.teamBuildUnix).toLocalDateTime(TimeZone.currentSystemDefault())),
                style = FontSizeNormal14(),
                color = Color(0x99000000)
            )

            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.5.dp).background(Color(0x1A000000)))
            Spacer(Modifier.height(8.dp))

            //Team Character
            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()){
                LazyRow(modifier = Modifier
                    .wrapContentWidth()
                    .height(Constants.CHAR_CARD_HEIGHT),

                ) {
                    item { Spacer(Modifier.height(12.dp)) }
                    items(teamListItem.teamDataList.size) { index ->
                        Box(modifier = Modifier.size(CHAR_CARD_WIDTH, CHAR_CARD_HEIGHT)) {
                            CharacterCard(character = teamListItem.teamDataList[index].character)
                        }
                        if (index < teamListItem.teamDataList.size - 1) {
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    }
}