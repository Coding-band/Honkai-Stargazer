/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.voc.honkai_stargazer.component.RelicCard
import components.BackIcon
import components.HeaderData
import components.LIST_FILTER_TOOL_HEIGHT
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.Relic
import utils.PageBottomMask

lateinit var relicList : MutableState<ArrayList<Relic>>
lateinit var relicListSortable : MutableState<ArrayList<Relic>>

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun initRelicList(){
    var isInited by rememberSaveable { mutableStateOf(false) }
    relicList = rememberSaveable(stateSaver = Relic.ListSaver) { mutableStateOf(arrayListOf()) }
    relicListSortable = rememberSaveable(stateSaver = Relic.ListSaver) { (relicList) }
    if(!isInited){
        relicList.value = runBlocking {
            val job = CoroutineScope(Dispatchers.Default).async {
                val tmpList = arrayListOf<Relic>()
                (Relic.relicListJson as JsonArray).fastForEach { jsonElement ->
                    tmpList.add(Relic.getRelicItemFromJSON(jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!))
                }
                return@async tmpList
            }
            job.await()
            job.getCompleted()
        }
        relicListSortable.value = relicList.value
        isInited = true
    }
}

@Composable
fun RelicListPage(modifier: Modifier = Modifier, navigator: Navigator, headerData: HeaderData = defaultHeaderData) {
    val hazeState = remember { HazeState() }

    Box {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .haze(state = hazeState),
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
            items(count = relicListSortable.value.size) { index ->
                RelicCard(relic = relicListSortable.value[index])
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


        PageHeader(navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}