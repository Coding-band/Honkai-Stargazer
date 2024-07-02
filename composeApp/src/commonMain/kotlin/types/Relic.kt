/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import utils.UtilTools


@Serializable
open class Relic(
    var officialId : Int? = 21018,
    var registName : String? = "Dance! Dance! Dance!",
    var fileName : String? = "21018",
    var rarity : Int? = 5, //其實沒甚麼用 因爲肯定是五星的
    var releaseVersion : String = "1.0.0",
){
    companion object {
        fun getRelicListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/relic_list.json")
        }

        fun getRelicDataFromJSON(relicFileName : String, textLanguage: UtilTools.TextLanguage) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("relic_data/${textLanguage.folderName}/${relicFileName}.json")
        }

        fun getRelicImageFromJSON(imageFolderType: UtilTools.ImageFolderType, imageFileName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(imageFileName))
        }
    }
}