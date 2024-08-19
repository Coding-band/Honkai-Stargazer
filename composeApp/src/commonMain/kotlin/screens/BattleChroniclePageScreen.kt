package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.BattleChronicleCard
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.MOCMyBattleReport
import files.Res
import files.ui_icon_share
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import types.Constants
import types.MemoryOfChaosList
import types.UserAccount
import utils.FontSizeNormal14
import utils.FontSizeNormal20
import utils.UtilTools

@Composable
fun BattleChroniclePageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
    backStackEntry: BackStackEntry,
) {
    val hazeState = remember { HazeState() }
    val listState = remember { LazyListState() }
    val uid = backStackEntry.query<String>("uid")!!
    val userAccount by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAccount.INSTANCE
        } else {
            UserAccount.UIDSEARCH
        }
    ) }
    val mocIds = userAccount.userCurrMOCList.map { it.mocId }.distinct().sortedDescending()
    val mocTitles = mocIds.map { MemoryOfChaosList.getMocTitleLocaleNameById(it) }

    println("mocIds : $mocIds")
    println("mocTitles : $mocTitles")

    Box(modifier = Modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(
                start = Constants.SCREEN_SAVE_PADDING,
                end = Constants.SCREEN_SAVE_PADDING
            ).haze(hazeState)
        ) {
            item { Spacer(Modifier.height(PAGE_HEADER_HEIGHT + 24.dp)) }

            val sorttedMOCList = userAccount.userCurrMOCList
                .sortedByDescending { it.mocId }
                .sortedByDescending { it.floor }
                .groupBy { it.mocId to it.floor }
                .map { it.value }
                .sortedByDescending { it.first().mocId }


            items(count = sorttedMOCList.size) { index ->
                val mocData = sorttedMOCList[index]
                BattleChronicleCard(
                    hazeState = hazeState,
                    mocData = mocData,
                    title = mocTitles[mocIds.indexOf(mocData.first().mocId)],
                )
                if (index < sorttedMOCList.size - 1) {
                    Spacer(Modifier.height(8.dp))
                }
            }

            item { Spacer(Modifier.statusBarsPadding().height(64.dp)) }
        }


        PageHeaderAlpha(
            navigator = navigator,
            onForward = {
                //TODO : Remember to add the Share Function
            },
            forwardIconId = Res.drawable.ui_icon_share,
            hazeState = hazeState,

            ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    UtilTools().removeStringResDoubleQuotes(Res.string.MOCMyBattleReport),
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                    style = FontSizeNormal20(),
                    color = Color.White
                )

                Text(
                    text = "${userAccount.uid}·${
                        UtilTools().removeStringResDoubleQuotes(
                            userAccount.server.localeName
                        )
                    }",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                        .clip(RoundedCornerShape(49.dp)).padding(8.dp),
                    style = FontSizeNormal14(),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BattleChronicleTopFilter() {
    Column {
        //TODO : Add the Filter Function
    }
}