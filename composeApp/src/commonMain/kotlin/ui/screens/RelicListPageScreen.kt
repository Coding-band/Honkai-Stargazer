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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
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
import types.Relic
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.LIST_FILTER_TOOL_HEIGHT
import ui.components.ListFilterTool
import ui.components.ListFilterType
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.RelicCard
import ui.components.defaultHeaderData
import ui.navigation.Screen
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.DefaultZIndex
import utils.app.PageBottomMask

lateinit var relicList : MutableState<ArrayList<Relic>>
lateinit var relicListSortable : MutableState<ArrayList<Relic>>

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun initRelicList(){
    relicList = rememberSaveable(stateSaver = Relic.ListSaver) { mutableStateOf(arrayListOf()) }
    relicListSortable = rememberSaveable(stateSaver = Relic.ListSaver) { (mutableStateOf(ArrayList(relicList.value))) }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun refreshRelicList(){
    Relic.refreshRelicJson()

    relicList.value = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).async {
            val tmpList = arrayListOf<Relic>()
            if (Relic.getRelicListJson() !is JsonArray) {
                return@async tmpList
            }
            (Relic.getRelicListJson().jsonArray).fastForEach { jsonElement ->
                tmpList.add(Relic.getRelicItemFromJSON(jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!))
            }
            return@async tmpList
        }
        job.await()
        job.getCompleted()
    }
    relicListSortable.value = relicList.value
}

@Composable
fun RelicListPage(
    navigator: NavHostController,
    hazeState: HazeState
) {

    Box {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .hazeSource(state = hazeState, zIndex = DefaultZIndex),
            columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = rememberLazyGridState(),
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(PAGE_HEADER_HEIGHT)
                )
            }
            items(count = relicListSortable.value.size, key = { index -> relicListSortable.value[index].officialId!!}) { index ->
                RelicCard(relic = relicListSortable.value[index], modifier = Modifier.animateItem())
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(LIST_FILTER_TOOL_HEIGHT)
                )
            }
        }


        PageBottomMask()

        ListFilterTool(
            originList = relicList.value,
            filterType = ListFilterType.RELIC,
            filtedList = relicListSortable,
            filterChoiceArray = filterChoiceArray,
            hazeState = hazeState
        )

        PageHeader(navigator, headerData = Screen.RelicListPage.headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}