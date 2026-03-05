/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Character
import types.FilterEnum
import ui.components.BackIcon
import ui.components.CharacterCard
import ui.components.LIST_FILTER_TOOL_HEIGHT
import ui.components.ListFilterTool
import ui.components.ListFilterType
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.navigation.Screen
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.DefaultZIndex
import utils.app.PageBottomMask
import utils.app.SG3VerticalScrollbar
import utils.app.rememberMutableStateListJsonOf

lateinit var charList : MutableState<ArrayList<Character>>
lateinit var charListSortable : MutableState<ArrayList<Character>>
lateinit var filterChoiceArray: SnapshotStateList<FilterEnum>

@Composable
fun initCharList() {
    charList = rememberSaveable(stateSaver = Character.ListSaver) { mutableStateOf(arrayListOf()) }
    // 確保 charListSortable 初始值與 charList 一致，避免 scrollbar 從空列表開始
    charListSortable = rememberSaveable(stateSaver = Character.ListSaver) {
        mutableStateOf(ArrayList(charList.value))
    }
    filterChoiceArray = rememberMutableStateListJsonOf<FilterEnum>()

    // 如果 charList 為空但 JSON 已可用，預先用已有的數據填充
    // 這確保了首次進入 CharacterListPage 時 scrollbar 就能確定長度
    if (charList.value.isEmpty()) {
        val cachedList = buildCharListFromJson()
        if (cachedList.isNotEmpty()) {
            charList.value = cachedList
            charListSortable.value = cachedList
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun refreshCharList(){
    Character.refreshCharJson()

    val newList = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).async {
            buildCharListFromJson()
        }
        job.await()
        job.getCompleted()
    }
    // 同時更新，減少中間狀態
    charList.value = newList
    charListSortable.value = newList
}

/**
 * 從 JSON 建立角色列表（純計算，可在任何線程執行）
 */
fun buildCharListFromJson(): ArrayList<Character> {
    val tmpCharList = arrayListOf<Character>()
    if (Character.getCharListJson() !is JsonArray) {
        return tmpCharList
    }
    (Character.getCharListJson().jsonArray).fastForEach { jsonElement ->
        tmpCharList.add(
            Character.getCharacterItemFromJSON(
                jsonElement.jsonObject["charId"]?.jsonPrimitive?.content!!,
                requireAttrData = true
            )
        )
    }
    return tmpCharList
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CharacterListPage(
    navigator: NavHostController,
    hazeState: HazeState,
    pageHeader: MutableState<@Composable () -> Unit>,
) {

    val gridState = rememberLazyGridState()

    pageHeader.value = {
        PageHeader(
            navigator = navigator,
            headerData = Screen.CharacterListPage.headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            gridState = gridState
        )
    }

    Box {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .hazeSource(state = hazeState, zIndex = DefaultZIndex),
            columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = gridState,
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(PAGE_HEADER_HEIGHT)
                )
            }
            items(count = charListSortable.value.size, key = { index -> charListSortable.value[index].officialId!! }) { index ->
                CharacterCard(character = charListSortable.value[index], modifier = Modifier.animateItem())
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(LIST_FILTER_TOOL_HEIGHT)
                )
            }
        }

        SG3VerticalScrollbar(gridState = gridState)

        PageBottomMask()

        ListFilterTool(
            originList = charList.value,
            filterType = ListFilterType.CHARACTER,
            filtedList = charListSortable,
            filterChoiceArray = filterChoiceArray,
            hazeState = hazeState
        )
    }
}