package types

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.app.Language
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsURLByFileName
import utils.app.readFromOnlineURL
import utils.starbase.StarbaseAPI

@Serializable
data class Wallpaper(
    val id: String,
    val fileName: String,
    val locale: Map<Language.TextLanguage, String>? = null,
    val requireOwnChar: Boolean = false
){
    companion object {
        val wallpaperList: ArrayList<Wallpaper> = arrayListOf()
        val DEFAULT_WALLPAPER = Wallpaper("221000", "221000", mapOf(
                Language.TextLanguage.ZH_CN to "无名路途",
                Language.TextLanguage.ZH_HK to "無名路途",
                Language.TextLanguage.JP to "ナナシの道",
                Language.TextLanguage.KR to "무명객의 여정",
                Language.TextLanguage.ES to "Camino de los Anónimos",
                Language.TextLanguage.FR to "Voyage des Sans Noms",
                Language.TextLanguage.RU to "Путешествие Безымянных",
                Language.TextLanguage.TH to "การเดินทางนิรนาม",
                Language.TextLanguage.VI to "Hành Trình Không Tên",
                Language.TextLanguage.DE to "Abenteuer der Namenlosen",
                Language.TextLanguage.PT to "Jornada Inominada",
                Language.TextLanguage.EN to "Nameless Journey"
            ),
            requireOwnChar = false
        )

        fun initWallpaperList(){
            val jsonStr = readFromOnlineURL("${StarbaseAPI().getGitHubStaticAssetURL()}/data/bgs.json", defaultData = "[]")
            val jsonArray = Json.parseToJsonElement(jsonStr)

            if(jsonArray is JsonNull || jsonArray !is JsonArray || jsonArray.isEmpty()) return

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

        fun getPreferenceWallpaper(): Wallpaper {
            return wallpaperList.find { it.id == Settings().getString("backgroundImage", "221000") } ?: wallpaperList.firstOrNull() ?: DEFAULT_WALLPAPER
        }

        fun setPreferenceWallpaper(id: String) {
            Settings().putString("backgroundImage", id)
        }

        fun getWallpaperURLById(wallpaper: Wallpaper): String {
            return getAssetsURLByFileName(ImageFolder.BGS, wallpaper.fileName)
        }
    }
}

