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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.voc.honkai_stargazer.component.CHAR_CARD_WIDTH
import com.voc.honkai_stargazer.component.CharacterCard
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
import types.Character
import types.CombatType
import types.Path
import utils.UtilTools
import utils.navigation.RootContent
import utils.navigation.Screen

@Composable
fun CharacterListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData
) {
    val hazeState = remember { HazeState() }
    val charListJSON: JsonArray = Character.getCharacterListFromJSON() as JsonArray
    val charNameList: ArrayList<String> = arrayListOf()
    charListJSON.forEach { jsonElement ->
        val localeName : String? = Character.getCharacterDataFromFileName(
            jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!, UtilTools.TextLanguage.ZH_HK
        ).jsonObject["name"]?.jsonPrimitive?.content

        if(localeName !== null){
            charNameList.add(localeName)
        }

    }
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
                        .height(LISTHEADER_HEIGHT)
                )
            }
            items(count = charListJSON.size) { index ->
                val charListItem = charListJSON.jsonArray[index]
                CharacterCard(
                    character = Character(
                        registName = charListItem.jsonObject["name"]?.jsonPrimitive?.content,
                        fileName = charListItem.jsonObject["fileName"]?.jsonPrimitive?.content,
                        combatType = CombatType.valueOf(charListItem.jsonObject["element"]?.jsonPrimitive?.content!!),
                        rarity = charListItem.jsonObject["rare"]?.jsonPrimitive?.int!!,
                        path = Path.valueOf(charListItem.jsonObject["path"]?.jsonPrimitive?.content!!),
                    ),
                    displayName = charNameList[index],
                    onClick = {
                        val charName = charListItem.jsonObject["name"]?.jsonPrimitive?.content!!;
                        val fileName = charListItem.jsonObject["fileName"]?.jsonPrimitive?.content!!;
                        navController.navigate(
                            Screen.CharacterInfoPage.route
                                  + "/${charName}"
                                  + "/${fileName}"
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
        ListHeader(navController = navController, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
    }
}

@Preview
@Composable
fun CharacterListPagePreview() {
    RootContent(
        screen = Screen.CharacterListPage,
        navController = rememberNavController(),
        page = {
            CharacterListPage(
                headerData = Screen.CharacterListPage.headerData,
                navController = rememberNavController()
            )
        })
}