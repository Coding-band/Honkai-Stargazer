package utils.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import type.Character
import type.ImageFolder
import utils.annotation.VersionUpdateCheck
import utils.starbase.StarbaseAPI
import kotlin.math.pow
import kotlin.math.roundToInt

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

/*
 * --------- Data Process 數據處理功能 ---------
 */

/**
 * Get Assets URL by File Name, NO NEED TO SPECIFIC SUFFIX (Since it will be handled by ImageFolder)
 */
fun getAssetsURLByFileName(folder: ImageFolder, fileName: String): String {
    return StarbaseAPI().getGitHubStaticAssetURL() + "/images/${folder.folderName}/${fileName}${folder.suffix}"
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