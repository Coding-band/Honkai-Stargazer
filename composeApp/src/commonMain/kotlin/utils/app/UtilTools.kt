package utils.app

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.ZipFile
import com.russhwolf.settings.Settings
import com.voc.stargazer3.BuildKonfig
import files.AABoss
import files.AAMission1
import files.AAMission2
import files.AAMission3
import files.IsDone
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
import files.NoDataYet
import files.Res
import files.StatusDays
import files.StatusHours
import files.StatusMinutes
import files.StatusToday
import files.StatusTomorrow
import getAppSpecificDirectory
import getByteArrayByImageBitmap
import getDeviceInfo
import getLocalHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.prepareGet
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import types.CharacterProficient
import types.CombatType
import types.ImageFolder
import types.Path
import ui.screens.ProficientSchool
import utils.annotation.VersionUpdateCheck
import utils.calculator.TeamListItem
import utils.calculator.TeammateItem
import utils.starbase.StarbaseAPI
import writeToFile
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
        floor(number.toDouble() * multiplier)
    } else {
        round(number.toDouble() * multiplier)
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
fun formatDecimalByte(number: Number, decimalPlaces: Int = 1, isRoundDown: Boolean = true, isUnited: Boolean = true): String {
    val multiplier = 10.0.pow(decimalPlaces)
    val roundedNumber = if (isRoundDown) {
        floor(number.toDouble() * multiplier)
    } else {
        round(number.toDouble() * multiplier)
    } / multiplier

    val suffix = when {
        roundedNumber >= 1_048_576L * 1_048_576L -> "TB"
        roundedNumber >= 1_048_576 * 1024 -> "GB"
        roundedNumber >= 1_048_576 -> "MB"
        roundedNumber >= 1_024 -> "KB"
        else -> ""
    }

    val scaledNumber = when (suffix) {
        "TB" -> roundedNumber / (1_048_576L * 1_048_576L)
        "GB" -> roundedNumber / (1_048_576 * 1024)
        "MB" -> roundedNumber / 1_048_576
        "KB" -> roundedNumber / 1_024
        else -> roundedNumber
    }

    val roundedScaledNumber = if (isRoundDown) {
        floor(scaledNumber * multiplier) / multiplier
    } else {
        round(scaledNumber * multiplier) / multiplier
    }

    val parts = roundedScaledNumber.toString().split('.')
    val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
    val decimalPart = parts.getOrNull(1)?.padEnd(decimalPlaces, '0') ?: "0".repeat(decimalPlaces)
    return "$integerPart${if (decimalPlaces > 0) {".$decimalPart"} else {""}}$suffix"
}
fun formatDecimalSci(number: Number, decimalPlaces: Int = 2): String {
    // 處理零的情況
    if (number.toDouble() == 0.0) {
        return "0"
    }

    // 確定正負號並取絕對值
    val sign = if (number.toDouble() < 0) "-" else ""
    val num = abs(number.toDouble())

    // 計算指數
    val exponent = floor(log10(num)).toInt()
    // 計算係數
    val coefficient = num / 10.0.pow(exponent.toDouble())

    // 將係數調整到 [1, 10) 範圍內
    var adjustedCoefficient = coefficient
    var adjustedExponent = exponent
    if (adjustedCoefficient >= 10) {
        adjustedCoefficient /= 10
        adjustedExponent += 1
    }

    // 手動格式化係數到指定小數位數
    val multiplier = 10.0.pow(decimalPlaces.toDouble())
    val rounded = round(adjustedCoefficient * multiplier) / multiplier
    val coeffStr = rounded.toString().let {
        val parts = it.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else ""
        // 補齊小數位數
        val paddedDecimal = decimalPart.padEnd(decimalPlaces, '0').take(decimalPlaces)
        "$integerPart.$paddedDecimal"
    }

    // 組合最終結果
    return "$sign$coeffStr"+"e$adjustedExponent"
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
fun getMocPhaseStrListByMocLen(mocLen: Int = 0): ArrayList<String> {
    if(mocLen == -1) return arrayListOf(removeStrQuote(Res.string.NoDataYet))
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
/**
 * Get Specific AA Phase's Name String by Index
 */
@Composable
fun getAAPhaseStrByIndex(index: Int): String {
    return when (index) {
        0 -> removeStrQuote(Res.string.AAMission1)
        1 -> removeStrQuote(Res.string.AAMission2)
        2 -> removeStrQuote(Res.string.AAMission3)
        3 -> removeStrQuote(Res.string.AABoss)
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
fun getAssetsJsonByFilePath(filePath: String, defaultData : String = "{}"): JsonElement {
    val readStr = readFromFile(filePath, defaultData = defaultData)
    return Json.parseToJsonElement(
        readStr
        //if(readStr == "{}")  getAssetsJsonStrByFilePath(filePath) else readStr
    )
}

fun getAssetsStrByFilePath(filePath: String, defaultData : String = "{}"): String {
    val readStr = readFromFile(filePath, defaultData = defaultData)
    return readStr
        //if(readStr == "{}")  getAssetsJsonStrByFilePath(filePath) else readStr
}

//ref: https://stackoverflow.com/questions/65082734/how-can-i-download-a-large-file-with-ktor-and-kotlin-with-a-progress-indicator
@OptIn(ExperimentalCoroutinesApi::class)
fun downloadFromURLProgress(url: String, downloadProgress: MutableState<Long>, isSuccess: MutableState<Boolean>) {
    val isDownloading = mutableStateOf(false)
    val client = getLocalHttpClient {
        install(HttpTimeout){
            socketTimeoutMillis =  8000
            requestTimeoutMillis = 30*60*1000
        }
        expectSuccess = true
    }
    val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob()) // 定義主線程範圍


    try {
        isSuccess.value =  runBlocking {
            val response: HttpResponse = client.prepareGet(Url(url)) {
                    onDownload { bytesSentTotal, contentLength ->
                        isDownloading.value = true
                        mainScope.launch {
                            downloadProgress.value = bytesSentTotal
                            //println("Download Progress: $bytesSentTotal, Content Length: $contentLength")
                            //println("-----------------")
                        }
                    }
                }.execute()

                //Check whether it is having any errors
                if (!arrayListOf(200,201,408).contains(response.status.value)) {
                    errorLog(
                        "UtilTools.kt",
                        "readFromOnlineURL(url = ${url})",
                        Exception("HTTP Error Code ${response.status.value} : ${response.status.description}")
                    )
                    return@runBlocking false
                } else {
                    val fileSystem = FileSystem.SYSTEM
                    val filePath = getAppSpecificDirectory().resolve("temp").resolve("update.zip")
                    fileSystem.createDirectories(filePath.parent!!, mustCreate = false)

                    val result = async {
                        response.bodyAsChannel().writeToFile(filePath.toString())
                        delay(1000)
                        return@async extractZip(zipPath = filePath, rootPath = getAppSpecificDirectory())
                    }.await()
                    return@runBlocking result
            }
        }

    }catch (e : UnresolvedAddressException){
        //Cannot find the Address, maybe bcz of u are offline
         errorLog("UtilTools.kt", "downloadFromURLProgress(url = ${url}, downloadProgress = ${downloadProgress.value})",e)
        isSuccess.value = false
    }catch (e : Exception){
        // All response
        errorLog("StarbaseRequest", "downloadFromURLProgress(url = ${url}, downloadProgress = ${downloadProgress.value})",e)
        isSuccess.value = false
    }finally {
        client.close()
    }
}

fun extractZip(zipPath: okio.Path, rootPath: okio.Path): Boolean {
    val zipFile = File(zipPath.toString())
    val zipParentFile = File(zipPath.parent.toString())
    val rootFile = File(rootPath.toString())
    return runBlocking {
        try {
            ZipFile(zipFile, FileMode.Read).use { zip ->
                for (entry in zip.entries) {
                    val entryName = entry.name.trimStart('/')
                    // 手動構建目標路徑
                    val destPath = File("${rootFile.path}/$entryName")

                    //println("-------")
                    val fileSystem = FileSystem.SYSTEM
                    val file = destPath.path.toPath()
                    //println("Extracting File: $entryName  (${destPath.path}), isExist: ${fileSystem.exists(file)}")
                    //println("Parent? : ${file.parent}  isExist: ${if(file.parent != null) fileSystem.exists(file.parent!!) else false}")
                    if(file.parent != null && !fileSystem.exists(file.parent!!)){
                        fileSystem.createDirectories(file.parent!!, mustCreate = false)
                        //println("Parent is created: ${fileSystem.exists(file.parent!!)}")
                    }
                    //println("Parent? : ${file.parent}  isExistAfterCreates: ${if(file.parent != null) fileSystem.exists(file.parent!!) else false}")

                    fileSystem.sink(file).buffer().use { sink ->
                        zip.readEntry(entry) { _, content, _, _ ->
                            //println("Sink Write: ${content.size}")
                            sink.write(content)
                        }
                        //println("File $entryName (${destPath.path}) exists: ${fileSystem.exists(file)}")
                    }
                }
            }
            //println("Extracted Zip File: $zipPath")
            zipFile.delete()
            zipParentFile.delete()

            return@runBlocking true
        } catch (e: Exception) {
            errorLog("UtilTools.kt", "extractZip(zipPath = $zipPath, rootPath = $rootPath)", e)
            return@runBlocking false
        }
    }
}

fun checkAssetsUpdate() {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            socketTimeoutMillis =  3000
            requestTimeoutMillis = 8000
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Get the latest release version from GitHub
            val response: HttpResponse = client.get("${StarbaseAPI().getGithubAPIURL()}/releases/latest")
            val jsonResponse = Json.parseToJsonElement(response.body()).jsonObject
            val latestVersion = jsonResponse["tag_name"]?.jsonPrimitive?.content ?: return@launch

            // Read the last version from the file
            val localVersion = Settings().getString("assetsVersion", "")

            // Compare versions
            if (localVersion != latestVersion) {
                // Get the list of changed files between the current version and the latest version
                val diffResponse: HttpResponse = client.get("${StarbaseAPI().getGithubAPIURL()}/compare/$localVersion...$latestVersion")
                val diffJsonResponse = Json.parseToJsonElement(diffResponse.body()).jsonObject
                val files = diffJsonResponse["files"]?.jsonArray ?: return@launch

                // Download and update each changed file
                files.forEach { file ->
                    val filePath = file.jsonObject["filename"]?.jsonPrimitive?.content ?: return@forEach
                    val downloadUrl = "${StarbaseAPI().getGithubAPIURL()}/$latestVersion/$filePath"
                    val fileContent: String = client.get(downloadUrl).body()

                    // Write the updated file content to the local file system
                    writeToFile(filePath, fileContent)
                }

                // Save the latest version to the file
                Settings().getString("assetsVersion", latestVersion)
            }
        } catch (e: Exception) {
            errorLog("UtilTools.kt", "checkAssetsUpdate", e)
        } finally {
            client.close()
        }
    }
}

fun isIosPlatform(): Boolean {
    return listOf("iOS", "iPadOS").contains(getDeviceInfo().deviceOSName)
}

fun isAndroidPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Android")
}

fun isMacOSPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Mac")
}

