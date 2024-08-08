/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
        fun getRelicListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/relic_list.json")
        }

        fun getRelicDataFromJSON(relicFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/${textLanguage.folderName}/${relicFileName}.json")
        }

        fun getRelicImageFromJSON(imageFolderType: UtilTools.ImageFolderType, imageFileName : String, index: Int = -1) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName("${imageFileName}${if(index > 0) {"_${index}"} else ""}"))
        }

        fun getRelicItemFromJSON(relicFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : Relic {
            val dataJson = getRelicDataFromJSON(relicFileName, textLanguage)
            val listDataJson = getRelicListFromJSON().jsonArray.find { lcData -> lcData.jsonObject["fileName"]!!.jsonPrimitive.content == relicFileName }

            return Relic(
                officialId = relicFileName.toInt(),
                fileName = relicFileName,
                registName = (if(listDataJson != null) listDataJson.jsonObject["name"]!!.jsonPrimitive.content else "None"),
                displayName = dataJson.jsonObject["name"]!!.jsonPrimitive.content,
            )
        }

        fun getRelicIdFromHoyoRelicId(hoyoRelicId: Int) : Int {
            return if(hoyoRelicId >= 10000){
                (hoyoRelicId % 10000) / 10
            } else {
                hoyoRelicId
            }
        }
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