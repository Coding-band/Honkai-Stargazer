
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.russhwolf.settings.Settings
import dev.datlag.kcef.KCEF
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.copyTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okio.Path
import okio.Path.Companion.toPath
import org.jetbrains.skia.Image
import utils.annotation.DoItLater
import utils.device.DeviceInfo
import java.io.File
import java.util.Locale
import kotlin.math.max
import kotlin.system.exitProcess

/**
 * Yeah... PC is support rn.
 */

actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    val skiaImage = Image.makeFromEncoded(byteArray)
    return skiaImage.toComposeImageBitmap()
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getIsLandscape(): Boolean {
    val windowInfo = LocalWindowInfo.current
    return windowInfo.containerSize.width > windowInfo.containerSize.height
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Deprecated("Please use BoxWithConstraints instead")
actual fun getScreenSizeInfo(): ScreenSizeInfo {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    return remember(density, windowInfo) {
        ScreenSizeInfo(
            hPX = windowInfo.containerSize.height,
            wPX = windowInfo.containerSize.width,
            hDP = with(density) { windowInfo.containerSize.height.toDp() },
            wDP = with(density) { windowInfo.containerSize.width.toDp() }
        )
    }
}

@Composable
actual fun getAppDataDir(): String {
    TODO("Not yet implemented")
}

actual fun getTimeStamp(): Long = System.currentTimeMillis()
actual fun getDeviceInfo(): DeviceInfo {
    //Return a DeviceInfo object that contains suitable OS version, device name data
    return DeviceInfo("Unspecified", System.getProperty("os.name"), System.getProperty("os.version"))
}

@Composable
actual fun setKeyboardDarkMode() {
    //Nothing will do since it even don't have virtual keyboard!
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = CIO, block = function)
}

actual fun changeLanguage(language: String, region : String?) {
    val locale = if(region == null) Locale(language) else Locale(language, region)
    Locale.setDefault(locale)
}

@DoItLater("Desktop Apply To AppData / Applicaton Support Directory")
actual fun getAppSpecificDirectory(): Path {
    val userHome = System.getProperty("user.home")
    return "$userHome/.Stargazer3".toPath()
}

//Desktop will never use this
actual class ContextFactory {
    actual fun getContext(): Any {
        TODO("Not yet implemented")
    }

    actual fun getApplication(): Any {
        TODO("Not yet implemented")
    }

    actual fun getActivity(): Any {
        TODO("Not yet implemented")
    }
}

actual suspend fun ByteReadChannel.writeToFile(filepath: String) {
    this.copyTo(File(filepath).writeChannel())
}

actual fun exitApp() {
    exitProcess(0)
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
actual fun kcefSetUpActual(downloadProgress: MutableState<Float>, isProcessing: MutableState<Boolean>, kcefStatus: MutableState<KCEFStatus>) {
    //val download: KCEFBuilder.Download = KCEFBuilder.Download.Builder().github().build()
    val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob()) // 定義主線程範圍
    val isInited = remember { mutableStateOf<Boolean?>(null) }
    val isRestart = remember { mutableStateOf<Boolean?>(null) }

        CoroutineScope(Dispatchers.IO).launch {
            KCEF.init(builder = {
                installDir(File("kcef-bundle"))

                /*
                  Add this code when using JDK 17.
                  Builder().github {
                      release("jbr-release-17.0.10b1087.23")
                  }.buffer(download.bufferSize).build()
                 */
                progress {
                    onDownloading { it->
                        mainScope.launch {
                            //println("[KCEF] Downloading: $it")
                            downloadProgress.value = max(it, 0F)
                        }
                    }
                    onInitialized {
                        println("[KCEF] isInited!")
                        isInited.value = true
                        isProcessing.value = false
                        kcefStatus.value = KCEFStatus.FINISH
                        Settings().putBoolean("isJCEFInited", true)
                    }
                }
                settings {
                    cachePath = File("cache").absolutePath
                }
            }, onError = {
                it?.printStackTrace()
            }, onRestartRequired = {
                println("[KCEF] requestRestart!")
                isRestart.value = true
                isProcessing.value = false
                kcefStatus.value = KCEFStatus.REQUIRE_RESTART
                Settings().putBoolean("isJCEFInited", true)
            })
        }
}