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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import files.ActionOrderEnemySpeedTitle
import files.ActionOrderInitSkillPoint
import files.ActionOrderMaxSkillPoint
import files.ActionOrderModify
import files.ActionOrderSimulatorActionTimes
import files.ActionOrderSimulatorActionValue
import files.ActionOrderSimulatorCharEnergy
import files.ActionOrderSimulatorSimulateRounds
import files.ActionOrderSimulatorSkillPoint
import files.Character
import files.Res
import files.ui_icon_back
import files.ui_icon_info
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import types.UserAccount
import ui.components.CharacterCard
import ui.components.HeaderData
import ui.components.defaultHeaderData
import ui.navigation.popBackStackLimited
import utils.app.Constants
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.SIMULATOR_LEFT_STATIC_ROW_WIDTH
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Preferences
import utils.app.TeamListItemSaver
import utils.app.newImageRequest
import utils.app.pxToDp
import utils.app.rememberMutableStateListJsonOf
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

/**
 * arrayListOf(<All Process Spinner>, <Skill Point>, <Energy>, <Action Value>, <Action Times>)
 */
lateinit var maxValueTextWidth : MutableState<ArrayList<Dp>>

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActionOrderSimulatorPageScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry,
) {
    val index = rememberSaveable { backStackEntry.arguments?.getString("index")!!.toInt() }
    val hazeState = remember { HazeState() }
    val teamListItem = rememberSaveable(stateSaver = TeamListItemSaver) { if(actionOrderTeamList.size < index+1) mutableStateOf(TeamListItem()) else mutableStateOf(actionOrderTeamList[index]) }
    val teamDataListSnap = rememberMutableStateListJsonOf<TeammateItem>().apply { clear() ; addAll(teamListItem.value.teamDataList) }
    val isInit = remember { mutableStateOf(false) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val localCharList = rememberSaveable { mutableStateOf<ArrayList<Character>>(arrayListOf()) }

    actionOrdereProcessList = rememberMutableStateListJsonOf<ActionOrderProcessItem>().apply { clear() ; addAll(TEST_SIMULATION_RESULT) }
    maxValueTextWidth = remember { mutableStateOf(arrayListOf(SIMULATOR_LEFT_STATIC_ROW_WIDTH, 48.dp, 48.dp, 48.dp, 48.dp)) }

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
        val isWideScreen = maxWidth > 750.dp

        if (isWideScreen) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .align(Alignment.Center)
                    .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            ) {
                key("ActionOrderItemInfoSetting") {
                    ActionOrderItemInfoSetting(teamListItem, teamDataListSnap, index, navigator, isPopupOpen)
                }
                Spacer(modifier = Modifier.width(12.dp))
                key("ActionOrderSimulatorUI") {
                    ActionOrderSimulatorUI(teamListItem, teamDataListSnap)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
                    .align(Alignment.Center)
                    .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            ) {
                key("ActionOrderItemInfoSetting") {
                    ActionOrderItemInfoSetting(teamListItem, teamDataListSnap, index, navigator, isPopupOpen)
                }
                Spacer(modifier = Modifier.height(12.dp))
                key("ActionOrderSimulatorUI") {
                    ActionOrderSimulatorUI(teamListItem, teamDataListSnap)
                }
            }
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
fun ActionOrderItemInfoSetting(teamListItem: MutableState<TeamListItem>, teamDataListSnap : SnapshotStateList<TeammateItem>,  index: Int, navigator: NavHostController, isPopupOpen : MutableState<Boolean>) {
    Column(modifier = Modifier.wrapContentSize().widthIn(min = INFO_MIN_WIDTH, max = INFO_MAX_WIDTH)) {
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
                        navigator.popBackStackLimited()
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
            .wrapContentSize()
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
                    modifier = Modifier.wrapContentSize()
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

                //Default as 5, max as 99 (????)
                val simulateRounds = remember{ mutableStateOf(5) }

                //Initial/Max Battle Point, Enemy Speed
                val textInfo = arrayListOf<Pair<StringResource, Int>>(
                    Pair(Res.string.ActionOrderInitSkillPoint, 3),
                    Pair(Res.string.ActionOrderMaxSkillPoint, checkMaxSkillPoint(ArrayList(teamDataListSnap))),
                    Pair(Res.string.ActionOrderEnemySpeedTitle, teamListItem.value.teamEnemySpeed),
                    Pair(Res.string.ActionOrderSimulatorSimulateRounds, simulateRounds.value),
                )
                Column {
                    textInfo.forEachIndexed { index, item ->
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
                        if(index < textInfo.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionOrderSimulatorUI(teamListItem: MutableState<TeamListItem>, teamDataListSnap : SnapshotStateList<TeammateItem>){
    val uiWidth = remember { mutableStateOf(100.dp) }
    val density = LocalDensity.current.density
    val sharedLazyRowState = mutableStateOf(rememberLazyListState())


    //UI
    BoxWithConstraints(modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp).navigationBarsPadding().widthIn(min = INFO_MIN_WIDTH, max = INFO_MAX_WIDTH)) {
        uiWidth.value = this.maxWidth

        Column(modifier = Modifier.wrapContentSize()) {
            Spacer(modifier = Modifier.height(8.dp))

            //Title Row
            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                //All Progress
                Row(modifier = Modifier
                    .padding(start = 16.dp)
                    .width(maxValueTextWidth.value[0])
                    .height(10.dp)
                ) {

                }

                //Title Row
                val textInfo = arrayListOf(Res.string.ActionOrderSimulatorSkillPoint, Res.string.ActionOrderSimulatorCharEnergy, Res.string.ActionOrderSimulatorActionValue, Res.string.ActionOrderSimulatorActionTimes)
                LazyRow(
                    state = sharedLazyRowState.value,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(textInfo){
                        Text(
                            text = removeStrQuote(it),
                            style = FontSizeNormal14(),
                            color = Color(0xFFDDDDDD),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .defaultMinSize(48.dp, 32.dp)
                                .wrapContentHeight().onSizeChanged { size ->
                                maxValueTextWidth.value[textInfo.indexOf(it)+1] = androidx.compose.ui.unit.max(maxValueTextWidth.value[textInfo.indexOf(it)+1], pxToDp(size.width, density))
                            }
                        )
                        if(textInfo.indexOf(it) < textInfo.size - 1) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Simulator Box
            Box(modifier = Modifier.fillMaxSize().background(Color(0x66F3F9FF), RoundedCornerShape(10.dp)).padding(16.dp)){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(actionOrdereProcessList){
                        ActionOrderSimulatorProcessRow(it, sharedLazyRowState)

                        if(actionOrdereProcessList.indexOf(it) < actionOrdereProcessList.size - 1) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

        }
    }

    // Synchronize scroll positions
    LaunchedEffect(sharedLazyRowState.value.firstVisibleItemScrollOffset, sharedLazyRowState.value.firstVisibleItemIndex) {
        snapshotFlow { sharedLazyRowState.value.firstVisibleItemScrollOffset }
            .collect { offset ->
                sharedLazyRowState.value.scrollToItem(sharedLazyRowState.value.firstVisibleItemIndex, offset)
            }
    }
}

@Composable
fun ActionOrderSimulatorProcessRow(processItem: ActionOrderProcessItem, sharedLazyRowState: MutableState<LazyListState>){
    val context = LocalPlatformContext.current
    val density = LocalDensity.current.density

    Row(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.onSizeChanged {
            maxValueTextWidth.value[0] = pxToDp(it.width, density = density)
        }) {
            AsyncImage(
                model = newImageRequest(
                    data = processItem.charIcon,
                    context = context
                ),
                contentDescription = "Character Icon",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = getCardBgColorByRare(processItem.charRarity)
                        )),
            )
            Spacer(modifier = Modifier.width(18.dp))

            val isCheckedB = mutableStateOf(processItem.action == CharAction.BASIC)
            val isCheckedS = mutableStateOf(processItem.action == CharAction.SKILL)
            val isCheckedU = mutableStateOf(processItem.action == CharAction.ULTIMATE)

            //B
            CheckableTextButton(
                text = CharAction.BASIC.shortForm.toString(),
                isChecked = isCheckedB,
                onClick = { processItemResult ->
                    processItemResult.action = CharAction.BASIC
                    isCheckedB.value = true
                    isCheckedS.value = false
                    isCheckedU.value = false
                    //@DoItLater("Ask for re-calculation")
                },
                processItem = processItem
            )
            Spacer(modifier = Modifier.width(10.dp))

            //S
            CheckableTextButton(
                text = CharAction.SKILL.shortForm.toString(),
                isChecked = isCheckedS,
                onClick = { processItemResult ->
                    processItemResult.action = CharAction.SKILL
                    isCheckedB.value = false
                    isCheckedS.value = true
                    isCheckedU.value = false
                    //@DoItLater("Ask for re-calculation")
                },
                processItem = processItem
            )
            Spacer(modifier = Modifier.width(10.dp))

            //U
            CheckableTextButton(
                text = CharAction.ULTIMATE.shortForm.toString(),
                isChecked = isCheckedU,
                onClick = { processItemResult ->
                    processItemResult.action = CharAction.ULTIMATE
                    isCheckedB.value = false
                    isCheckedS.value = false
                    isCheckedU.value = true
                    //@DoItLater("Ask for re-calculation")
                },
                processItem = processItem
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        //Scrollable Row
        val textData = mutableStateListOf(
            processItem.currSkillPoint.toString(),
            processItem.energy.toString(),
            processItem.actionValue.toString(),
            processItem.charCurrActionTimes.toString()
        )

        LazyRow(
            state = sharedLazyRowState.value,
            modifier = Modifier.wrapContentWidth().height(32.dp).align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(textData){
                val index = textData.indexOf(it) + 1
                Text(
                    text = it,
                    style = FontSizeNormal16(),
                    color = when(index){
                        1 -> if(processItem.currSkillPoint >= processItem.teamMaxSkillPoint) Color(0xFFFFD070) else Color.White
                        2 -> if(processItem.energy >= processItem.energyMax) Color(0xFFFFD070) else Color.White
                        else -> Color.White
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.onSizeChanged {
                        maxValueTextWidth.value[index] = androidx.compose.ui.unit.max(maxValueTextWidth.value[index], pxToDp(it.width, density))
                    }.width(maxValueTextWidth.value[index]).height(32.dp)
                )

                if(textData.indexOf(it) < textData.size - 1) {
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}

@Composable
private fun CheckableTextButton(text: String = "B", processItem: ActionOrderProcessItem, isChecked: MutableState<Boolean> = mutableStateOf(false), onClick: (processItem: ActionOrderProcessItem) -> Unit){
    Box(modifier = Modifier
        .size(28.dp, 32.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color(if(isChecked.value) 0xCCFFFFFF else 0x33000000))
        .clickable { onClick.invoke(processItem) }
    ){
        Text(
            text = text,
            style = FontSizeNormal16(),
            color = Color(if(isChecked.value) 0xFF444444 else 0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
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