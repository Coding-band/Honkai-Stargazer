import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.utils.io.ByteReadChannel
import utils.device.DeviceInfo
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

//ref: https://medium.com/@robert.jamison/passing-android-context-in-kmp-jetpack-compose-8de5b5de7bdd
expect class ContextFactory {
    fun getContext(): Any
    fun getApplication(): Any
    fun getActivity(): Any
}

expect fun getAppSpecificDirectory(): okio.Path

//ref: https://stackoverflow.com/questions/78739232/how-to-save-a-response-body-to-a-file-in-kotlin-multiplatform-with-ktor
expect suspend fun ByteReadChannel.writeToFile(filepath: String)

expect fun exitApp(): Unit

enum class KCEFStatus {
    ASKING, ACCEPT_DOWNLOAD, DENY, FINISH, REQUIRE_RESTART
}
@Composable
expect fun kcefSetUpActual(downloadProgress: MutableState<Float>, isProcessing: MutableState<Boolean>, kcefStatus: MutableState<KCEFStatus>)