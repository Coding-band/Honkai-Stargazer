
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.russhwolf.settings.Settings
import com.voc.stargazer3.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.copyTo
import okio.Path
import okio.Path.Companion.toPath
import types.UserAccount
import ui.screens.doDonorRefresh
import utils.app.DONATION_FAILED
import utils.app.DONATION_PRODUCT_NOT_FIND
import utils.app.Language
import utils.app.errorLog
import utils.app.isIosPlatform
import utils.app.isMacOSPlatform
import utils.app.replaceStrRes
import utils.app.showWarningToast
import utils.device.DeviceInfo
import java.io.File
import java.util.Locale
import kotlin.system.exitProcess

/**
 * This is the declaration kt file for specific-platform function
 * THIS IS ANDROID-MAIN, so ONLY ACTUAL
 */



actual fun getImageBitmapByByteArray(byteArray: ByteArray): ImageBitmap {
    val bitmap : ImageBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size).asImageBitmap()
    bitmap.prepareToDraw()
    return bitmap;
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

@Composable
actual fun getAppDataDir(): String {
    return LocalContext.current.filesDir.absolutePath
}

actual fun getTimeStamp(): Long = System.currentTimeMillis()
actual fun getDeviceInfo(): DeviceInfo = DeviceInfo(
    deviceModel = Build.MODEL,
    deviceOSName = "Android",
    deviceOSVersion = Build.VERSION.SDK_INT.toString()
)

@Composable
actual fun setKeyboardDarkMode() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val activity = context as? Activity
            val window = activity?.window
            if (window != null) {
                val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
                windowInsetsController.isAppearanceLightNavigationBars = false
            }
        } else {
            //Nothing will do
        }
    }
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = CIO, block = function)
}

actual fun changeLanguage(language: String, region: String?) {
    val locale = if(region == null) Locale(language) else Locale(language, region)
    Locale.setDefault(locale)
}

actual fun getAppSpecificDirectory(): Path {
    val context: Context = platformContext.getContext() as Context
    return context.filesDir.absolutePath.toPath()
}

actual class ContextFactory(private val activity: ComponentActivity) {
    actual fun getContext(): Any = activity.baseContext
    actual fun getApplication(): Any = activity.application
    actual fun getActivity(): Any = activity
}

actual suspend fun ByteReadChannel.writeToFile(filepath: String) {
    this.copyTo(File(filepath).writeChannel())
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
    val donationIdSuffix = "_gp"

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
    //Android version
    // Initialize purchase related variables or states here
    val donationIdSuffix = "_gp"
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