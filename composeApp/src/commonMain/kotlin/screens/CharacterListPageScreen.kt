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
import com.voc.honkai_stargazer.component.CharacterCard
import components.BackIcon
import components.HeaderData
import components.ListFilterTool
import components.ListFilterType
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import types.Character
import types.Constants.Companion.CHAR_CARD_WIDTH
import utils.JsonArraySaver
import utils.PageBottomMask
import utils.navigation.Screen
import utils.navigation.navigateLimited

@Composable
fun CharacterListPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val hazeState = remember { HazeState() }
    val charListJSON: JsonArray by rememberSaveable(stateSaver = JsonArraySaver) { mutableStateOf(Character.getCharacterListFromJSON() as JsonArray) }
    //val charNameList: ArrayList<String> = rememberSaveable { arrayListOf<String>() }
    var isInited by rememberSaveable { mutableStateOf(false) }
    val charList by rememberSaveable { mutableStateOf(arrayListOf<Character>()) }

    if(!isInited){
        isInited = true
        println("charListJSON inited")
        charListJSON.forEach { jsonElement ->
            charList.add(Character.getCharacterItemFromJSON(jsonElement.jsonObject["charId"]?.jsonPrimitive?.content!!,))
        }
    }
    var charListSortable by rememberSaveable { mutableStateOf(charList) }

    /*
       val charList = arrayListOf<Character>()
    val charBitmaps : ArrayList<Bitmap> = arrayListOf<Bitmap>()

    for( x in (0..<charListJSON.length())){
        val charListItem : JSONObject = charListJSON.getJSONObject(x)
        charList.add(Character(
            registName = charListItem.getString("name"),
            fileName = charListItem.getString("fileName"),
            combatType = CombatType.valueOf(charListItem.getString("element")),
            rarity = charListItem.getInt("rare"),
            path = Path.valueOf(charListItem.getString("path")),
        ))
        charBitmaps.add(
            Character.getCharacterImageFromJSON(
                LocalContext.current,
                UtilTools.ImageFolderType.CHAR_ICON,
                charListItem.getString("name")
            )
        )
    }
     */

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
            items(count = charListSortable.size) { index ->
                val charListItem = charListSortable[index]
                CharacterCard(
                    character = charListSortable[index],
                    onClick = {
                        navigator.navigateLimited(
                            Screen.CharacterInfoPage.route
                                  + "/${charListItem.registName?.replace(" ","_")}"
                                  + "?fileName=${charListItem.fileName}"
                                  + "&combatType=${charListItem.combatType.name}"
                                  + "&path=${charListItem.path.name}"
                                  + "&charId=${charListItem.officialId}"
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

        PageBottomMask()

        ListFilterTool(
            filterList = charList,
            filterType = ListFilterType.CHARACTER,
            onFilterApplied = { filteredList ->
                charListSortable = filteredList
            }
        )


        PageHeader(navigator = navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}