fun isWindowsPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Windows")
}

fun isLinuxPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Linux")
}

fun isProductionEnv() : Boolean {
    return BuildKonfig.appProfile == "PRODUCTION" || BuildKonfig.appProfile == "PRODUCTION_GP"
}
fun isBetaEnv() : Boolean {
    return BuildKonfig.appProfile == "BETA" || BuildKonfig.appProfile == "C.BETA"
}
fun isDevEnv() : Boolean {
    return BuildKonfig.appProfile == "DEV"
}

lateinit var StatusDays : String
lateinit var StatusHours : String
lateinit var StatusMinutes : String
lateinit var StatusToday : String
lateinit var StatusTomorrow : String
lateinit var StatusFinished : String


@Composable
fun dateTimeStrInit(){
    StatusDays = removeStrQuote(Res.string.StatusDays)
    StatusHours = removeStrQuote(Res.string.StatusHours)
    StatusMinutes = removeStrQuote(Res.string.StatusMinutes)
    StatusToday = removeStrQuote(Res.string.StatusToday)
    StatusTomorrow = removeStrQuote(Res.string.StatusTomorrow)
    StatusFinished = removeStrQuote(Res.string.IsDone)
}

/**
 * Get Remaining Time String
 */
fun getRemainingTimeStr(remainingTime: Int): String {
    val days = remainingTime / (24 * 60 * 60)
    val hours = (remainingTime % (24 * 60 * 60)) / (60 * 60)
    val minutes = (remainingTime % (60 * 60)) / 60

    return (
            if(days > 0) StatusDays.replaceStrRes(days.toString())+" " else ""+
            if(hours > 0) StatusHours.replaceStrRes(hours.toString())+" " else ""+
            if(minutes > 0) StatusMinutes.replaceStrRes(minutes.toString()) else StatusFinished
            )

}

