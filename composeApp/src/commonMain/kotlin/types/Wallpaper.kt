package types

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.app.Language
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsURLByFileName

@Serializable
data class Wallpaper(
    val id: String,
    val fileName: String,
    val locale: Map<Language.TextLanguage, String>? = null,
    val requireOwnChar: Boolean = false
){
    companion object {
        val wallpaperList: ArrayList<Wallpaper> = arrayListOf()

        fun initWallpaperList(){
            val jsonArray = getAssetsJsonByFilePath("bgs.json", defaultData = "[]").jsonArray
            val tmpList = jsonArray.map {
                val localeNameJsonObject = it.jsonObject["locale"]
                return@map Wallpaper(
                    id = it.jsonObject["id"]!!.jsonPrimitive.content,
                    fileName = it.jsonObject["fileName"]!!.jsonPrimitive.content,
                    requireOwnChar = it.jsonObject["requireOwnChar"]!!.jsonPrimitive.booleanOrNull ?: false,
                    locale = if (localeNameJsonObject != null && localeNameJsonObject is JsonObject) {
                        val tmpMap = mutableMapOf<Language.TextLanguage, String>()
                        localeNameJsonObject.jsonObject.forEach { (key, value) ->
                            tmpMap[Language.TextLanguage.entries.first { it.folderName == key}] = value.jsonPrimitive.content
                        }
                        tmpMap
                    } else null
                )
            }
            wallpaperList.clear()
            wallpaperList.addAll(tmpList)
        }

        fun getWallpaperById(id: String): Wallpaper? {
            return wallpaperList.find { it.id == id }
        }

        fun getPreferenceWallpaper(): Wallpaper {
            return wallpaperList.find { it.id == Settings().getString("backgroundImage", "221000") } ?: wallpaperList.first()
        }

        fun setPreferenceWallpaper(id: String) {
            Settings().putString("backgroundImage", id)
        }

        fun getWallpaperURLById(wallpaper: Wallpaper): String {
            return getAssetsURLByFileName(ImageFolder.BGS, wallpaper.fileName)
        }
    }
}

