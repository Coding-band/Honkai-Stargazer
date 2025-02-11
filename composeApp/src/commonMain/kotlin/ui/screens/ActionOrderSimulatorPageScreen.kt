package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import ui.components.HeaderData
import ui.components.defaultHeaderData
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16


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
                if(UserAccount.INSTANCE.characterList.filter { it.officialId == char.officialId }.isEmpty()){
                    localCharList.value.add(char)
                }
            }
            isInit.value = true
        }
    }

    //UI
    FlowRow(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
fun ActionOrderItemInfoSetting(teamListItem: TeamListItem, index: Int, navigator: Navigator) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
    ) {
        Column {
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
                        modifier = Modifier.size(32.dp).align(Alignment.Center).rotate(180f),
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
        }
    }
}