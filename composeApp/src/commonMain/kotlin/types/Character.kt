/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.UtilTools


@Serializable
open class Character(
    var officialId: Int? = -1,
    var registName: String? = "Unknown",
    var fileName: String? = "unknown",
    var localName: String? = "未知",
    @IntRange(4, 5) var rarity: Int = 4,
    var path: Path = Path.Unspecified,
    var combatType: CombatType = CombatType.Unspecified,
    var gender: Gender = Gender.Unspecified,

    //For Character Status
    var characterStatus: CharacterStatus? = null,
    var displayName: String? = "?",
    var version: String? = "1.0.0",

    ){
    enum class Gender{
        Male, Female, Unspecified
    }
    companion object {
        fun getCharacterListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/character_list.json")
        }

        fun getCharacterDataFromFileName(characterFileName : String, textLanguage: UtilTools.TextLanguage = UtilTools.TextLanguage.EN) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/${textLanguage.folderName}/${characterFileName}.json")
        }

        /**
         * composeResources/files/files/images/character_icon/jade_icon.webp
         */
        fun getCharacterImageFromFileName(imageFolderType: UtilTools.ImageFolderType, characterName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(characterName, (imageFolderType === UtilTools.ImageFolderType.CHAR_FULL)))
        }


        fun getCharacterItemFromJSON(charId : String, textLanguage: UtilTools.TextLanguage = UtilTools.TextLanguage.EN) : Character {
            val listDataJson = getCharacterListFromJSON().jsonArray.find { lcData -> lcData.jsonObject["charId"]!!.jsonPrimitive.content == charId } ?: return Character(path = Path.Unspecified, )

            val dataJson = getCharacterDataFromFileName(listDataJson.jsonObject["fileName"]!!.jsonPrimitive.content, textLanguage)

            return Character(
                officialId = charId.toInt(),
                fileName = listDataJson.jsonObject["fileName"]!!.jsonPrimitive.content,
                registName = (listDataJson.jsonObject["name"]!!.jsonPrimitive.content),
                rarity = dataJson.jsonObject["rarity"]!!.jsonPrimitive.int,
                path = (Path.valueOf(listDataJson.jsonObject["path"]!!.jsonPrimitive.content)),
                version = (listDataJson.jsonObject["version"]!!.jsonPrimitive.content),
                displayName = dataJson.jsonObject["name"]!!.jsonPrimitive.content,
                combatType = (CombatType.valueOf(listDataJson.jsonObject["element"]!!.jsonPrimitive.content)),
            )
        }
    }
}