package utils.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
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
import getLocalHttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import okio.FileSystem
import okio.IOException
import okio.SYSTEM
import okio.buffer
import okio.use
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import types.ImageFolder
import utils.annotation.VersionUpdateCheck
import utils.starbase.StarbaseAPI
import kotlin.math.pow
import kotlin.math.roundToInt

/*
 * --------- Deprecated Soon ---------
 */

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
                errorLog("UtilTools","getAssetsWebpByFileName()",e)
                return@async "{}"
            }
        }
        job.await()
        job.getCompleted()
    }
}

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalResourceApi::class)
@Deprecated("Use getAssetsJsonByFilePath() instead", ReplaceWith("getAssetsJsonByFilePath(filePath)"))
fun getAssetsJsonStrByFilePath(filePath: String): String {
    return runBlocking {
        val job = async(Dispatchers.Default) {
            try {
                //Ararar I spent 4hrs on there lol
                val assetString: String = Res.readBytes("files/data/${filePath}").decodeToString()
                return@async assetString
            } catch (e: Exception) {
                // Handle the exception, ErrorLogExporter Please!
                errorLog("UtilTools","getAssetsWebpByFileName()",e)
                return@async "{}"
            }
        }
        job.await()
        job.getCompleted()
    }
}

/*
 * --------- Useful App Function 一些常用的App功能 ---------
 */

/**
 * Remove String Quote
 */
@Composable
fun removeStrQuote(stringResource: StringResource) : String{
    return stringResource(stringResource).removePrefix("\"").removeSuffix("\"")
}

/**
 * Image Loader, Max Cache Size: 1GB (Since Images already reach 400MB in Stargazer 2.7.0)
 */
fun newImageLoader(context : PlatformContext, isDebug: Boolean = false): ImageLoader = ImageLoader.Builder(context)
    .networkCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .diskCache {
        DiskCache.Builder()
            .maxSizeBytes(1024L * 1024 * 1024)
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("image_cache"))
            .build()
    }
    .crossfade(true)
    .logger(if(isDebug) {DebugLogger()} else null)
    .build()

/**
 * Image Request
 */
fun newImageRequest(context: PlatformContext, data: Any, crossFade : Boolean = true) = ImageRequest.Builder(context)
    .data(data)
    .networkCachePolicy(CachePolicy.ENABLED)
    .crossfade(crossFade)
    .diskCachePolicy(CachePolicy.ENABLED)
    .build()

/**
 * Function that use for handling Dec's Format, but I prefer name it as "PrettyCount" :D
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

/**
 * Convert Px to Dp
 */
fun pxToDp(px : Int, density: Float) : Dp {
    return Dp(px / density)
}

/**
 * Convert Dp to Px
 */
fun DpToPx(dp : Dp, density: Float) : Int {
    return (dp.value * density).roundToInt()
}

/**
 * Turn JSON HTML Text to HTML for Webview
 */
fun htmlDescApplier(htmlText: String, levelDataParams: ArrayList<Float>) : String{
    var htmlTextFinal = htmlText

    //Apply all params
    for((index, param) in levelDataParams.withIndex()){
        htmlTextFinal = htmlTextFinal
            .replace("#${(index+1)}[i]%","${formatDecimal((param * 100).toInt(),0)}%" )
            .replace("#${(index+1)}[f1]%","${formatDecimal(param * 100)}%" )
            .replace("#${(index+1)}[i]", formatDecimal(param.toInt(),0))
            .replace("#${(index+1)}[f1]", formatDecimal(param))
    }

    //Imaginary Color Modify
    htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

    //Sparkle Chinese Name Modify
    htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

    return htmlDescApplierImpl(htmlTextFinal)
}
/**
 * Implement of htmlDescApplier
 */
fun htmlDescApplierImpl(htmlText: String) : String{
    var htmlTextFinal = htmlText

    //Imaginary Color Modify
    htmlTextFinal = htmlTextFinal.replace("#F4D258","#D9A800")

    //Sparkle Chinese Name Modify
    htmlTextFinal = htmlTextFinal.replace("花<span style=\"color:#F84F36;\">火</span>","花火")

    return htmlTextFinal
}

/**
 * Get MOC Phase's Name (Res.string) List by MOC Length
 */