@OptIn(ExperimentalTime::class)
fun getFinishTimeStr(remainingTime: Int): String {
    if (remainingTime == 0) return StatusFinished

    val now = Clock.System.now()
    val tz = TimeZone.currentSystemDefault()
    val finalTime = now.plus(DateTimePeriod(seconds = remainingTime), tz)

    val nowLocale = now.toLocalDateTime(tz)
    val finalLocale = finalTime.toLocalDateTime(tz)

    //check whether now and finalTime is in the same day
    return (if(nowLocale.dayOfYear == finalLocale.dayOfYear) StatusToday else StatusTomorrow).replaceStrRes(
        "${if(finalLocale.hour < 10) "0" else ""}${finalLocale.hour}:${if(finalLocale.minute < 10) "0" else ""}${finalLocale.minute}")
}

@OptIn(ExperimentalTime::class)
fun getFinishTimeStr(finishTime: Long): String {
    val now = Clock.System.now()
    if (finishTime < now.toEpochMilliseconds() / 1000) return StatusFinished

    val tz = TimeZone.currentSystemDefault()
    val finalTime = Instant.fromEpochSeconds(finishTime)

    val nowLocale = now.toLocalDateTime(tz)
    val finalLocale = finalTime.toLocalDateTime(tz)

    //check whether now and finalTime is in the same day
    return (if(nowLocale.dayOfYear == finalLocale.dayOfYear) StatusToday else StatusTomorrow).replaceStrRes(
        "${if(finalLocale.hour < 10) "0" else ""}${finalLocale.hour}:${if(finalLocale.minute < 10) "0" else ""}${finalLocale.minute}")
}


