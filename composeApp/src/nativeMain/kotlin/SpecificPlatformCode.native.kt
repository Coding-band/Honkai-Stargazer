import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import org.jetbrains.skia.Image
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSDate
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientationLandscapeLeft
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import utils.device.DeviceInfo
import androidx.compose.runtime.SideEffect
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIInterfaceOrientation
import platform.UIKit.UIInterfaceOrientationPortrait
import platform.UIKit.UIKeyboardAppearanceDark
import platform.UIKit.UITextField
import utils.annotation.DoItLater
import kotlin.experimental.ExperimentalNativeApi

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS NATIVE-MAIN, so ONLY ACTUAL (iOS)
 */

actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

@Composable
actual fun getIsLandscape(): Boolean {
    val orientation = UIDevice.currentDevice.orientation.value
    return orientation == UIInterfaceOrientationLandscapeLeft || orientation == UIInterfaceOrientationLandscapeRight

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenSizeInfo(): ScreenSizeInfo {
    val density = LocalDensity.current
    val config = LocalWindowInfo.current.containerSize


    return ScreenSizeInfo(
        hPX = config.height,
        wPX = config.width,
        hDP = with(density) { config.height.toDp() },
        wDP = with(density) { config.width.toDp() }
    )
}

@Composable
actual fun getAppDataDir(): String {
    val fileManager = NSFileManager.defaultManager()
    val urls = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask)
    val appSupportDir = urls.last() as NSURL
    return appSupportDir.path ?: throw IllegalStateException("Could not get the path for app support directory")
}

actual fun getTimeStamp(): Long = NSDate().timeIntervalSince1970.toLong() * 1000
actual fun getDeviceInfo(): DeviceInfo = DeviceInfo(
    deviceModel = UIDevice.currentDevice.model(),
    deviceOSName = UIDevice.currentDevice.systemName(),
    deviceOSVersion = UIDevice.currentDevice.systemVersion()
)

@DoItLater("iOS testing")
@Composable
actual fun setKeyboardDarkMode() {
    SideEffect {
        val textField = UITextField()
        textField.keyboardAppearance = UIKeyboardAppearanceDark
        textField.becomeFirstResponder()
        textField.resignFirstResponder()
    }
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = Darwin, block = function)
}

actual fun changeLanguage(language: String, region : String?) {
    val lang = if(region != null && language.lowercase() == "zh") {
        "$language-$region"
    }else {
        language
    }
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),"AppleLanguages")
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalNativeApi::class)
@Composable
actual fun getOrientation(): Orientation {
    //how iOS and macOS handle orientation?
    val orientation = UIDevice.currentDevice.orientation
    return when (orientation) {
            UIDeviceOrientation.UIDeviceOrientationLandscapeLeft -> Orientation.Horizontal
            UIDeviceOrientation.UIDeviceOrientationLandscapeRight -> Orientation.Horizontal
            else -> Orientation.Vertical
    }
}