@Composable
fun getMocPhaseStrListByMocLen(mocLen: Int): ArrayList<String> {
    val mocPhaseStrList = ArrayList<String>()
    for (index in 0 until mocLen) {
        mocPhaseStrList.add(
            when (index) {
                0 -> removeStrQuote(Res.string.MOCMissionPart1)
                1 -> removeStrQuote(Res.string.MOCMissionPart2)
                2 -> removeStrQuote(Res.string.MOCMissionPart3)
                3 -> removeStrQuote(Res.string.MOCMissionPart4)
                4 -> removeStrQuote(Res.string.MOCMissionPart5)
                5 -> removeStrQuote(Res.string.MOCMissionPart6)
                6 -> removeStrQuote(Res.string.MOCMissionPart7)
                7 -> removeStrQuote(Res.string.MOCMissionPart8)
                8 -> removeStrQuote(Res.string.MOCMissionPart9)
                9 -> removeStrQuote(Res.string.MOCMissionPart10)
                10 -> removeStrQuote(Res.string.MOCMissionPart11)
                11 -> removeStrQuote(Res.string.MOCMissionPart12)
                else -> "?"
            }
        )
    }
    return mocPhaseStrList
}


/**
 * Get Specific MOC Phase's Name String by Index
 */
@Composable
fun getMocPhaseStrByIndex(index: Int): String {
    return when (index) {
        0 -> removeStrQuote(Res.string.MOCMissionPart1)
        1 -> removeStrQuote(Res.string.MOCMissionPart2)
        2 -> removeStrQuote(Res.string.MOCMissionPart3)
        3 -> removeStrQuote(Res.string.MOCMissionPart4)
        4 -> removeStrQuote(Res.string.MOCMissionPart5)
        5 -> removeStrQuote(Res.string.MOCMissionPart6)
        6 -> removeStrQuote(Res.string.MOCMissionPart7)
        7 -> removeStrQuote(Res.string.MOCMissionPart8)
        8 -> removeStrQuote(Res.string.MOCMissionPart9)
        9 -> removeStrQuote(Res.string.MOCMissionPart10)
        10 -> removeStrQuote(Res.string.MOCMissionPart11)
        11 -> removeStrQuote(Res.string.MOCMissionPart12)
        else -> "?"
    }
}

/*
 * --------- Data Process 數據處理功能 ---------
 */

/**
 * Get Assets URL by File Name
 * @param folder ImageFolder
 * @param fileName Only name of the file, without suffix and any slashes
 * E.g. "yunli_icon"
 */
fun getAssetsURLByFileName(folder: ImageFolder, fileName: String): String {
    return StarbaseAPI().getGitHubStaticAssetURL() + "/images/${folder.folderName}/${fileName}${folder.suffix}"
}

/**
 * Get Json from online by File Name
 * @param filePath Relative path of the file, with suffix and any slashes
 * E.g. "character_data/character_list.json"
 */
fun getAssetsJsonByFilePath(filePath: String): JsonElement {
    val readStr = readFromFile(filePath)
    return Json.parseToJsonElement(
        readStr
        //if(readStr == "{}")  getAssetsJsonStrByFilePath(filePath) else readStr
    )
}

fun getAssetsStrByFilePath(filePath: String): String {
    val readStr = readFromFile(filePath)
    return readStr
        //if(readStr == "{}")  getAssetsJsonStrByFilePath(filePath) else readStr
}

fun writeToFile(filePath: String, content: String) {
    val fileSystem = FileSystem.SYSTEM
    val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("data").resolve(filePath)

    try {
        // Create directory if it doesn't exist
        fileSystem.createDirectories(file.parent!!, mustCreate = false)

        // Write to file
        fileSystem.openReadWrite(file).use { fileHandle ->
            fileHandle.sink().buffer().use { sink ->
                sink.writeUtf8(content)
            }
        }
    } catch (e: Exception) {
        errorLog("UtilTools.kt", "writeToFile(${filePath}, ...)", e)
    }
}