fun writeToFile(filePath: String, content: String, folder: String = "data") {
    val fileSystem = FileSystem.SYSTEM
    //val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("data").resolve(filePath)
    val file = getAppSpecificDirectory().resolve(folder).resolve(filePath)

    try {
        // Create directory if it doesn't exist
        fileSystem.createDirectories(file.parent!!, mustCreate = false)

        fileSystem.write(file) {
            writeUtf8("")
        }

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

fun writeToFileImageBitmap(filePath: String, content: ImageBitmap, folder: String = "data", furtherAction: (filePath: okio.Path) -> Unit = {}) {
    val fileSystem = FileSystem.SYSTEM
    //val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("data").resolve(filePath)
    val file = getAppSpecificDirectory().resolve(folder).resolve(filePath)

    try {
        // Create directory if it doesn't exist
        fileSystem.createDirectories(file.parent!!, mustCreate = false)

        // Write to file
        fileSystem.openReadWrite(file).use { fileHandle ->
            fileHandle.sink().buffer().use { sink ->
                sink.write( getByteArrayByImageBitmap(content))
            }
        }

        furtherAction.invoke(file)

    } catch (e: Exception) {
        errorLog("UtilTools.kt", "writeToFile(${filePath}, ...)", e)
    }
}


fun readFromFile(filePath: String, localOnly : Boolean = false, defaultData : String = "{}"): String {
    val fileSystem = FileSystem.SYSTEM
    //val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("data").resolve(filePath)
    //println(getAppSpecificDirectory())
    val file = getAppSpecificDirectory().resolve("data").resolve(filePath)

    //println("file ${file} is exist: ${fileSystem.exists(file)})")
    try {
        // Check if the file exists
        if (!fileSystem.exists(file)) {

            val data = if(localOnly) defaultData else readFromOnlineURL(StarbaseAPI().getGitHubStaticAssetURL() + "/data/${filePath}", defaultData)
            writeToFile(filePath, data)
            return data
        }

        /*
        if (!localOnly){
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
                    val data = readFromOnlineURL(onlineFileUrl, defaultData)
                    writeToFile(filePath, data)
                    return data
                }
            }catch (e: Exception){
                // No need to response
                errorLog("UtilTools.kt", "readFromFile(...) -> !localOnly", e)
            }
        }
        */
        // Read from file
        return fileSystem.source(file).buffer().use { source ->
            source.readUtf8()
        }
    } catch (e: Exception) {
        errorLog("UtilTools.kt", "readFromFile", e)
    }
    return defaultData
}

/**
 * Read from Online URL
 */
fun readFromOnlineURL(url: String, defaultData: String = "{}"): String {
    val client = getLocalHttpClient {
        install(HttpTimeout){
            socketTimeoutMillis =  3000
            requestTimeoutMillis = 8000
        }
        install(ContentNegotiation){ json() }
        expectSuccess = true
    }

    try {
        return runBlocking {
            return@runBlocking withTimeout(8000) {
                val response: HttpResponse = client.get(url)

                //Check whether it is having any errors
                if (!arrayListOf(200,201,408).contains(response.status.value)) {
                    errorLog(
                        "UtilTools.kt",
                        "readFromOnlineURL(url = ${url})",
                        Exception("HTTP Error Code ${response.status.value} : ${response.status.description}")
                    )
                    return@withTimeout defaultData
                } else {
                    return@withTimeout if(response.body<String>() == "{}" && defaultData != "{}") defaultData else response.body()
                }
            }
        }

    }catch (e : UnresolvedAddressException){
        //Cannot find the Address, maybe bcz of u are offline
       // errorLog("UtilTools.kt", "readFromOnlineURL(url = ${url})",e)
    }catch (e : Exception){
        // All response
        errorLog("UtilTools.kt", "readFromOnlineURL(url = ${url})",e)
    }
    return defaultData
}

/**
 * Path.valueOfWithDefault
 */

fun valueOfWithDefaultPath(name: String): Path {
    return try {
        Path.valueOf(name)
    } catch (e: IllegalArgumentException) {
        Path.Unspecified
    }
}
fun valueOfWithDefaultCombatType(name: String): CombatType {
    return try {
        CombatType.valueOf(name)
    } catch (e: IllegalArgumentException) {
        CombatType.Unspecified
    }
}

@VersionUpdateCheck
fun getIconByUserAccountIconValue(icon : String): Any {
    if(icon.startsWith("http")){
        return icon
    }
    if (icon.length == 6 && icon[2] == '1' || icon.length == 6 && icon[2] == '8'){
        return getAssetsURLByFileName(
            folder = ImageFolder.AVATAR_ICON,
            icon.substring(2)
        )
    }

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
        .replace(Regex("Trailblazer Boy \\(([^)]+)\\)"), { matchResult ->
            "trailblazer_${matchResult.groupValues[1].lowercase()}_male"
        })
        .replace(Regex("Trailblazer Girl \\(([^)]+)\\)"), { matchResult ->
            "trailblazer_${matchResult.groupValues[1].lowercase()}_female"
        })
        .replace("Topaz & Numby","topaz")
        .replaceDanHengRegistName()
        //.replace("Dan Heng • Imbibitor Lunae","dan_heng_il")
        //.replace("Dan Heng • Permansor Terrae","dan_heng_pt")
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
        .replace("@","_")

        .replace("void_ranger","voidranger")

    if(isCharFullImg){
        registNameFinal = registNameFinal
            .replace(Regex("trailblazer_\\w+_male"), "trailblazer_boy")
            .replace(Regex("trailblazer_\\w+_female"), "trailblazer_girl")
    }
    if(isCharNoElement){
        registNameFinal = registNameFinal
            .replace(Regex("trailblazer_\\w+_male"), "trailblazer_male")
            .replace(Regex("trailblazer_\\w+_female"), "trailblazer_female")
    }
    if(isCharNoGen){
        registNameFinal = registNameFinal
            .replace("_male","")
            .replace("_female","")
    }

    return registNameFinal
}

// 處理 "Dan Heng • xxxxxx" 類型角色名
fun String.replaceDanHengRegistName(): String {
    val parts = this.split("•").map { it.trim() }
    if (parts.size == 2) {
        val prefix = parts[0].lowercase().replace(" ", "_")
        val suffix = parts[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.first().lowercaseChar() }
            .joinToString("")
        return "${prefix}_$suffix"
    }
    return this
}

@Composable
fun getAnnotatedStrFromSpan(
    donationDesc: String,
    textStyle: TextStyle = FontSizeNormal14()
): AnnotatedString {
    // 更新正則表達式以匹配 style="color:#xxxxxx"
    val spanRegex = Regex("<span style=\"color:(#[0-9A-Fa-f]{6});\">(.*?)</span>")
    return buildAnnotatedString {
        var lastIndex = 0
        spanRegex.findAll(donationDesc).forEach { matchResult ->
            // 添加匹配前的普通文本
            append(donationDesc.substring(lastIndex, matchResult.range.first))

            // 提取顏色和文本內容
            val colorHex = matchResult.groupValues[1] // e.g., "#DD8200"
            val text = matchResult.groupValues[2] // 內嵌文本

            // 將顏色從 HEX 轉換為 Compose 的 Color 對象
            val color = Color(colorHex.removePrefix("#").toLong(16) or 0xFF000000)

            println("ANNOTATED: ${text}, color = $color")
            // 應用樣式並添加文本
            withStyle(
                style = SpanStyle(
                    color = color,
                    fontSize = textStyle.fontSize,
                    fontFamily = textStyle.fontFamily,
                    fontWeight = textStyle.fontWeight
                )
            ) {
                append(text)
            }

            // 更新 lastIndex 到匹配結束位置
            lastIndex = matchResult.range.last + 1
        }

        // 添加剩餘的文本（如果有）
        if (lastIndex < donationDesc.length) {
            append(donationDesc.substring(lastIndex))
        }
    }
}

/*
 * --------- Type Extension 類別擴充功能 ---------
 */

/**
 * Replace sth like ${1} to your value
 */
fun String.replaceStrRes(newValue: String, index : Int = 1) : String{
    return this.replace("\${$index}", newValue)
}

fun String.replaceStr(replaceValueJsonArray: JsonArray, key: String = "Value") : String{
    var returnString = this

    replaceValueJsonArray.forEachIndexed { index, jsonElement ->
        if(jsonElement is JsonPrimitive){
            val value = jsonElement.double
            val replaceVaule = if(value.toInt().toDouble() == value) {
                formatDecimal(value, 1)
            }else{
                formatDecimal(value, 0)
            }
            returnString = returnString.replace("#${index+1}[i]", replaceVaule)
        }else if(jsonElement.jsonObject[key] != null){
            val value = jsonElement.jsonObject[key]!!.jsonPrimitive.double
            val replaceVaule = if(value.toInt().toDouble() == value) {
                formatDecimal(value, 1)
            }else{
                formatDecimal(value, 0)
            }
            returnString = returnString.replace("${index+1}[i]",replaceVaule)
        }else{
            //... Continue
        }
    }
    return returnString
}

fun String.replaceStrRes(replaceValueArray: ArrayList<String>) : String{
    var returnString = this

    replaceValueArray.forEachIndexed { index, str ->
        returnString = returnString.replace("$"+"{${index+1}}", str)
    }
    return returnString
}

/**
 * Boolean to Int
 */
fun Boolean.toInt() = if (this) 1 else 0

/**
 * Int to Boolean
 */
fun Int.toBoolean() = this != 0

fun JsonObject.toSubJsonElementMap(): Map<String, JsonElement> {
    return this.mapValues { it.value }
}

fun JsonObject.toLocaleMap(): Map<Language.TextLanguage, String> {
    val tmpMap = mutableMapOf<Language.TextLanguage, String>()
    this.map {map ->
        tmpMap[Language.TextLanguage.entries.firstOrNull{it.folderName == map.key} ?: Language.TextLanguage.EN] = map.value.jsonPrimitive.content
    }
    return tmpMap
}

// ref: https://medium.com/@mittalkshitij20/adding-a-custom-scrollbar-to-a-column-in-jetpack-compose-9996c26f498f
@Composable
fun Modifier.verticalColumnScrollbar(
    scrollState: ScrollState,
    width: Dp = 4.dp,
    showScrollBarTrack: Boolean = true,
    scrollBarTrackColor: Color = Color.Gray,
    scrollBarColor: Color = Color.Black,
    scrollBarCornerRadius: Float = 4f,
    endPadding: Float = 12f
): Modifier {
    return drawWithContent {
        // Draw the column's content
        drawContent()
        // Dimensions and calculations
        val viewportHeight = this.size.height
        val totalContentHeight = scrollState.maxValue.toFloat() + viewportHeight
        val scrollValue = scrollState.value.toFloat()
        // Compute scrollbar height and position
        val scrollBarHeight =
            (viewportHeight / totalContentHeight) * viewportHeight
        val scrollBarStartOffset =
            (scrollValue / totalContentHeight) * viewportHeight
        // Draw the track (optional)
        if (showScrollBarTrack) {
            drawRoundRect(
                cornerRadius = CornerRadius(scrollBarCornerRadius),
                color = scrollBarTrackColor,
                topLeft = Offset(this.size.width - endPadding, 0f),
                size = Size(width.toPx(), viewportHeight),
            )
        }
        // Draw the scrollbar
        drawRoundRect(
            cornerRadius = CornerRadius(scrollBarCornerRadius),
            color = scrollBarColor,
            topLeft = Offset(this.size.width - endPadding, scrollBarStartOffset),
            size = Size(width.toPx(), scrollBarHeight)
        )
    }
}

/** 你在想甚麼呀？ */
private fun CosImageOfVocchi(){
    println("No Way...?...ok?")
}

/*
 * --------- Type Saver 各種通用類型的Saver ---------
 */

val JsonElementSaver: Saver<JsonElement, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]) }
)
val JsonObjectSaver: Saver<JsonObject, Any> = listSaver(
    save = { listOf(it.toString()) },
    restore = { Json.parseToJsonElement(it[0]).jsonObject }
)

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

