/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package type

import androidx.annotation.IntRange
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.annotation.DoItLater
import utils.app.Constants.Companion.LOST_IMAGE_DRAWABLE
import utils.app.Language
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.calculator.AttrData


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
    var characterAttrData: AttrData? = null,
    ){
    enum class Gender{
        Male, Female, Unspecified
    }



    @DoItLater("Rearrange those function later")
    companion object {
        val charListJson = getCharacterListFromJSON()
        val charExtListJson = getCharacterExtListFromJSON()

        private fun getCharacterListFromJSON() : JsonElement {
            return Json.parseToJsonElement(getAssetsJsonByFilePath("character_data/character_list.json"))
        }
        private fun getCharacterExtListFromJSON() : JsonElement {
            return Json.parseToJsonElement(getAssetsJsonByFilePath("character_data/character_ext_list.json"))
        }

        fun getCharacterDataFromFileName(characterFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return Json.parseToJsonElement(getAssetsJsonByFilePath("character_data/${textLanguage.folderName}/${characterFileName}.json"))
        }
        fun getCharacterImageFromFileName(imageFolderType: ImageFolder, characterName : String) : String {
            return getAssetsURLByFileName(imageFolderType, getImageNameByRegistName(characterName, (imageFolderType === ImageFolder.CHAR_FULL)))
        }

        fun getCharacterImageFromOfficialId(imageFolderType: ImageFolder, charId : String) : Any {
            val listDataJson = charListJson.jsonArray.find { data -> data.jsonObject["charId"]!!.jsonPrimitive.content == charId } ?: return LOST_IMAGE_DRAWABLE
            return getCharacterImageFromFileName(imageFolderType, listDataJson.jsonObject["name"]!!.jsonPrimitive.content)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getCharacterItemFromJSON(charId : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance, requireAttrData: Boolean = false) : Character {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    val listDataJson = charListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["charId"]!!.jsonPrimitive.content == charId } ?: return@async Character(path = Path.Unspecified, )
                    val listExtDataJson = charExtListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["officialId"]!!.jsonPrimitive.content == charId } ?: return@async Character(path = Path.Unspecified, )

                    return@async Character(
                        officialId = charId.toInt(),
                        fileName = listDataJson.jsonObject["fileName"]!!.jsonPrimitive.content,
                        registName = (listDataJson.jsonObject["name"]!!.jsonPrimitive.content),
                        rarity = listDataJson.jsonObject["rare"]!!.jsonPrimitive.int,
                        path = (Path.valueOf(listDataJson.jsonObject["path"]!!.jsonPrimitive.content)),
                        version = (listDataJson.jsonObject["version"]!!.jsonPrimitive.content),
                        displayName = listExtDataJson.jsonObject["localeName"]!!.jsonObject[textLanguage.folderName]?.jsonPrimitive?.content ?: "?",
                        combatType = (CombatType.valueOf(listDataJson.jsonObject["element"]!!.jsonPrimitive.content)),
                        characterAttrData = if(requireAttrData){ Json.decodeFromJsonElement<AttrData>(listExtDataJson.jsonObject["attrData"]!!) } else { null },
                    )
                }
                job.await()
                job.getCompleted()
            }
        }

        val Saver: Saver<Character, Any> = Saver(
            save = { Json.encodeToString(it) },
            restore = { Json.decodeFromString<Character>(it as String) }
        )
        val ListSaver: Saver<ArrayList<Character>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }
}