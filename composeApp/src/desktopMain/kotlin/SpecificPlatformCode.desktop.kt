import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo

/**
 * Ehm... yes... PC not support rn.
 */

actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    TODO("Not yet implemented")
}


actual fun getIsLandscape(): Boolean {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
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