val TeamListItemSaver: Saver<TeamListItem, Any> = listSaver(
    save = { listOf(it.uid, it.teamBuildUnix, it.id, it.teamName, Json.encodeToString(it.teamDataList), it.teamEnemySpeed) },
    restore = {
        TeamListItem(
            uid = it[0] as String,
            teamBuildUnix = it[1] as Long,
            id = it[2] as String,
            teamName = it[3] as String,
            teamDataList = Json.decodeFromString(it[4] as String),
            teamEnemySpeed = it[5] as Int
        )
    }
)

val TeammateItemSaver: Saver<TeammateItem, Any> = listSaver(
    save = { listOf(Json.encodeToString(it.character), it.level, it.energyRechargeRate, it.energyMax, it.speedBase, it.speedRate) },
    restore = {
        TeammateItem(
            character = Json.decodeFromString(it[0] as String),
            level = it[1] as Int,
            energyRechargeRate = it[2] as Float,
            energyMax = it[3] as Int,
            speedBase = it[4] as Float,
            speedRate = it[5] as Float
        )
    }
)

val CharacterProficientSaver: Saver<SnapshotStateList<CharacterProficient>, Any> = listSaver(
    save = { listOf(Json.encodeToString(it.toList())) },
    restore = { Json.decodeFromString(it[0]) as SnapshotStateList<CharacterProficient> }
)
val ProficientSchoolSaver: Saver<SnapshotStateList<ProficientSchool>, Any> = listSaver(
    save = { listOf(Json.encodeToString(it.toMutableList())) },
    restore = { Json.decodeFromString(it[0]) as SnapshotStateList<ProficientSchool> }
)

val TextFieldValueSaver: Saver<TextFieldValue, Any> = listSaver(
    save = { listOf(it.text) },
    restore = { TextFieldValue(it[0] as String) }
)

@Composable
inline fun <reified T: Any> rememberMutableStateListJsonOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(saver = snapshotStateListJsonSaver<T>()) {
        elements.toList().toMutableStateList()
    }
}

inline fun <reified T : Any> snapshotStateListJsonSaver() = listSaver<SnapshotStateList<T>, String>(
    save = { stateList -> listOf(Json.encodeToString(stateList.toList())) },
    restore = { Json.decodeFromString<List<T>>(it[0]).toMutableStateList() }
)