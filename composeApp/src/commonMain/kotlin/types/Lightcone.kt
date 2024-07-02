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
import utils.UtilTools


@Serializable
open class Lightcone(
    var officialId : Int? = 21018,
    var registName : String? = "Dance! Dance! Dance!",
    var fileName : String? = "21018",
    var rarity : Int = 4,
    var path : Path = Path.Harmony,
    var releaseVersion : String = "1.0.0",

    @IntRange(1,5) var eidolon : Int = 1,
){
    companion object {
        fun getLightconeListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/lightcone_list.json")
        }

        fun getLightconeDataFromJSON(lightconeFileName : String, textLanguage: UtilTools.TextLanguage) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("lightcone_data/${textLanguage.folderName}/${lightconeFileName}.json")
        }

        fun getLightconeImageFromJSON(imageFolderType: UtilTools.ImageFolderType, lightconeName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(lightconeName))
        }
    }
}