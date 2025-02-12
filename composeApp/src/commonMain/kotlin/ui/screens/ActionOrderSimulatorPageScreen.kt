package ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
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
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.UserAccount
import ui.components.CharacterCard
import ui.components.HeaderData
import ui.components.defaultHeaderData
import utils.app.Constants
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.removeStrQuote


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActionOrderSimulatorPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
) {
    val index = backStackEntry.query<String>("index")!!.toInt()
    val hazeState = remember { HazeState() }
    val teamListItem = remember { mutableStateOf(actionOrderTeamList[index]) }
    val isInit = remember { mutableStateOf(false) }
    val isPopupOpen = remember { mutableStateOf(false) }
    val localCharList = rememberSaveable { mutableStateOf<ArrayList<Character>>(arrayListOf()) }

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
    FlowRow(modifier = Modifier.fillMaxSize().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)) {
        ActionOrderItemInfoSetting(teamListItem.value, index, navigator)
    }
}

@Composable
fun ActionOrderItemInfoSetting(teamListItem: TeamListItem, index: Int, navigator: Navigator) {
    Column {
        Spacer(modifier = Modifier.statusBarsPadding().height(16.dp))
        //Title of Team, Back Button and Info Button
        Row(modifier = Modifier.wrapContentHeight()) {
            //Back Button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = CircleShape)
                    .background(Color(0x33FFFFFF))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        //Save and Exit
                        actionOrderTeamList[index] = teamListItem
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
            //Title of Team

            Column {
                val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy.MM.dd") }
                Text(
                    text = teamListItem.teamName,
                    style = FontSizeNormal16(),
                    color = Color(0xFFFFFFFF)
                )

                Text(
                    text = dateFormat.format(
                        Instant.fromEpochMilliseconds(teamListItem.teamBuildUnix).toLocalDateTime(
                            TimeZone.currentSystemDefault())),
                    style = FontSizeNormal14(),
                    color = Color(0x99FFFFFF)
                )
            }
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
            Column(modifier = Modifier.wrapContentSize()) {
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
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                //Character Card
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(
                        start = SCREEN_SAVE_PADDING,
                        end = SCREEN_SAVE_PADDING
                    )
                ){
                    items(teamListItem.teamDataList.size) { index ->
                        Column(modifier = Modifier.wrapContentSize()) {
                            CharacterCard(teamListItem.teamDataList[index].character, isDisplayLevel = true)

                            Spacer(modifier = Modifier.height(8.dp))

                            UIWithGrayBG {
                                Text(
                                    text = teamListItem.teamDataList[index].energyMax.toString(),
                                    style = FontSizeNormal16(),
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }

                //Initial/Max Battle Point, Enemy Speed

            }
        }
    }
}

@Composable
fun UIWithGrayBG(component : @Composable () -> Unit){
    Box(
        modifier = Modifier
            .background(Color(0x33000000))
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .clip(RoundedCornerShape(4.dp))
            .defaultMinSize(60.dp, 30.dp),
        contentAlignment = Alignment.Center
    ) {
        component()
    }
}