
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.copyTo
import okio.Path
import okio.Path.Companion.toPath
import org.jetbrains.skia.Image
import utils.annotation.DoItLater
import utils.device.DeviceInfo
import java.io.File
import java.util.Locale

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