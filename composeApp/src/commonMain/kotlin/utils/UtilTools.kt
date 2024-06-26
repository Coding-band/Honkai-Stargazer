package utils

import androidx.compose.ui.graphics.ImageBitmap
import files.Res
import getImageBitmapByByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okio.IOException
import org.jetbrains.compose.resources.ExperimentalResourceApi

class UtilTools {

    //App語言 Language for App (R.string)
    enum class AppLanguage(var localeName: String, var folderName: String) {
        VOCCHINESE("Vocchinese", "yue"),
        EN("English", "en"),
        ZH_CN("简体中文", "zh_cn"),
        ZH_HK("繁體中文", "zh_hk"),
        JP("日本語", "jp"),
        FR("Français", "fr"),
        RU("Русский", "ru"),
        DE("Deutsch", "de"),
        PT("Português", "pt-PT"),
        VI("tiếng Việt", "vi"),
        ES("Español", "es-ES"),
        KR("한국어", "kr"),
        TH("ภาษาไทย", "th"),
        JYU_YAM("ㄓㄨˋ ㄧㄣ", "zh"),
        UK("Українська", "uk");
    }

    //文本語言 Language for Text (assets/data)
    enum class TextLanguage(var localeName: String, var folderName: String) {
        EN("English", "en"),
        ZH_CN("简体中文", "zh_cn"),
        ZH_HK("繁體中文", "zh_hk"),
        JP("日本語", "jp"),
        FR("Français", "fr"),
        RU("Русский", "ru"),
        DE("Deutsch", "de"),
        PT("Português", "pt-PT"),
        VI("tiếng Việt", "vi"),
        ES("Español", "es-ES"),
        KR("한국어", "kr"),
        TH("ภาษาไทย", "th"),
    }

    enum class ImageFolderType(var folderName: String, var suffix: String) {
        AVATAR_ICON("avatar_icon", ".webp"),
        BGS("bgs", ".webp"),

        CHAR_EIDOLON("character_eidolon", "_eidolon_${1}.webp"),
        CHAR_EIDOLON_BORDER("character_eidolon_border", ".svg"),
        CHAR_FADE("character_fade", "_fade.webp"),
        CHAR_FULL("character_full", "_full.webp"),
        CHAR_ICON("character_icon", "_icon.webp"),
        CHAR_SKILL("character_skill", "_skill.webp"),
        CHAR_SKILL_TREE("character_skill_tree", ".webp"),
        CHAR_SOUL("character_soul", "_soul_${1}.webp"),
        CHAR_SPLASH("character_splash", "_splash.webp"),

        LC_ARTWORK("lightcone_artwork", "_artwork.webp"),
        LC_ICON("lightcone_icon", ".webp"),
        MAOGOU("maogou", ".webp"),
        MATERIAL_ICON("material_icon", ".webp"),
        MONSTER_ICON("monster_icon", ".webp"),
        ORMANENT_ICON("ornament_icon", "_${1}.webp"),
        ORMANENT_PC_ICON("ornament_pcicon", ".webp"),
        RELIC_ICON("relic_icon", "_${1}.webp"),
        RELIC_PC_ICON("relic_pcicon", ".webp"),
    }


    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getAssetsWebpByFileName(folderType: ImageFolderType, fileName: String): ImageBitmap {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetByte: ByteArray = Res.readBytes("files/images/${folderType.folderName}/${fileName}${folderType.suffix}")
                    return@async getImageBitmapByByteArray(assetByte)
                } catch (e: IOException) {
                    // Handle the exception, ErrorLogExporter Please!
                    return@async getImageBitmapByByteArray(Res.readBytes("files/ico_lost_img.webp"))
                }
            }
            job.await()
            job.getCompleted()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
            /**
             * You must know the basic structure it is, JsonArray or JsonObject
             * E.g. getAssetsJsonByContext("character_data/character_list.json").jsonArray[0]
             */
    fun getAssetsJsonByFilePath(filePath: String): JsonElement {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                    return@async Json.parseToJsonElement(assetString)
                } catch (e: IOException) {
                    // Handle the exception, ErrorLogExporter Please!
                    throw e
                }
            }
            job.await()
            job.getCompleted()
        }
    }


    @VersionUpdateCheck
    fun getImageNameByRegistName(registName: String) : String {
        return registName
            .replace("Trailblazer Boy (Physical)","trailblazer_physical_male")
            .replace("Trailblazer Girl (Physical)","trailblazer_physical_female")
            .replace("Trailblazer Boy (Fire)","trailblazer_fire_male")
            .replace("Trailblazer Girl (Fire)","trailblazer_fire_female")
            .replace("Trailblazer Boy (Imaginary)","trailblazer_imaginary_male")
            .replace("Trailblazer Girl (Imaginary)","trailblazer_imaginary_female")
            .replace("Topaz & Numby","topaz")
            .replace("Dan Heng • Imbibitor Lunae","dan_heng_il")
            .replace("Void","Void_")

            .lowercase()

            .replace("sam",if (registName.lowercase() === "sam") "firefly" else "sam")

            .replace("(","")
            .replace(")","")
            .replace("!","")
            .replace("?","")
            .replace(":","")
            .replace(",","")
            .replace("\"","")
            .replace("\'","")
            .replace(".","")
            .replace(" ","_")
            .replace("-","_")
    }
}