import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS ANDROID-MAIN, so ONLY ACTUAL
 */

actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size).asImageBitmap();
}

@Composable
actual fun getIsLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
actual fun getScreenSizeInfo(): ScreenSizeInfo {
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val hDp = config.screenHeightDp.dp
    val wDp = config.screenWidthDp.dp

    return ScreenSizeInfo(
        hPX = with(density) { hDp.roundToPx() },
        wPX = with(density) { wDp.roundToPx() },
        hDP = hDp,
        wDP = wDp
    )
}