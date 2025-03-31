package utils.hoyolab

import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import utils.app.Preferences
import kotlin.math.floor
import kotlin.random.Random

/**
 * Ref: PizzaHelperUnited (https://github.com/pizza-studio/PizzaHelperUnited/blob/main/Packages/PZKit/Sources/PZAccountKit/HoYoAPIs/LoginRelated/GenerateDeviceFingerPrintAPI/GetDeviceFingerPrint.swift)
 */
fun getDeviceFingerPrint(
    platform : HoyolabRequest.PLATFORM = HoyolabRequest.PLATFORM.HOYOLAB,
    length: Int = 16,
    forceReset: Boolean = false
) : String {
    // Step 0, Check if force reset
    val time = Clock.System.now().toEpochMilliseconds()
    if(forceReset || Preferences.AppSettingsClass().getDeviceFP() != ""){
        Preferences.AppSettingsClass().setDeviceFPRefreshTime(time)
    }else{
        return Preferences.AppSettingsClass().getDeviceFP()
    }

    // Step 1, Initialize seed time, device id
    val seed_time = (time / 1000) * 1000
    val device_id = md5HexTotal(genRandomHex()+ time.toString())
    val seed_id = genRandomHex()

    // Step 2, Generate device fingerprint
    val initialRandomFp = md5HexTotal(device_id).substring(0, 13)

    // Step 3, Generate HTTP REQUEST
    val host = when(platform){
        HoyolabRequest.PLATFORM.HOYOLAB -> "https://sg-public-data-api.hoyoverse.com"
        HoyolabRequest.PLATFORM.MIYOUSHE -> "https://public-data-api.mihoyo.com"
    }
    val platformId = when(platform){
        HoyolabRequest.PLATFORM.HOYOLAB -> 1
        HoyolabRequest.PLATFORM.MIYOUSHE -> 5
    }
    val body = mapOf(
        "seed_id" to seed_id,
        "device_id" to device_id,
        "platform" to platformId,
        "seed_time" to seed_time,
        "ext_fields" to getFpExtFields(device_id, platform),
        "app_name" to platform.name,
        "device_fp" to initialRandomFp
    )

    println(body)

    return initialRandomFp
}

fun genRandomHex(length: Int = 16): String {
    val characters = "0123456789abcdef"
    return (1..length).map { characters.random() }.joinToString("")
}

/**
 * From PizzaHelperUnited (https://github.com/pizza-studio/PizzaHelperUnited/blob/main/Packages/PZKit/Sources/PZAccountKit/HoYoAPIs/LoginRelated/GenerateDeviceFingerPrintAPI/GetDeviceFingerPrint.swift)
 */
fun getFpExtFields(deviceId: String, platform: HoyolabRequest.PLATFORM): String {
    return when (platform) {
        HoyolabRequest.PLATFORM.MIYOUSHE -> """
                {"ramCapacity":"3746","hasVpn":"0","proxyStatus":"0","screenBrightness":"0.550","packageName":"com.miHoYo.mhybbs","romRemain":"100513","deviceName":"iPhone","isJailBreak":"0","magnetometer":"-160.495300x-206.488358x58.534348","buildTime":"1706406805675","ramRemain":"97","accelerometer":"-0.419876x-0.748367x-0.508057","cpuCores":"6","cpuType":"CPU_TYPE_ARM64","packageVersion":"2.20.1","gyroscope":"0.133974x-0.051780x-0.062961","batteryStatus":"45","appUpdateTimeDiff":"1707130080397","appMemory":"57","screenSize":"414×896","vendor":"--","model":"iPhone12,5","IDFV":"${deviceId.uppercase()}","romCapacity":"488153","isPushEnabled":"1","appInstallTimeDiff":"1696756955347","osVersion":"17.2.1","chargeStatus":"1","isSimInserted":"1","networkType":"WIFI"}
            """.trimIndent()
        HoyolabRequest.PLATFORM.HOYOLAB -> """
                {"userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36","browserScreenSize":"387904","maxTouchPoints":"5","isTouchSupported":"1","browserLanguage":"zh-CN","browserPlat":"Linux aarch64","browserTimeZone":"Asia/Shanghai","webGlRender":"Adreno (TM) 640","webGlVendor":"Qualcomm","numOfPlugins":"0","listOfPlugins":"unknown","screenRatio":"2.625","deviceMemory":"4","hardwareConcurrency":"8","cpuClass":"unknown","ifNotTrack":"unknown","ifAdBlock":"0","hasLiedLanguage":"0","hasLiedResolution":"1","hasLiedOs":"0","hasLiedBrowser":"0","canvas":"${genRandomHex(64)}","webDriver":"0","colorDepth":"24","pixelRatio":"2.625","packageName":"unknown","packageVersion":"2.27.0","webgl":"${genRandomHex(64)}"}
            """.trimIndent()
    }
}