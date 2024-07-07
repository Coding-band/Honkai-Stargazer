package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import utils.annotation.VersionUpdateCheck
import kotlin.math.roundToInt

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

        CHAR_EIDOLON("character_eidolon", ".webp"),
        CHAR_EIDOLON_BORDER("character_eidolon_border", ".svg"),
        CHAR_FADE("character_fade", "_fade.webp"),
        CHAR_FULL("character_full", "_full.webp"),
        CHAR_ICON("character_icon", "_icon.webp"),
        CHAR_SKILL("character_skill", ".webp"),
        CHAR_SKILL_TREE("character_skill_tree", ".webp"),
        CHAR_SOUL("character_soul", ".webp"),
        CHAR_SPLASH("character_splash", "_splash.webp"),

        LC_ARTWORK("lightcone_artwork", "_artwork.webp"),
        LC_ICON("lightcone_icon", ".webp"),
        MAOGOU("maogou", ".webp"),
        MATERIAL_ICON("material_icon", ".webp"),
        MONSTER_ICON("monster_icon", ".webp"),
        ORMANENT_ICON("ornament_icon", ".webp"),
        ORMANENT_PC_ICON("ornament_pcicon", ".webp"),
        RELIC_ICON("relic_icon", ".webp"),
        RELIC_PC_ICON("relic_pcicon", ".webp"),
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getLostImgBitmap() : ImageBitmap {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                val assetByte: ByteArray = Res.readBytes("files/ico_lost_img.webp")
                return@async getImageBitmapByByteArray(assetByte)
            }
            job.await()
            job.getCompleted()
        }
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
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
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
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async Json.parseToJsonElement("{}")
                }
            }
            job.await()
            job.getCompleted()
        }
    }


    @VersionUpdateCheck
    fun getImageNameByRegistName(registName: String, isCharFullImg: Boolean = false, isCharNoGen: Boolean = false) : String {
        var registNameFinal = registName
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

            .replace("sam",if (registName.startsWith("sam") || registName.lowercase() === "sam") "firefly" else "sam")

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

        if(isCharFullImg){
            registNameFinal = registNameFinal
                .replace("trailblazer_physical_male","trailblazer_boy")
                .replace("trailblazer_fire_male","trailblazer_boy")
                .replace("trailblazer_imaginary_male","trailblazer_boy")
                .replace("trailblazer_physical_female","trailblazer_girl")
                .replace("trailblazer_fire_female","trailblazer_girl")
                .replace("trailblazer_imaginary_female","trailblazer_girl")
        }
        if(isCharNoGen){
            registNameFinal = registNameFinal
                .replace("_male","")
                .replace("_female","")
        }

        return registNameFinal
    }

    @Composable
    fun removeStringResDoubleQuotes(stringResource: StringResource) : String{
        return stringResource(stringResource).removePrefix("\"").removeSuffix("\"")
    }

    fun pxToDp(px : Int, density: Float) : Dp {
        return Dp(px / density)
    }
    fun DpToPx(dp : Dp, density: Float) : Int {
        return (dp.value * density).roundToInt()
    }

    fun unixTimestampToFormattedString(timestamp: Long): String {
        // 計算時間各部分
        val secondsInMinute = 60
        val secondsInHour = 60 * secondsInMinute
        val secondsInDay = 24 * secondsInHour

        // Unix 時間戳是從 1970-01-01 開始的秒數
        val baseYear = 1970
        var secondsRemaining = timestamp / 1000

        // 計算年份
        var year = baseYear
        while (true) {
            val leapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
            val daysInYear = if (leapYear) 366 else 365
            val secondsInCurrentYear = daysInYear * secondsInDay
            if (secondsRemaining >= secondsInCurrentYear) {
                secondsRemaining -= secondsInCurrentYear
                year++
            } else {
                break
            }
        }

        // 計算月份和日期
        val months = intArrayOf(31, if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var month = 1
        for (daysInMonth in months) {
            val secondsInMonth = daysInMonth * secondsInDay
            if (secondsRemaining >= secondsInMonth) {
                secondsRemaining -= secondsInMonth
                month++
            } else {
                break
            }
        }

        val day = (secondsRemaining / secondsInDay) + 1
        secondsRemaining %= secondsInDay

        // 計算小時、分鐘和秒
        val hour = secondsRemaining / secondsInHour
        secondsRemaining %= secondsInHour

        val minute = secondsRemaining / secondsInMinute
        val second = secondsRemaining % secondsInMinute

        // 格式化結果
        val yearStr = year.toString().padStart(4, '0')
        val monthStr = month.toString().padStart(2, '0')
        val dayStr = day.toString().padStart(2, '0')
        val hourStr = hour.toString().padStart(2, '0')
        val minuteStr = minute.toString().padStart(2, '0')
        val secondStr = second.toString().padStart(2, '0')

        return "$yearStr-$monthStr-$dayStr $hourStr:$minuteStr:$secondStr"
    }

}