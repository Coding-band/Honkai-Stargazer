package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import components.BattleChronicleCard
import components.DropdownMenuNoPadding
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AppStatusNoDataFound
import files.MOCMyBattleReport
import files.MemoryOfChaos
import files.PureFiction
import files.Res
import files.ic_arrow_down_spinner
import files.ic_exchange_icon
import files.pom_pom_failed_issue
import files.ui_icon_exchange
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import types.AbyssInfoList
import types.AbyssInfoType
import types.Constants
import types.UserAbyssRecord
import types.UserAccount
import utils.FontSizeNormal14
import utils.FontSizeNormal16
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
    val userAbyssRecord by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAbyssRecord.INSTANCE
        } else {
            //TODO : Add the UserAbyssRecord.UIDSEARCH when needed
            UserAbyssRecord.INSTANCE
        }
    ) }

    val choiceChronicleIndex = remember { mutableStateOf(0) }
    val choiceStrList = listOf(
        UtilTools().removeStringResDoubleQuotes(Res.string.MemoryOfChaos) to AbyssInfoType.MemoryOfChaos,
        UtilTools().removeStringResDoubleQuotes(Res.string.PureFiction) to AbyssInfoType.PureFiction,
    )
    val choiceChronicle = remember { mutableStateOf(userAbyssRecord.userCurrMOCList) }
    choiceChronicle.value = when (choiceStrList[choiceChronicleIndex.value].second) {
        AbyssInfoType.MemoryOfChaos -> userAbyssRecord.userCurrMOCList
        AbyssInfoType.PureFiction -> userAbyssRecord.userCurrPFList
    }

    val mocIds = userAbyssRecord.userCurrMOCList.map { it.id }.distinct().sortedDescending()
    val pfIds = userAbyssRecord.userCurrPFList.map { it.id }.distinct().sortedDescending()
    val mocTitles = mocIds.map { AbyssInfoList.getAbyssTitleLocaleNameById(it, AbyssInfoType.MemoryOfChaos) }
    val pfTitles = pfIds.map { AbyssInfoList.getAbyssTitleLocaleNameById(it, AbyssInfoType.PureFiction) }

    val isAsc = remember { mutableStateOf(false) }
    val ascendingStr = arrayListOf(
        "降序",
        "升序",
    )
    val ids = remember { mutableStateOf(mocIds) }
    ids.value = when (choiceStrList[choiceChronicleIndex.value].second) {
        AbyssInfoType.MemoryOfChaos -> mocIds
        AbyssInfoType.PureFiction -> pfIds
    }
    val density = LocalDensity.current.density
    val sorttedAbyssList = choiceChronicle.value
        .asSequence()
        .sortedByDescending { it.id }
        .sortedByDescending { it.floor }
        .groupBy { it.id to it.floor }
        .map { it.value }
        .sortedByDescending { it.first().id }
        .toList()

    val sorttedAbyssListAsc = sorttedAbyssList.reversed()

    Box(modifier = Modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(
                start = Constants.SCREEN_SAVE_PADDING,
                end = Constants.SCREEN_SAVE_PADDING
            ).haze(hazeState)
        ) {
            item { Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(PAGE_HEADER_HEIGHT)
            ) }

            item {
                val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
                val isDropDownOpen = remember { mutableStateOf(false) }
                Column {
                    Row {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable { isDropDownOpen.value = !isDropDownOpen.value }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .onSizeChanged { optionTextViewSize.value = it },
                            ) {
                                Text(choiceStrList[choiceChronicleIndex.value].first, style = FontSizeNormal16(), color = Color.White)
                                Spacer(Modifier.width(4.dp))
                                Image(painterResource(Res.drawable.ic_arrow_down_spinner), modifier = Modifier.size(12.dp).align(Alignment.CenterVertically), colorFilter = ColorFilter.tint(Color.White), contentDescription = null,)
                            }
                            //對於DropdownItem沒法按照設計稿展示，暫時無解
                            DropdownMenuNoPadding(
                                expanded = isDropDownOpen.value,
                                onDismissRequest = { isDropDownOpen.value = false },
                                modifier = Modifier
                                    .background(Color(0xFF3E3E47))
                                    .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
                            ) {
                                choiceStrList.forEachIndexed { index, option ->
                                    DropdownMenuItem(
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        contentPadding = PaddingValues(
                                            vertical = 4.dp,
                                            horizontal = 0.dp
                                        ),
                                        onClick = {
                                            choiceChronicleIndex.value = index
                                            isDropDownOpen.value = false
                                            //optionAction(schoolIndex.value)
                                        }
                                    ) {
                                        Text(
                                            text = option.first,
                                            style = FontSizeNormal16(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        Spacer(Modifier.width(16.dp))

                        Row(Modifier.clickable { isAsc.value = !isAsc.value }) {
                            Text(ascendingStr[if(isAsc.value) 1 else 0], style = FontSizeNormal16(), color = Color.White)
                            Spacer(Modifier.width(4.dp))
                            Image(painterResource(Res.drawable.ic_exchange_icon), modifier = Modifier.size(12.dp).align(Alignment.CenterVertically), colorFilter = ColorFilter.tint(Color.White), contentDescription = null)
                        }

                    }
                    Spacer(Modifier.height(8.dp))
                }
            }

            items(count = sorttedAbyssList.size) { index ->
                val mocData = (if(isAsc.value){ sorttedAbyssListAsc } else sorttedAbyssList)[index]
                BattleChronicleCard(
                    hazeState = hazeState,
                    data = mocData,
                    type = choiceStrList[choiceChronicleIndex.value].second,
                    title =
                    when (choiceStrList[choiceChronicleIndex.value].second) {
                        AbyssInfoType.MemoryOfChaos -> mocTitles
                        AbyssInfoType.PureFiction -> pfTitles
                    }[ids.value.indexOf(mocData.first().id)],
                )
                if (index < sorttedAbyssList.size - 1) {
                    Spacer(Modifier.height(8.dp))
                }
            }

            item { Spacer(Modifier.statusBarsPadding().height(64.dp)) }
        }

        if(sorttedAbyssList.isEmpty()) {
            Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxWidth(1/2f).align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(Res.drawable.pom_pom_failed_issue),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = UtilTools().removeStringResDoubleQuotes(Res.string.AppStatusNoDataFound),
                        style = FontSizeNormal16(),
                        color = Color.White,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }


        PageHeaderAlpha(
            navigator = navigator,
            onForward = {
                isAsc.value = !isAsc.value
            },
            forwardIconId = Res.drawable.ui_icon_exchange,
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