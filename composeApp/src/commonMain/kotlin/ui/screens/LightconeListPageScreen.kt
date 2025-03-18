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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Lightcone
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.LIST_FILTER_TOOL_HEIGHT
import ui.components.LightconeCard
import ui.components.ListFilterTool
import ui.components.ListFilterType
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import ui.navigation.Screen
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.DefaultZIndex
import utils.app.PageBottomMask

lateinit var lcList : MutableState<ArrayList<Lightcone>>
lateinit var lcListSortable : MutableState<ArrayList<Lightcone>>

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun initLcList(){
    lcList = rememberSaveable(stateSaver = Lightcone.ListSaver) { mutableStateOf(arrayListOf()) }
    lcListSortable = rememberSaveable(stateSaver = Lightcone.ListSaver) { mutableStateOf(ArrayList(lcList.value)) }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun refreshLcList(){
    lcList.value = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).async {
            val tmpLcList = arrayListOf<Lightcone>()
            if (Lightcone.lcListJson !is JsonArray) {
                return@async tmpLcList
            }
            Lightcone.lcListJson.fastForEach { jsonElement ->
                tmpLcList.add(Lightcone.getLightconeItemFromJSON(jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!, requireAttrData = true))
            }
            return@async tmpLcList
        }
        job.await()
        job.getCompleted()
    }
    lcListSortable.value = lcList.value
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LightconeListPage(
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
            items(count = lcListSortable.value.size, key = { index -> lcListSortable.value[index].officialId!!}) { index ->
                LightconeCard(lightcone = lcListSortable.value[index], modifier = Modifier.animateItem())
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
            originList = lcList.value,
            filterType = ListFilterType.LIGHTCONE,
            filtedList = lcListSortable,
            filterChoiceArray = filterChoiceArray,
            hazeState = hazeState
        )

        PageHeader(navigator = navigator, headerData = Screen.LightconeListPage.headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}