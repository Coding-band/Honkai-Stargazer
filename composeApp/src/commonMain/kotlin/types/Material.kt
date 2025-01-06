package types


import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.annotation.DoItLater
import utils.annotation.VersionUpdateCheck
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsURLByFileName

@Serializable
open class Material(
    var officialId: Int,
    var name: String?,
    @IntRange(1,5) var rarity: Int,
    var count: Int,
    var isDisplayCount : Boolean = false,
) {
    @DoItLater("Rearrange those function later")
    companion object {
        val materialListJson = getMaterialListFromJSON()

        private fun getMaterialListFromJSON() : JsonElement {
            return getAssetsJsonByFilePath("material_list.json")
        }

        fun getMaterialImageById(officialId: Int): String {
            return getAssetsURLByFileName(ImageFolder.MATERIAL_ICON,"material_" + materialListJson.jsonObject[officialId.toString()]?.jsonPrimitive?.content)
        }
    }

}