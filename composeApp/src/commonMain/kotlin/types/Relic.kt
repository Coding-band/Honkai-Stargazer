/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Lightcone.Companion.lcExtListJson
import utils.Language
import utils.UtilTools
import utils.hoyolab.AttributeExchange


@Serializable
open class Relic(
    var officialId : Int? = -1,
    var registName : String? = "Unknown", //EN Official Name
    var fileName : String? = "",
    var rarity : Int? = 5, //其實沒甚麼用 因爲肯定是五星的
    var displayName : String? = "未知", //Localed Name,

    var level: Int = -1,
    var properties: ArrayList<HsrProperties> = arrayListOf(),
){
    companion object {
        val relicListJson = getRelicListFromJSON()
        val relicExtListJson = getRelicExtListFromJSON()

        private fun getRelicListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/relic_list.json")
        }
        private fun getRelicExtListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/relic_ext_list.json")
        }

        fun getRelicDataFromJSON(relicFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/${textLanguage.folderName}/${relicFileName}.json")
        }

        fun getRelicImageFromJSON(imageFolderType: UtilTools.ImageFolderType, imageFileName : String, index: Int = -1) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName("${imageFileName}${if(index > 0) {"_${index}"} else ""}"))
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getRelicItemFromJSON(relicFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : Relic {
            return runBlocking {
                val job = async(Dispatchers.Default){
                    val listDataJson = relicListJson.jsonArray.find { relicData -> relicData.jsonObject["fileName"]!!.jsonPrimitive.content == relicFileName } ?: return@async Relic()
                    val listExtDataJson = relicExtListJson.jsonArray.firstOrNull { relicData -> relicData.jsonObject["officialId"]!!.jsonPrimitive.content == relicFileName } ?: return@async Relic()

                    return@async Relic(
                        officialId = relicFileName.toInt(),
                        fileName = relicFileName,
                        registName = (listDataJson.jsonObject["name"]?.jsonPrimitive?.content),
                        displayName = listExtDataJson.jsonObject["localeName"]!!.jsonObject[textLanguage.folderName]?.jsonPrimitive?.content ?: "?",
                    )
                }
                job.await()
                job.getCompleted()
            }
        }

        fun getRelicIdFromHoyoRelicId(hoyoRelicId: Int) : Int {
            return when(hoyoRelicId){
                55001 -> 101
                55002 -> 101
                55003 -> 102
                55004 -> 103
                55005 -> 103
                else -> if(hoyoRelicId >= 10000){
                    (hoyoRelicId % 10000) / 10
                } else {
                    hoyoRelicId
                }
            }
        }
        val Saver: Saver<Relic, Any> = Saver(
            save = { Json.encodeToString(it) },
            restore = { Json.decodeFromString<Relic>(it as String) }
        )
        val ListSaver: Saver<ArrayList<Relic>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }



}

@Serializable
data class HsrProperties(
    var attributeExchange: AttributeExchange = AttributeExchange.ATTREX_UNKNOWN,
    var valueFinal: Float = 0f,
    var valueBase: Float = 0f,
    var valueAdd: Float = 0f,
    var times: Int = 0,
){
    companion object{
        fun turnStrToValue(str: String) : Float {
            return if(str.contains("%")) {
                str.replace("%","").toFloat() / 100
            } else {
                str.toFloat()
            }
        }
    }

}