import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS COMMON-MAIN, so ONLY EXPECT
 */

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap;

@Composable
expect fun getIsLandscape(): Boolean;