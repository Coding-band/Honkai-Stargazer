import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import types.DeviceInfo
import utils.annotation.DoItLater

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS COMMON-MAIN, so ONLY EXPECT
 */

expect fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap;

@Composable
expect fun getIsLandscape(): Boolean;

/** Getting screen size info for UI-related calculations */
data class ScreenSizeInfo(val hPX: Int, val wPX: Int, val hDP: Dp, val wDP: Dp)

@Composable
@DoItLater("Remember to reduce home page width if u are using iPad")
expect fun getScreenSizeInfo(): ScreenSizeInfo

@Composable
expect fun getAppDataDir(): String

@Deprecated("Use Kotlinx-DateTime instead")
expect fun getTimeStamp(): Long
expect fun getDeviceInfo(): DeviceInfo

@Composable
expect fun setKeyboardDarkMode()
expect fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient

expect fun changeLanguage(language: String, region: String? = null)