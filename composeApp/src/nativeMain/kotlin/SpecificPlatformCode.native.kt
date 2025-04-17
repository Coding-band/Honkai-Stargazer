
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.russhwolf.settings.Settings
import com.voc.stargazer3.BuildKonfig
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
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.UIKit.UIInterfaceOrientationLandscapeLeft
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import platform.UIKit.UIKeyboardAppearanceDark
import platform.UIKit.UITextField
import types.UserAccount
import ui.screens.doDonorRefresh
import utils.annotation.DoItLater
import utils.app.DONATION_FAILED
import utils.app.DONATION_PRODUCT_NOT_FIND
import utils.app.Language
import utils.app.errorLog
import utils.app.replaceStrRes
import utils.app.showWarningToast
import utils.device.DeviceInfo
import kotlin.system.exitProcess

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS NATIVE-MAIN, so ONLY ACTUAL (iOS)
 */

actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

actual fun getByteArrayByImageBitmap(imageBitmap: ImageBitmap): ByteArray {
    return Image.makeFromBitmap(imageBitmap.asSkiaBitmap()).encodeToData()!!.bytes
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

actual fun doPurchaseImpl(itemId: String, isSuccess: MutableState<Boolean>, productList: List<Any>) {
    // Suffix for the product ID based on the platform
    val donationIdSuffix = "_asr"

    // Check if the product list is empty, return if it is (Since it may from JVM)
    if(productList.isEmpty()) return

    try {
        // Find the product in the list using the itemId and suffix
        val product = (productList as List<StoreProduct>).find { it.id == itemId + donationIdSuffix }

        if(product == null){
            showWarningToast(
                message = DONATION_PRODUCT_NOT_FIND.replaceStrRes(itemId + donationIdSuffix),
                dismissPrevious = true
            )
        }

        // Proceed with the purchase using the found product
        Purchases.sharedInstance.purchase(
            storeProduct = product!!,
            onSuccess = { storeTransaction, customerInfo ->
                // Handle successful purchase & update user account, preventing:
                // 1. User restart the app after purchasing
                // 2. User login failed (Wrong Server / No Record of that account in Star Rail)
                // 3. User would like to not login
                // 4. User would like to login very later (in any time)
                // We may not support this situation :
                // User would like to redeem at the one account that no donor role, but they first logged in to another account that have donor role already
                // If you are seeking for help, please contact us via Discord
                isSuccess.value = true // Returning result, allow to dismiss the dialog
                UserAccount.INSTANCE.donor = true // Update the donor status locally
                Settings().putBoolean("donorNeedRedeem", UserAccount.getUID() == "000000000") // Update the donor status in settings for later use
                doDonorRefresh.value = !doDonorRefresh.value // Trigger the refresh of the donor status
                Language().setAppLanguage() // Refresh again the app language
            },
            onError = { error, isUserCancel ->
                if(!isUserCancel){
                    // Handle purchase error
                    showWarningToast(
                        message = DONATION_FAILED.replaceStrRes(error.message),
                        dismissPrevious = true
                    )
                }
                Language().setAppLanguage() // Refresh again the app language
            }
        )
    }catch (e: Exception){
        errorLog(
            className = "SpecificPlatformCode.android.kt",
            functionName = "doPurchaseImpl",
            error = e,
        )
    }
}


actual fun initPurchaseImpl(
    productIdList: List<Pair<String, String>>,
    apiKey: String,
    productList: MutableState<List<Any>>
) {
    // iOS version
    // Initialize purchase related variables or states here
    val donationIdSuffix = "_asr"
    val localDonationChoiceList = productIdList.map { it.second + donationIdSuffix }

    // Initialize Purchases SDK
    Purchases.logLevel = if(BuildKonfig.appProfile == "DEV") { LogLevel.DEBUG } else { LogLevel.INFO }
    Purchases.configure(apiKey = apiKey) { appUserId = if(UserAccount.getUID() == "000000000") null else UserAccount.getUID() }
    Purchases.sharedInstance.getProducts(localDonationChoiceList, onSuccess = { list ->
        productList.value = list
    }, onError = {
        println("Error fetching products: $it")
    })
}

actual fun performHapticFeedback(intensity: Float, context: ContextFactory) {
    val generator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleRigid)
    generator.prepare()
    generator.impactOccurredWithIntensity(intensity.toDouble())
}