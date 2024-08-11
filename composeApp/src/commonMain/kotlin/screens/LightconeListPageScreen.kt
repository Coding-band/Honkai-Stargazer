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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.LightconeCard
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
import types.Lightcone
import types.Path
import utils.Language
import utils.navigation.Screen

@Composable
fun LightconeListPage(modifier: Modifier = Modifier, navigator: Navigator, headerData: HeaderData = defaultHeaderData) {
    val hazeState = remember { HazeState() }
    val lcListJSON: JsonArray = Lightcone.getLightconeListFromJSON() as JsonArray
    val lcNameList: ArrayList<String> = arrayListOf()
    lcListJSON.forEach { jsonElement ->
        val localeName : String? = Lightcone.getLightconeDataFromJSON(
            jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!, Language.TextLanguageInstance
        ).jsonObject["name"]?.jsonPrimitive?.content

        if(localeName !== null){
            lcNameList.add(localeName)
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
            items(count = lcListJSON.size) { index ->
                val lcListItem = lcListJSON.jsonArray[index]
                LightconeCard(
                    lightcone = Lightcone(
                        registName = lcListItem.jsonObject["name"]?.jsonPrimitive?.content,
                        fileName = lcListItem.jsonObject["fileName"]?.jsonPrimitive?.content,
                        rarity = lcListItem.jsonObject["rare"]?.jsonPrimitive?.int!!,
                        path = Path.valueOf(lcListItem.jsonObject["path"]?.jsonPrimitive?.content!!),
                        displayName = lcNameList[index]
                        ),
                    onClick = {
                        val lcName = lcListItem.jsonObject["name"]?.jsonPrimitive?.content!!;
                        val fileName = lcListItem.jsonObject["fileName"]?.jsonPrimitive?.content!!;
                        navigator.navigate(
                            Screen.LightconeInfoPage.route
                                    + "/${lcName}"
                                    + "?fileName=${fileName}"
                                    + "&path=${lcListItem.jsonObject["path"]?.jsonPrimitive?.content!!}"
                        )
                    }
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
        PageHeader(navigator = navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}