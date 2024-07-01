import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import org.jetbrains.skia.Image
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientation
import platform.UIKit.UIInterfaceOrientationLandscapeLeft
import platform.UIKit.UIInterfaceOrientationLandscapeRight


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
    val appSupportDir = urls.lastObject() as NSURL
    return appSupportDir.path ?: throw IllegalStateException("Could not get the path for app support directory")
}

actual fun getTimeStamp(): Long = NSDate().timeIntervalSince1970.toLong() * 1000
actual fun getDeviceName(): String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion