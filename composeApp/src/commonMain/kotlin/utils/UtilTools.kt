package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.addLastModifiedToFileCacheKey
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import files.MOCMissionPart1
import files.MOCMissionPart10
import files.MOCMissionPart11
import files.MOCMissionPart12
import files.MOCMissionPart2
import files.MOCMissionPart3
import files.MOCMissionPart4
import files.MOCMissionPart5
import files.MOCMissionPart6
import files.MOCMissionPart7
import files.MOCMissionPart8
import files.MOCMissionPart9
import files.Res
import getImageBitmapByByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import okio.FileSystem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import types.Character
import utils.annotation.DoItLater
import utils.annotation.VersionUpdateCheck
import kotlin.math.pow
import kotlin.math.roundToInt

class UtilTools {
    enum class ImageFolderType(val folderName: String, val suffix: String) {
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
        MONSTER_ICON("monster_icon", "_icon.webp"),
        ORMANENT_ICON("ornament_icon", ".webp"),
        ORMANENT_PC_ICON("ornament_pcicon", ".webp"),
        RELIC_ICON("relic_icon", ".webp"),
        RELIC_PC_ICON("relic_pcicon", ".webp"),
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getLostImgByteArray() : ByteArray {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                val assetByte: ByteArray = Res.readBytes("files/ico_lost_img.webp")
                return@async (assetByte)
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
                } catch (e: Exception) {
                    if(ImageFolderType.CHAR_FULL == folderType){
                        return@async getAssetsWebpByFileName(ImageFolderType.CHAR_SPLASH, fileName)
                    }else{
                        errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                        return@async getImageBitmapByByteArray(Res.readBytes("files/ico_lost_img.webp"))
                    }

                }
            }
            job.await()
            job.getCompleted()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getAssetsWebpByteArrayByFileName(folderType: ImageFolderType, fileName: String): ByteArray {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    return@async (Res.readBytes("files/images/${folderType.folderName}/${fileName}${folderType.suffix}"))
                } catch (e: Exception) {
                    if(ImageFolderType.CHAR_FULL == folderType){
                        return@async getAssetsWebpByteArrayByFileName(ImageFolderType.CHAR_SPLASH, fileName)
                    }else {
                        errorLogExport("UtilTools", "getAssetsWebpByFileName()", e)
                        return@async (Res.readBytes("files/ico_lost_img.webp"))
                    }
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
            val job = async(Dispatchers.Default) {
                try {
                    return@async Json.parseToJsonElement(getAssetsStringByFilePath(filePath))
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async Json.parseToJsonElement("{}")
                }
            }
            job.await()
            job.getCompleted()
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    inline
            /**
             * You must know the basic structure it is, JsonArray or JsonObject
             * E.g. getAssetsJsonByContext("character_data/character_list.json").jsonArray[0]
             */
    fun <reified T> getAssetsJsonByFilePathWithIgnore(filePath: String): T {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    return@async Json {ignoreUnknownKeys = true} .decodeFromString<T>(getAssetsStringByFilePath(filePath))
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsJsonByFilePathWithIgnore()",e)
                    return@async Json {ignoreUnknownKeys = true} .decodeFromString<T>("{}")
                }
            }

            job.await()
            job.getCompleted()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    fun getAssetsStringByFilePath(filePath: String): String{
        return runBlocking {
            val job = async(Dispatchers.IO) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                    return@async assetString
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async "{}"
                }
            }
            job.await()
            job.getCompleted()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
    fun getAssetsJsonStrByFilePath(filePath: String): String {
        return runBlocking {
            val job = async(Dispatchers.Default) {
                try {
                    //Ararar I spent 4hrs on there lol
                    val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                    return@async assetString
                } catch (e: Exception) {
                    // Handle the exception, ErrorLogExporter Please!
                    errorLogExport("UtilTools","getAssetsWebpByFileName()",e)
                    return@async "{}"
                }
            }
            job.await()
            job.getCompleted()
        }
    }

