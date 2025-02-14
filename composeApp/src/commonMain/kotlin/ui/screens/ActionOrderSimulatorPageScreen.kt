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
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import files.ActionOrderEnemySpeedExtHigh
import files.ActionOrderEnemySpeedExtSlow
import files.ActionOrderEnemySpeedHigh
import files.ActionOrderEnemySpeedMid
import files.ActionOrderEnemySpeedSlow
import files.ActionOrderEnemySpeedTitle
import files.ActionOrderInitSkillPoint
import files.ActionOrderMaxSkillPoint
import files.ActionOrderModify
import files.ActionOrderSimulatorActionTimes
import files.ActionOrderSimulatorActionValue
import files.ActionOrderSimulatorCharEnergy
import files.ActionOrderSimulatorSkillPoint
import files.Character
import files.ModifyHomePage
import files.Res
import files.ui_icon_back
import files.ui_icon_info
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import types.UserAccount
import ui.components.CharacterCard
import ui.components.HeaderData
import ui.components.defaultHeaderData
import utils.annotation.VersionUpdateCheck
import utils.app.Constants
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.Constants.Companion.SIMULATOR_LEFT_STATIC_ROW_WIDTH
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Preferences
import utils.app.TeamListItemSaver
import utils.app.newImageLoader
import utils.app.newImageRequest
import utils.app.rememberMutableStateListJsonOf
import utils.app.rememberMutableStateListOf
import utils.app.removeStrQuote
import utils.calculator.ActionOrderEnemySpeed
import utils.calculator.ActionOrderProcessItem
import utils.calculator.CharAction
import utils.calculator.TeamListItem
import utils.calculator.TeammateItem
import utils.calculator.checkMaxSkillPoint

private lateinit var actionOrdereProcessList : SnapshotStateList<ActionOrderProcessItem> ;

var TEST_SIMULATION_RESULT = arrayListOf<ActionOrderProcessItem>(
    ActionOrderProcessItem(
        charId = 1212,
        charIcon = Character.getCharacterImageFromOfficialId(imageFolder = ImageFolder.CHAR_ICON, charId = "1212").let { if(it is String) it else "" },
        charRarity = 5,
        currRound = 1,
        charCurrActionTimes = 1,
        currSkillPoint = 4,
        energy = 95,
        energyMax = 140,
        actionValue = 72.91f,
        action = CharAction.BASIC
    ),
    ActionOrderProcessItem(
        charId = 1202,
        charIcon = Character.getCharacterImageFromOfficialId(imageFolder = ImageFolder.CHAR_ICON, charId = "1202").let { if(it is String) it else "" },
        charRarity = 4,
        currRound = 1,
        charCurrActionTimes = 1,
        currSkillPoint = 3,
        energy = 90,
        energyMax = 130,
        actionValue = 85.30f,
        action = CharAction.SKILL
    ),
    ActionOrderProcessItem(
        charId = 1205,
        charIcon = Character.getCharacterImageFromOfficialId(imageFolder = ImageFolder.CHAR_ICON, charId = "1205").let { if(it is String) it else "" },
        charRarity = 5,
        currRound = 1,
        charCurrActionTimes = 1,
        currSkillPoint = 2,
        energy = 130,
        energyMax = 130,
        actionValue = 95.30f,
        action = CharAction.ULTIMATE
    ),
    ActionOrderProcessItem(
        charId = 1102,
        charIcon = Character.getCharacterImageFromOfficialId(imageFolder = ImageFolder.CHAR_ICON, charId = "1102").let { if(it is String) it else "" },
        charRarity = 5,
        currRound = 1,
        charCurrActionTimes = 1,
        currSkillPoint = 1,
        energy = 90,
        energyMax = 120,
        actionValue = 105.30f,
        action = CharAction.SKILL
    ),
    ActionOrderProcessItem(
        charId = 1205,
        charIcon = Character.getCharacterImageFromOfficialId(imageFolder = ImageFolder.CHAR_ICON, charId = "1205").let { if(it is String) it else "" },
        charRarity = 5,
        currRound = 1,
        charCurrActionTimes = 1,
        currSkillPoint = 2,
        energy = 5,
        energyMax = 130,
        actionValue = 110.30f,
        action = CharAction.BASIC
    ),

)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActionOrderSimulatorPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
) {
    val index = rememberSaveable { backStackEntry.query<String>("index")!!.toInt() }
    val hazeState = remember { HazeState() }
    val teamListItem = rememberSaveable(stateSaver = TeamListItemSaver) { if(actionOrderTeamList.size < index+1) mutableStateOf(TeamListItem()) else mutableStateOf(actionOrderTeamList[index]) }
    val teamDataListSnap = rememberMutableStateListJsonOf<TeammateItem>().apply { clear() ; addAll(teamListItem.value.teamDataList) }
    val isInit = remember { mutableStateOf(false) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val localCharList = rememberSaveable { mutableStateOf<ArrayList<Character>>(arrayListOf()) }
    val screenScaler = remember { mutableStateOf(1f) }

    actionOrdereProcessList = rememberMutableStateListOf<ActionOrderProcessItem>().apply { clear() ; addAll(TEST_SIMULATION_RESULT) }

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

    //UI
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        screenScaler.value = maxWidth / 390.dp
        FlowRow(modifier = Modifier
            .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            .fillMaxSize(),
        ) {
            ActionOrderItemInfoSetting(teamListItem, teamDataListSnap ,index, navigator, isPopupOpen, screenScaler)
            ActionOrderSimulatorUI(teamListItem, teamDataListSnap, screenScaler)
        }

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
                //So complicated, consider to improve it at future
                TeamSelectPopup(localCharList, isPopupOpen, teamDataList = mutableStateListOf<TeammateItem>().apply { addAll(teamListItem.value.teamDataList) } ){ teamListItemTmp ->
                    actionOrderTeamList[index].teamDataList.clear()
                    actionOrderTeamList[index].teamDataList.addAll(teamListItemTmp)
                    teamDataListSnap.clear()
                    teamDataListSnap.addAll(teamListItemTmp)
                    teamListItem.value = actionOrderTeamList[index]
                    isPopupOpen.value = false
                    Preferences().ActionOrder.setActionOrderList()
                }
            }
        }
    }
}

