import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.cancel
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import okio.Buffer
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.Sink
import okio.Source
import okio.Timeout
import okio.buffer
import okio.use
import org.jetbrains.skia.Image
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSBundle
import platform.Foundation.NSDate
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientationLandscapeLeft
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import platform.UIKit.UIKeyboardAppearanceDark
import platform.UIKit.UITextField
import utils.annotation.DoItLater
import utils.device.DeviceInfo
import kotlin.system.exitProcess

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


//iOS will never use this
actual class ContextFactory {
    // Bundle allows you to lookup resources
    actual fun getContext(): Any = NSBundle
    // UIApplication allows you to access all app info
    actual fun getApplication(): Any = UIApplication
    // RootViewController can be used to identify your current screen
    actual fun getActivity(): Any = UIApplication.sharedApplication.keyWindow?.rootViewController ?: ""
}

actual fun getAppSpecificDirectory(): okio.Path {
    val fileManager = NSFileManager.defaultManager()
    val urls = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask)
    val appSupportDir = urls.last() as NSURL
    return appSupportDir.path?.toPath() ?: throw IllegalStateException("Could not get the path for app support directory")
}

private const val BUFFER_SIZE = 4096

class ByteReadChannelSource(
    private val channel: ByteReadChannel,
    private val scope: CoroutineScope = GlobalScope
) : Source {
    override fun read(sink: Buffer, byteCount: Long): Long {
        if (channel.isClosedForRead) return -1L

        return runBlocking(scope.coroutineContext) {
            val buffer = ByteArray(byteCount.coerceAtMost(8192).toInt())
            val bytesRead = channel.readAvailable(buffer, 0, buffer.size)
            if (bytesRead < 0) {
                -1L
            } else {
                sink.write(buffer, 0, bytesRead)
                bytesRead.toLong()
            }
        }
    }

    override fun close() {
        channel.cancel()
    }

    override fun timeout(): Timeout = Timeout.NONE
}

// 擴展函數
fun ByteReadChannel.copyToOkio(sink: Sink, limit: Long = Long.MAX_VALUE): Long {
    val source = ByteReadChannelSource(this)
    return source.use { okioSource ->
        val bufferedSink = sink.buffer()
        var totalBytesCopied = 0L
        bufferedSink.use {
            while (totalBytesCopied < limit) {
                val bytesRead = okioSource.read(bufferedSink.buffer, minOf(limit - totalBytesCopied, 8192L))
                if (bytesRead == -1L) break
                totalBytesCopied += bytesRead
            }
            totalBytesCopied
        }
    }
}

actual suspend fun ByteReadChannel.writeToFile(filepath: String) {
    val path = filepath.toPath()
    val parent = path.parent
    if (parent != null && !FileSystem.SYSTEM.exists(parent)) {
        FileSystem.SYSTEM.createDirectories(parent)
    }
    this.copyToOkio(FileSystem.SYSTEM.sink(filepath.toPath()))
}

actual fun exitApp() {
    exitProcess(0)
}

@Composable
actual fun kcefSetUpActual(
    downloadProgress: MutableState<Float>,
    isProcessing: MutableState<Boolean>,
    kcefStatus: MutableState<KCEFStatus>
) {
    downloadProgress.value = 1f
    isProcessing.value = false
    kcefStatus.value = KCEFStatus.FINISH
}