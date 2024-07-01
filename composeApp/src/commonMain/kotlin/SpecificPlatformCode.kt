import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS COMMON-MAIN, so ONLY EXPECT
 */

interface Platform {
    val name: String
}
expect fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap;

@Composable
expect fun getIsLandscape(): Boolean;

/** Getting screen size info for UI-related calculations */
data class ScreenSizeInfo(val hPX: Int, val wPX: Int, val hDP: Dp, val wDP: Dp)

@Composable
expect fun getScreenSizeInfo(): ScreenSizeInfo

@Composable
expect fun getAppDataDir(): String
expect fun getTimeStamp(): Long
expect fun getDeviceName(): String