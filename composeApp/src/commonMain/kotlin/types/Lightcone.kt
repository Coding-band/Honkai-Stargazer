/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.annotation.IntRange
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope
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
import types.Character.Companion.charExtListJson
import types.Character.Companion.charListJson
import utils.Language
import utils.UtilTools
import utils.calculator.AttrData
import utils.calculator.getCharAttrData
import utils.calculator.getLcAttrData


@Serializable
open class Lightcone(
    var officialId : Int? = -1,
    var registName : String? = "Unknown", //EN Name Allow, for Image
    var fileName : String? = "",
    var rarity : Int = 4,
    var path : Path = Path.Unspecified,
    var releaseVersion : String = "1.0.0",
    var displayName : String? = "未知",
    var lcAttrData: AttrData? = null,

    @IntRange(1,5) var superimposition : Int = -1,
    var level : Int = -1,
){
    companion object {
        val lcListJson = getLightconeListFromJSON()
        val lcExtListJson = getLightconeExtListFromJSON()

        private fun getLightconeListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/lightcone_list.json")
        }
        private fun getLightconeExtListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/lightcone_ext_list.json")
        }

        fun getLightconeDataFromJSON(lightconeFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/${textLanguage.folderName}/${lightconeFileName}.json")
        }

        fun getLightconeImageFromJSON(imageFolderType: UtilTools.ImageFolderType, lightconeName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(lightconeName))
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getLightconeItemFromJSON(lightconeFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance, requireAttrData : Boolean = false) : Lightcone {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    if(lightconeFileName == "-1") return@async Lightcone()

                    val listDataJson = lcListJson.jsonArray.firstOrNull { lcData -> lcData.jsonObject["fileName"]!!.jsonPrimitive.content == lightconeFileName } ?: return@async Lightcone()
                    val listExtDataJson = lcExtListJson.jsonArray.firstOrNull { lcData -> lcData.jsonObject["officialId"]!!.jsonPrimitive.content == lightconeFileName } ?: return@async Lightcone()

                    return@async Lightcone(
                        officialId = lightconeFileName.toInt(),
                        fileName = lightconeFileName,
                        registName = (listDataJson.jsonObject["name"]!!.jsonPrimitive.content),
                        rarity = listDataJson.jsonObject["rare"]!!.jsonPrimitive.int,
                        path = (Path.valueOf(listDataJson.jsonObject["path"]!!.jsonPrimitive.content)),
                        releaseVersion = (listDataJson.jsonObject["version"]!!.jsonPrimitive.content),
                        displayName = listExtDataJson.jsonObject["localeName"]!!.jsonObject[textLanguage.folderName]?.jsonPrimitive?.content ?: "?",
                        lcAttrData = if(requireAttrData){ Json.decodeFromJsonElement<AttrData>(listExtDataJson.jsonObject["attrData"]!!) } else { null },
                    )
                }
                job.await()
                job.getCompleted()
            }
        }

        val Saver: Saver<Lightcone, Any> = Saver(
            save = { Json.encodeToString(it) },
            restore = { Json.decodeFromString<Lightcone>(it as String) }
        )
        val ListSaver: Saver<ArrayList<Lightcone>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }
}