    @Composable
    fun getMocPhaseStrListByMocLen(mocLen: Int): ArrayList<String> {
        val mocPhaseStrList = ArrayList<String>()
        for (index in 0 until mocLen) {
            mocPhaseStrList.add(
                when (index) {
                    0 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart1)
                    1 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart2)
                    2 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart3)
                    3 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart4)
                    4 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart5)
                    5 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart6)
                    6 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart7)
                    7 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart8)
                    8 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart9)
                    9 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart10)
                    10 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart11)
                    11 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart12)
                    else -> "?"
                }
            )
        }
        return mocPhaseStrList
    }


    @Composable
    fun getMocPhaseStrByIndex(index: Int): String {
        return when (index) {
            0 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart1)
            1 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart2)
            2 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart3)
            3 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart4)
            4 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart5)
            5 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart6)
            6 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart7)
            7 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart8)
            8 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart9)
            9 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart10)
            10 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart11)
            11 -> UtilTools().removeStringResDoubleQuotes(Res.string.MOCMissionPart12)
            else -> "?"
        }
    }

    fun htmlDescApplier(htmlText: String, levelDataParams: ArrayList<Float>) : String{
        var htmlTextFinal = htmlText

        //Apply all params
        for((index, param) in levelDataParams.withIndex()){
            htmlTextFinal = htmlTextFinal
                .replace("#${(index+1)}[i]%","${UtilTools().formatDecimal((param * 100).toInt(),0)}%" )
                .replace("#${(index+1)}[f1]%","${UtilTools().formatDecimal(param * 100)}%" )
                .replace("#${(index+1)}[i]", UtilTools().formatDecimal(param.toInt(),0))
                .replace("#${(index+1)}[f1]", UtilTools().formatDecimal(param))
        }

        //Imaginary Color Modify
        htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

        //Sparkle Chinese Name Modify
        htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

        return htmlDescApplierImpl(htmlTextFinal)
    }

    fun htmlDescApplierImpl(htmlText: String) : String{
        var htmlTextFinal = htmlText

        //Imaginary Color Modify
        htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

        //Sparkle Chinese Name Modify
        htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

        return htmlTextFinal
    }

    @VersionUpdateCheck
    fun getIconByUserAccountIconValue(icon : String): Any {
        println(icon)
        if(icon == ""){
            return UtilTools().getAssetsWebpByteArrayByFileName(
                folderType = ImageFolderType.AVATAR_ICON,
                "Anonymous"
            )
        }else if(icon.startsWith("http")){
            return icon
        }else if (icon.length == 4){
            return Character.getCharacterImageFromOfficialId(
                imageFolderType = ImageFolderType.CHAR_ICON,
                icon
            )
        }else if (icon.length == 6 && icon[2] == '1' || icon.length == 6 && icon[2] == '8'){
            return Character.getCharacterImageFromOfficialId(
                imageFolderType = ImageFolderType.CHAR_ICON,
                icon.substring(2)
            )
        }else {
            return UtilTools().getAssetsWebpByteArrayByFileName(
                folderType = ImageFolderType.AVATAR_ICON,
                icon
            )
        }
    }

    //RegistName means the Official EN Name
    @VersionUpdateCheck
    fun getImageNameByRegistName(registName: String, isCharFullImg: Boolean = false, isCharNoElement : Boolean = false, isCharNoGen: Boolean = false) : String {
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

            .replace("void_ranger","voidranger")

        if(isCharFullImg){
            registNameFinal = registNameFinal
                .replace("trailblazer_physical_male","trailblazer_boy")
                .replace("trailblazer_fire_male","trailblazer_boy")
                .replace("trailblazer_imaginary_male","trailblazer_boy")
                .replace("trailblazer_physical_female","trailblazer_girl")
                .replace("trailblazer_fire_female","trailblazer_girl")
                .replace("trailblazer_imaginary_female","trailblazer_girl")
        }
        if(isCharNoElement){
            registNameFinal = registNameFinal
                .replace("trailblazer_physical_male","trailblazer_male")
                .replace("trailblazer_fire_male","trailblazer_male")
                .replace("trailblazer_imaginary_male","trailblazer_male")
                .replace("trailblazer_physical_female","trailblazer_female")
                .replace("trailblazer_fire_female","trailblazer_female")
                .replace("trailblazer_imaginary_female","trailblazer_female")
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

    @Composable
    fun removeStringResDoubleQuotesTest(stringResource: utils.res.StringResource) : String{
        return utils.res.stringResource(stringResource).removePrefix("\"").removeSuffix("\"")
    }

    fun pxToDp(px : Int, density: Float) : Dp {
        return Dp(px / density)
    }
    fun DpToPx(dp : Dp, density: Float) : Int {
        return (dp.value * density).roundToInt()
    }

    @Deprecated("Replaced by Kotlinx-DateTime")
            /**
             *     val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]") }
             *     dateFormat.format(timeStamp.toLocalDateTime(TimeZone.currentSystemDefault()))
             */
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

    /**
     * Function that use for handling Dec's Format
     */
    fun formatDecimal(number: Number, decimalPlaces: Int = 1, isRoundDown: Boolean = false, isUnited: Boolean = false): String {
        val multiplier = 10.0.pow(decimalPlaces)
        val roundedNumber = if (isRoundDown) {
            kotlin.math.floor(number.toDouble() * multiplier)
        } else {
            kotlin.math.round(number.toDouble() * multiplier)
        } / multiplier

        val suffix = when {
            roundedNumber >= 1_000_000_000_000 -> "T"
            roundedNumber >= 1_000_000_000 -> "B"
            roundedNumber >= 1_000_000 -> "M"
            roundedNumber >= 1_000 -> "K"
            else -> ""
        }

        val scaledNumber = when (suffix) {
            "T" -> roundedNumber / 1_000_000_000_000
            "B" -> roundedNumber / 1_000_000_000
            "M" -> roundedNumber / 1_000_000
            "K" -> roundedNumber / 1_000
            else -> roundedNumber
        }

        val parts = if(isUnited){scaledNumber}else{roundedNumber}.toString().split('.')
        val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
        val decimalPart = parts.getOrNull(1)?.padEnd(decimalPlaces, '0') ?: "0".repeat(decimalPlaces)
        return if (isUnited) {
            "$integerPart${if (decimalPlaces > 0) {".$decimalPart"} else {""}}$suffix"
        } else {
            "$integerPart${if (decimalPlaces > 0) {".$decimalPart"} else {""}}"
        }
    }

    @VersionUpdateCheck
    @DoItLater("Remember to remove if already implemented!")
    class TemporaryFunction(){
        fun getCharWeightListJson() : JsonElement{
            return UtilTools().getAssetsJsonByFilePath("charWeightList.json")
        }
    }

    fun newImageLoader(context : PlatformContext, logger: DebugLogger? = null): ImageLoader = ImageLoader.Builder(context)
        .networkCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.1)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(0.03)
                .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("image_cache"))
                .build()
        }
        .logger(DebugLogger())
        .addLastModifiedToFileCacheKey(true)
        .build()

    fun newImageRequest(context: PlatformContext, data: Any, crossFade : Boolean = true) = ImageRequest.Builder(context)
        .data(data)
        .networkCachePolicy(CachePolicy.ENABLED)
        .crossfade(crossFade)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    fun <T> swapItemsInArray(array: ArrayList<T>, fromIndex: Int, toIndex: Int) {
        val temp = array[fromIndex]
        array[fromIndex] = array[toIndex]
        array[toIndex] = temp
    }


}

val JsonArraySaver: Saver<JsonArray, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]).jsonArray }
)
val JsonElementSaver: Saver<JsonElement, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]) }
)

@Composable
fun String.replaceStrRes(newValue: String, index : Int = 1) : String{
    return this.replace("$"+"{$index}", newValue)
}

val BooleanSaver: Saver<Boolean, Any> = autoSaver()

private fun CosImageOfVocchi(){
    /** 你在想甚麼呀？ */
}

fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this != 0