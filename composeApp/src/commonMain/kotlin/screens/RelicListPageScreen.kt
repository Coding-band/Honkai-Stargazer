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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.RelicCard
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.Relic
import utils.JsonArraySaver
import utils.Language
import utils.PageBottomMask

@Composable
fun RelicListPage(modifier: Modifier = Modifier, navigator: Navigator, headerData: HeaderData = defaultHeaderData) {
    val hazeState = remember { HazeState() }
    val relicListJSON: JsonArray by rememberSaveable(stateSaver = JsonArraySaver) { mutableStateOf(Relic.getRelicListFromJSON() as JsonArray) }
    val relicNameList: ArrayList<String> = rememberSaveable { arrayListOf() }
    var isInited by rememberSaveable { mutableStateOf(false) }

    if(!isInited) {
        isInited = true
        relicListJSON.forEach { jsonElement ->
            val localeName: String? = Relic.getRelicDataFromJSON(
                jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!,
                Language.TextLanguageInstance
            ).jsonObject["name"]?.jsonPrimitive?.content

            if (localeName !== null) {
                relicNameList.add(localeName)
            }
        }
    }

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
            items(count = relicListJSON.size) { index ->
                val relicListItem = relicListJSON.jsonArray[index]
                RelicCard(
                    relic = Relic(
                        registName = relicListItem.jsonObject["name"]?.jsonPrimitive?.content,
                        fileName = relicListItem.jsonObject["fileName"]?.jsonPrimitive?.content,
                        officialId = relicListItem.jsonObject["fileName"]?.jsonPrimitive?.int,
                        displayName = relicNameList[index]
                        ),
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(0.dp)
                )
            }
        }


        PageBottomMask()


        PageHeader(navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}