fun readFromFile(filePath: String): String {
    val fileSystem = FileSystem.SYSTEM
    val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("data").resolve(filePath)

    try {
        // Check if the file exists
        if (!fileSystem.exists(file)) {
            val data = readFromOnlineURL(StarbaseAPI().getGitHubStaticAssetURL() + "/data/${filePath}")
            writeToFile(filePath, data)
            return data
        }

        try {
            // Get local file size and last modified time
            val localFileSize = fileSystem.metadata(file).size ?: 0L
            val localFileLastModified = fileSystem.metadata(file).lastModifiedAtMillis ?: 0L

            // Get online file size and last modified time
            val onlineFileUrl = StarbaseAPI().getGitHubStaticAssetURL() + "/data/${filePath}"
            val client = getLocalHttpClient {
                install(HttpTimeout) { requestTimeoutMillis = 8000 }
                install(ContentNegotiation) { json() }
                expectSuccess = true
            }

            val response: HttpResponse = runBlocking {
                client.head(onlineFileUrl)
            }

            val onlineFileSize = response.headers[HttpHeaders.ContentLength]?.toLong() ?: 0L
            val onlineFileLastModified = response.headers[HttpHeaders.LastModified]?.let {
                Instant.parse(it).toEpochMilliseconds()
            } ?: 0L

            // Compare file size and last modified time
            if (localFileSize != onlineFileSize || localFileSize != onlineFileSize && localFileLastModified < onlineFileLastModified) {
                val data = readFromOnlineURL(onlineFileUrl)
                writeToFile(filePath, data)
                return data
            }
        }catch (e: UnresolvedAddressException){
            // No need to response
        }

        // Read from file
        return fileSystem.source(file).buffer().use { source ->
            source.readUtf8()
        }
    } catch (e: Exception) {
        errorLog("UtilTools.kt", "readFromFile", e)
    }
    return "{}"
}

/**
 * Read from Online URL
 */
fun readFromOnlineURL(url: String): String {
    val client = getLocalHttpClient {
        install(HttpTimeout){ requestTimeoutMillis = 8000 }
        install(ContentNegotiation){ json() }
        expectSuccess = true
    }

    try {
        return runBlocking {
            return@runBlocking withTimeout(8000) {
                val response: HttpResponse = client.get(url)

                //Check whether it is having any errors
                if (!arrayListOf(200, 201).contains(response.status.value)) {
                    errorLog(
                        "UtilTools.kt",
                        "readFromOnlineURL(url = ${url})",
                        Exception("HTTP Error Code ${response.status.value} : ${response.status.description}")
                    )
                    return@withTimeout "{}"
                } else {
                    return@withTimeout response.body<String>()
                }
            }
        }

    }catch (e : UnresolvedAddressException){
        //Cannot find the Address, maybe bcz of u are offline
       // errorLog("UtilTools.kt", "readFromOnlineURL(url = ${url})",e)
    }catch (e : Exception){
        // All response
        errorLog("StarbaseRequest", "readFromOnlineURL(url = ${url})",e)
    }
    return "{}"
}

@VersionUpdateCheck
fun getIconByUserAccountIconValue(icon : String): Any {
    println(icon)
    if(icon.startsWith("http")){
        return icon
    }
    /*
    else if (icon.length == 4){
        return Character.getCharacterImageFromOfficialId(
            imageFolderType = ImageFolder.CHAR_ICON,
            icon
        )
    }else if (icon.length == 6 && icon[2] == '1' || icon.length == 6 && icon[2] == '8'){
        return Character.getCharacterImageFromOfficialId(
            imageFolderType = ImageFolder.CHAR_ICON,
            icon.substring(2)
        )
    }
     */

    else if(icon == ""){
        return getAssetsURLByFileName(
            folder = ImageFolder.AVATAR_ICON,
            "Anonymous"
        )
    }else {
        return getAssetsURLByFileName(
            folder = ImageFolder.AVATAR_ICON,
            icon
        )
    }
}

/**
 * Get Assets Webp by File Name
 * Better to check at every version update
 */
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

        .replace("sam",if ((registName.startsWith("sam") || registName.lowercase() === "sam") && !registName.startsWith("sampo")) "firefly" else "sam")

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

/*
 * --------- Type Extension 類別擴充功能 ---------
 */

/**
 * Replace sth like ${1} to your value
 */
@Composable
fun String.replaceStrRes(newValue: String, index : Int = 1) : String{
    return this.replace("\${$index}", newValue)
}

/**
 * Boolean to Int
 */
fun Boolean.toInt() = if (this) 1 else 0

/**
 * Int to Boolean
 */
fun Int.toBoolean() = this != 0

/** 你在想甚麼呀？ */
private fun CosImageOfVocchi(){
    println("No Way...?...ok?")
}

val JsonElementSaver: Saver<JsonElement, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]) }
)

private var sJob: Job? = null