@Composable
fun ActionOrderItemInfoSetting(teamListItem: MutableState<TeamListItem>, teamDataListSnap : SnapshotStateList<TeammateItem>,  index: Int, navigator: Navigator, isPopupOpen : MutableState<Boolean>, screenScaler: MutableState<Float>) {
    Column(modifier = Modifier.wrapContentSize()) {
        Spacer(modifier = Modifier.statusBarsPadding().height(16.dp))
        //Title of Team, Back Button and Info Button
        Row(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
            //Back Button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = CircleShape)
                    .background(Color(0x33FFFFFF))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        //Save and Exit
                        actionOrderTeamList[index] = teamListItem.value
                        navigator.popBackStack()
                    }
            ) {
                Image(
                    painter = painterResource(Res.drawable.ui_icon_back),
                    contentDescription = "Exit Without Saving",
                    modifier = Modifier.size(32.dp).align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }
            Spacer(modifier = Modifier.weight(1f).wrapContentHeight())
            //Title of Team

            Column(modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically)) {
                val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy.MM.dd") }
                Text(
                    text = teamListItem.value.teamName,
                    style = FontSizeNormal16(),
                    color = Color(0xFFFFFFFF)
                )

                Text(
                    text = dateFormat.format(
                        Instant.fromEpochMilliseconds(teamListItem.value.teamBuildUnix).toLocalDateTime(
                            TimeZone.currentSystemDefault())),
                    style = FontSizeNormal14(),
                    color = Color(0x99FFFFFF)
                )
            }
            Spacer(modifier = Modifier.weight(1f).wrapContentHeight())
            //Info Button
            Image(
                painter = painterResource(Res.drawable.ui_icon_info),
                contentDescription = "Info Button",
                modifier = Modifier.size(20.dp).align(Alignment.CenterVertically).padding(2.dp),
                colorFilter = ColorFilter.tint(Color.White),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
            .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        ) {
            Column(modifier = Modifier.wrapContentSize().padding(16.dp)) {
                Row {
                    Text(
                        text = removeStrQuote(Res.string.Character),
                        style = FontSizeNormal16(),
                        color = Color(0xFF222222),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = removeStrQuote(Res.string.ActionOrderModify),
                        style = FontSizeNormal16(),
                        color = Color(0xFF222222),
                        modifier = Modifier.clickable { isPopupOpen.value = true }.clip(
                            RoundedCornerShape(4.dp)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                //Character Card
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(
                        start = SCREEN_SAVE_PADDING,
                        end = SCREEN_SAVE_PADDING
                    )
                ){
                    items(teamDataListSnap.size) { index ->
                        Column(modifier = Modifier.wrapContentSize()) {
                            CharacterCard(teamDataListSnap[index].character, isDisplayLevel = true)

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally)) {
                                UIWithGrayBG {
                                    Text(
                                        text = teamDataListSnap[index].energyMax.toString(),
                                        style = FontSizeNormal16(),
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                //Initial/Max Battle Point, Enemy Speed
                //@DoItLater("Apply Speed Checking in last Pair")
                val textInfo = arrayListOf<Pair<StringResource, Int>>(
                    Pair(Res.string.ActionOrderInitSkillPoint, 3),
                    Pair(Res.string.ActionOrderMaxSkillPoint, checkMaxSkillPoint(ArrayList(teamDataListSnap))),
                    Pair(Res.string.ActionOrderEnemySpeedTitle, teamListItem.value.teamEnemySpeed)
                )
                Column {
                    textInfo.forEachIndexed { idex, item ->
                        Row {
                            Text(
                                text = removeStrQuote(item.first),
                                style = FontSizeNormal16(),
                                color = Color(0xFF222222),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            if(item.first == Res.string.ActionOrderEnemySpeedTitle){
                                //Spinner
                                UIWithGrayBG {
                                    Text(
                                        text = removeStrQuote(ActionOrderEnemySpeed.fromSpeed(item.second).res),
                                        style = FontSizeNormal16(),
                                        color = Color.White,
                                    )

                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            UIWithGrayBG {
                                Text(
                                    text = item.second.toString(),
                                    style = FontSizeNormal16(),
                                    color = Color.White,
                                )

                            }
                        }
                        if(idex < textInfo.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionOrderSimulatorUI(teamListItem: MutableState<TeamListItem>, teamDataListSnap : SnapshotStateList<TeammateItem>, screenScaler: MutableState<Float>){
    //UI
    Box(modifier = Modifier.wrapContentSize()) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            //Title Row
            Row(modifier = Modifier.fillMaxWidth()) {
                //All Progress
                Row(modifier = Modifier.width((SIMULATOR_LEFT_STATIC_ROW_WIDTH + 16.dp) * screenScaler.value).background(Color.Blue)) {

                }

                Spacer(modifier = Modifier.width(4.dp))

                //Title Row
                val textInfo = arrayListOf(Res.string.ActionOrderSimulatorSkillPoint, Res.string.ActionOrderSimulatorCharEnergy, Res.string.ActionOrderSimulatorActionValue, Res.string.ActionOrderSimulatorActionTimes)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(textInfo){
                        Text(
                            text = removeStrQuote(it),
                            style = FontSizeNormal14(),
                            color = Color(0xFFDDDDDD),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier.defaultMinSize(48.dp, 32.dp).wrapContentWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Simulator Box
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                ) {

                }
            }

        }
    }
}

@Composable
fun ActionOrderSimulatorProcessRow(processItem: ActionOrderProcessItem, screenScaler : MutableState<Float>, valueTextWidth : MutableState<List<Float>>){
    val context = LocalPlatformContext.current
    Row {
        AsyncImage(
            model = newImageRequest(
                data = processItem.charIcon,
                context = context
            ),
            contentDescription = "Character Icon",
            modifier = Modifier
                .size(32.dp)
                .background(
                    Brush.verticalGradient(
                    colors = getCardBgColorByRare(processItem.charRarity)
                ))
                .clip(CircleShape),
        )
    }

    Spacer(modifier = Modifier.width(18.dp))

    //B
    CheckableTextButton(
        text = processItem.action.shortForm.toString(),
        isChecked = mutableStateOf(processItem.action == CharAction.BASIC),
        onClick = { processItemResult ->
            processItemResult.action = CharAction.BASIC
            //@DoItLater("Ask for re-calculation")
        },
        processItem = processItem
    )
    Spacer(modifier = Modifier.width(10.dp))

    //S
    CheckableTextButton(
        text = processItem.action.shortForm.toString(),
        isChecked = mutableStateOf(processItem.action == CharAction.BASIC),
        onClick = { processItemResult ->
            processItemResult.action = CharAction.BASIC
            //@DoItLater("Ask for re-calculation")
        },
        processItem = processItem
    )
    Spacer(modifier = Modifier.width(10.dp))

    //U
    CheckableTextButton(
        text = processItem.action.shortForm.toString(),
        isChecked = mutableStateOf(processItem.action == CharAction.BASIC),
        onClick = { processItemResult ->
            processItemResult.action = CharAction.BASIC
            //@DoItLater("Ask for re-calculation")
        },
        processItem = processItem
    )
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
private fun CheckableTextButton(text: String = "B", processItem: ActionOrderProcessItem, isChecked: MutableState<Boolean> = mutableStateOf(false), onClick: (processItem: ActionOrderProcessItem) -> Unit){
    Box(modifier = Modifier
        .size(28.dp, 32.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color(if(isChecked.value) 0xCCFFFFFF else 0x33000000))
    ){
        Text(
            text = text,
            style = FontSizeNormal16(),
            color = Color(if(isChecked.value) 0xFF444444 else 0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center).clickable { onClick.invoke(processItem) }
        )
    }
}


@Composable
private fun UIWithGrayBG(component : @Composable () -> Unit){
    Box(
        modifier = Modifier
            .background(Color(0x33000000), RoundedCornerShape(8.dp))
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .defaultMinSize(60.dp, 30.dp),
        contentAlignment = Alignment.Center
    ) {
        component()
    }
}