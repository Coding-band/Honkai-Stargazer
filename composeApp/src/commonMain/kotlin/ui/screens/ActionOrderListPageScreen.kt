package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.HorizontalGrid
import com.cheonjaeung.compose.grid.SimpleGridCells
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import files.Res
import files.phorphos_check_regular
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.navigation.Navigator
import types.Character
import types.CombatType
import types.Path
import ui.components.BackIcon
import ui.components.CharacterCard
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import utils.annotation.DoItLater
import utils.app.Constants
import utils.app.Constants.Companion.CLARA_KAMOJI
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16

data class TeamListItem(
    val uid: String,
    val teamBuildUnix: Long = Clock.System.now().toEpochMilliseconds(),
    val id: String = "${uid}-${teamBuildUnix}",
    val teamName: String = CLARA_KAMOJI,
    val teamDataList: ArrayList<TeammateItem> = arrayListOf()
)

data class TeammateItem(
    val character: Character,
    val level: Int = 1,
    val energyRechargeRate : Float = 1f,
    val speedBase: Float = 100.0f,
    val speedRate: Float = 0f,
)

val TEST_LIST = arrayListOf(
    TeamListItem(
        uid = "900033852",
        teamBuildUnix = 1719789540000L,
        teamName = "再見，Stargazer 2；你好，Stargazer 3！\nBye Stargazer 2 and Hi Stargazer 3!",
        teamDataList = arrayListOf(
            TeammateItem(Character.getCharacterItemFromJSON("1310"), 78, 1.0f, 100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("8006"), 78, 1.0f, 106.0f, 25.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1303"), 78, 1.0f, 112.0f, 25.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1217"), 78, 1.0f, 108.0f, 25.0f),
        )
    ),

    TeamListItem(
        uid = "900033852",
        teamBuildUnix = 1739937600000L,
        teamName = "來測一下？\n黑塔女士舉世無雙！\n黑塔女士聰明絕頂！\n黑塔女士沉魚落雁！",
        teamDataList = arrayListOf(
            TeammateItem(Character.getCharacterItemFromJSON("1401"), 2, 1.0f, 100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1013"), 19, 1.0f, 100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("8006"), 3, 1.0f, 100.0f, 34.0f),
            TeammateItem(Character.getCharacterItemFromJSON("1303"), 5, 1.0f, 100.0f, 34.0f),
        )
    )
)

@DoItLater("Allow user to Export and Import TeamList")
@Composable
fun ActionOrderListPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val hazeState = remember { HazeState() }
    val itemList = remember { mutableStateListOf<TeamListItem>() }
    val isInit = remember { mutableStateOf(false) }

    //Just Testing
    LaunchedEffect(Unit){
        if(!isInit.value){
            itemList.addAll(TEST_LIST)
            isInit.value = true
        }
    }

    Box(modifier = modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier
            .padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
            .haze(state = hazeState)
            //.haze(state = hazeState)
        ) {
            item {
                Spacer(Modifier.height(PAGE_HEADER_HEIGHT+12.dp).statusBarsPadding())
            }
            items(itemList.size) { index ->
                TeamListItemCard(itemList[index], navigator)
                if(index < itemList.size - 1){
                    Spacer(Modifier.height(12.dp))
                }
            }
            item {
                Spacer(Modifier.padding(top = 16.dp).navigationBarsPadding())
            }

        }

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.phorphos_check_regular,
            onForward = {
                //多選功能
            }
        )
    }
}

@DoItLater("Pack this style's card as a Common Component")
@Composable
fun TeamListItemCard(
    teamListItem: TeamListItem,
    navigator: Navigator
) {
    val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy.MM.dd") }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
        .clickable {  } //DoItLater("Open ActionOrderSimulatorPage")
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
                        CharacterCard(character = teamListItem.teamDataList[index].character)
                        if (index < teamListItem.teamDataList.size - 1) {
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    }
}