/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

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
import utils.app.Language
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.app.valueOfWithDefaultPath
import utils.calculator.AttrData


@Serializable
open class Lightcone(
    var officialId : Int? = -1,
    var registName : String? = "Unknown", //EN Name Allow, for Image
    var fileName : String? = "",
    var rarity : Int = 4,
    var path : Path = Path.Unspecified,
    var version : String = "1.0.0",
    var displayName : String? = "未知",
    var lcAttrData: AttrData? = null,

    @IntRange(1,5) var superimposition : Int = -1,
    var level : Int = -1,
){
    @DoItLater("Rearrange those function later")
    companion object {
        val lcListJson = getLightconeListFromJSON()
        val lcExtListJson = getLightconeExtListFromJSON()

        private fun getLightconeListFromJSON() : JsonElement {
            return getAssetsJsonByFilePath("lightcone_data/lightcone_list.json", defaultData = "[]")
        }
        private fun getLightconeExtListFromJSON() : JsonElement {
            return getAssetsJsonByFilePath("lightcone_data/lightcone_ext_list.json", defaultData = "[]")
        }

        fun getLightconeDataFromJSON(lightconeFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return getAssetsJsonByFilePath("lightcone_data/${textLanguage.folderName}/${lightconeFileName}.json")
        }

        fun getLightconeImageFromJSON(imageFolderType: ImageFolder, lightconeName : String) : String {
            return getAssetsURLByFileName(imageFolderType, getImageNameByRegistName(lightconeName))
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
                        path = (valueOfWithDefaultPath(listDataJson.jsonObject["path"]!!.jsonPrimitive.content)),
                        version = (listDataJson.jsonObject["version"]!!.jsonPrimitive.content),
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