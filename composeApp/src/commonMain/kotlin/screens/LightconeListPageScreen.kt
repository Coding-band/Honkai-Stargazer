/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package com.voc.honkai_stargazer.screen

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.voc.honkai_stargazer.component.CHAR_CARD_WIDTH
import com.voc.honkai_stargazer.component.LightconeCard
import components.BackIcon
import components.HeaderData
import components.LISTHEADER_HEIGHT
import components.ListHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Lightcone
import types.Path
import utils.UtilTools
import utils.navigation.RootContent
import utils.navigation.Screen

@Composable
fun LightconeListPage(modifier: Modifier = Modifier, navController: NavController, headerData: HeaderData = defaultHeaderData) {
    val hazeState = remember { HazeState() }
    val lcListJSON: JsonArray = Lightcone.getLightconeListFromJSON() as JsonArray
    val lcNameList: ArrayList<String> = arrayListOf()
    lcListJSON.forEach { jsonElement ->
        val localeName : String? = Lightcone.getLightconeDataFromJSON(
            jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!, UtilTools.TextLanguage.ZH_HK
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
                        .height(LISTHEADER_HEIGHT)
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
                    ),
                    displayName = lcNameList[index]
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
        ListHeader(navController = navController, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}

@Preview
@Composable
fun LightconeListPagePreview() {
    RootContent(
        screen = Screen.LightconeListPage,
        navController = rememberNavController(),
        page = {LightconeListPage(navController = rememberNavController()) })
}