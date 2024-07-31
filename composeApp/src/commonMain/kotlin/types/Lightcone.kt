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
import utils.Language
import utils.UtilTools


@Serializable
open class Lightcone(
    var officialId : Int? = 21018,
    var registName : String? = "Dance! Dance! Dance!", //EN Name Allow, for Image
    var fileName : String? = "21018",
    var rarity : Int = 4,
    var path : Path = Path.Harmony,
    var releaseVersion : String = "1.0.0",
    var displayName : String? = "舞！舞！舞！",

    @IntRange(1,5) var superimposition : Int = -1,
    var level : Int = -1,
){
    companion object {
        fun getLightconeListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/lightcone_list.json")
        }

        fun getLightconeDataFromJSON(lightconeFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/${textLanguage.folderName}/${lightconeFileName}.json")
        }

        fun getLightconeImageFromJSON(imageFolderType: UtilTools.ImageFolderType, lightconeName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(lightconeName))
        }

        fun getLightconeItemFromJSON(lightconeFileName : String, textLanguage: Language.TextLanguage = Language.TextLanguageInstance) : Lightcone {
            if(lightconeFileName == "-1") return Lightcone()
            val dataJson = getLightconeDataFromJSON(lightconeFileName, textLanguage)
            val listDataJson = getLightconeListFromJSON().jsonArray.find { lcData -> lcData.jsonObject["fileName"]!!.jsonPrimitive.content == lightconeFileName }

            return Lightcone(
                officialId = lightconeFileName.toInt(),
                fileName = lightconeFileName,
                registName = (if(listDataJson != null) listDataJson.jsonObject["name"]!!.jsonPrimitive.content else "None"),
                rarity = dataJson.jsonObject["rarity"]!!.jsonPrimitive.int,
                path = (if(listDataJson != null) Path.valueOf(listDataJson.jsonObject["path"]!!.jsonPrimitive.content) else Path.Unspecified),
                releaseVersion = (if(listDataJson != null) listDataJson.jsonObject["version"]!!.jsonPrimitive.content else "-1"),
                displayName = dataJson.jsonObject["name"]!!.jsonPrimitive.content,
            )
        }
    }
}