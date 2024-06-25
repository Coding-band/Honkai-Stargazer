/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package types

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.json.JsonElement
import utils.UtilTools



open class Character(
    var officialId : Int? = 1006,
    var registName : String? = "Silver Wolf",
    var fileName : String? = "silverwolf",
    var rarity : Int = 5,
    var path : Path = Path.Harmony,
    var combatType: CombatType = CombatType.Quantum,
    var gender : Gender = Gender.Female,
    var releaseVersion : String = "1.0.0",
){
    enum class Gender{
        Male, Female
    }
    companion object {
        fun getCharacterListFromJSON() : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/character_list.json")
        }

        fun getCharacterDataFromFileName(characterFileName : String, textLanguage: UtilTools.TextLanguage) : JsonElement {
            return UtilTools().getAssetsJsonByFilePath("character_data/${textLanguage.folderName}/${characterFileName}.json")
        }

        /**
         * composeResources/files/files/images/character_icon/jade_icon.webp
         */
        fun getCharacterImageFromFileName(imageFolderType: UtilTools.ImageFolderType, characterName : String) : ImageBitmap {
            return UtilTools().getAssetsWebpByFileName(imageFolderType, UtilTools().getImageNameByRegistName(characterName))
        }
    }
}