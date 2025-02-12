package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.unit.dp
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
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Preferences
import utils.app.removeStrQuote
import utils.calculator.ActionOrderEnemySpeed
import utils.calculator.TeamListItem
import utils.calculator.TeammateItem
import utils.calculator.checkMaxSkillPoint

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
    val teamListItem = rememberSaveable { mutableStateOf(if(actionOrderTeamList.size < index+1) TeamListItem() else actionOrderTeamList[index]) }
    val isInit = remember { mutableStateOf(false) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val localCharList = rememberSaveable { mutableStateOf<ArrayList<Character>>(arrayListOf()) }
    val teamDataListTmp = SnapshotStateList<TeammateItem>()

    LaunchedEffect(Unit){
        if(!isInit.value){
            localCharList.value.addAll(UserAccount.INSTANCE.characterList)
            charList.value.forEach { char ->
                if(UserAccount.INSTANCE.characterList.none { it.officialId == char.officialId }){
                    localCharList.value.add(char)
                }
            }
            teamDataListTmp.clear()
            teamDataListTmp.addAll(actionOrderTeamList[index].teamDataList)
            isInit.value = true
        }
    }

    //UI
    Box(modifier = modifier.fillMaxSize()) {
        FlowRow(modifier = Modifier.padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)) {
            ActionOrderItemInfoSetting(teamListItem, index, navigator, isPopupOpen)
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
                TeamSelectPopup(localCharList, isPopupOpen, teamDataList = teamDataListTmp ){ teamListItemTmp ->
                    actionOrderTeamList[index].teamDataList.clear()
                    actionOrderTeamList[index].teamDataList.addAll(teamListItemTmp)
                    teamListItem.value = actionOrderTeamList[index]
                    isPopupOpen.value = false
                    Preferences().ActionOrder.setActionOrderList()
                    teamDataListTmp.clear()
                    teamDataListTmp.addAll(actionOrderTeamList[index].teamDataList)
                }
            }
        }
    }
}

//TeamDataList set as non-snapshot
@Composable
fun ActionOrderItemInfoSetting(teamListItem: MutableState<TeamListItem>, index: Int, navigator: Navigator, isPopupOpen : MutableState<Boolean>) {
    val teamDataList: SnapshotStateList<TeammateItem> = rememberSaveable { mutableStateListOf() }
    teamDataList.addAll(teamListItem.value.teamDataList)
    Column {
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
                    items(teamDataList.size) { index ->
                        Column(modifier = Modifier.wrapContentSize()) {
                            CharacterCard(teamDataList[index].character, isDisplayLevel = true)

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally)) {
                                UIWithGrayBG {
                                    Text(
                                        text = teamDataList[index].energyMax.toString(),
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
                    Pair(Res.string.ActionOrderMaxSkillPoint, checkMaxSkillPoint(teamListItem.value.teamDataList)),
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
fun UIWithGrayBG(component : @Composable () -> Unit){
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