import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import types.DeviceInfo

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
expect fun getTimeStamp(): Long
expect fun getDeviceInfo(): DeviceInfo