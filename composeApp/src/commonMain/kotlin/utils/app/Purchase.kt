package utils.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.Popup
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.russhwolf.settings.Settings
import com.voc.stargazer3.BuildKonfig
import dev.chrisbanes.haze.HazeState
import files.DonateUs
import files.DonationCannotFindProduct
import files.DonationDesc
import files.DonationDesc2
import files.DonationFailed
import files.DonationSuccessFurtherNotes
import files.DonationSuccessThanking
import files.DonationTargetFinishRate
import files.Res
import files.pom_pom_gift
import files.yunli_eating
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.UserAccount
import ui.components.AppDialog
import ui.components.UIButton
import ui.screens.doDonorRefresh
import utils.annotation.TranslationPls
import utils.starbase.StarbaseAPI
import kotlin.math.min

private val DonationChoiceList = listOf(
    "$1" to "sg3_donation_usd_1",
    "$2" to "sg3_donation_usd_2",
    "$5" to "sg3_donation_usd_5",
    "$10" to "sg3_donation_usd_10",
    "$20" to "sg3_donation_usd_20",
    "$50" to "sg3_donation_usd_50",
)

private const val PURCHASE_GOOGLE_KEY = "goog_BpnfBRwPJTJTYljNFCHGzpZDqUM"
private const val PURCHASE_APPLE_KEY = "appl_rYZMbREirAEzhROzBryDmtGiXSg"
private var productList: List<StoreProduct> = emptyList()
lateinit var showDonationPopup : MutableState<Boolean>
lateinit var DONATION_FAILED : String
lateinit var DONATION_PRODUCT_NOT_FIND : String

@Composable
fun initPurchase() {
    showDonationPopup = remember { mutableStateOf(false) }
    DONATION_FAILED = removeStrQuote(Res.string.DonationFailed)
    DONATION_PRODUCT_NOT_FIND = removeStrQuote(Res.string.DonationCannotFindProduct)
    // Check if the platform is supported
    if(!isIosPlatform() && !isMacOSPlatform() && !isAndroidPlatform()) {
        return
    }

    // Initialize purchase related variables or states here
    val apiKey = when {
        isIosPlatform() || isMacOSPlatform() -> PURCHASE_APPLE_KEY
        else -> PURCHASE_GOOGLE_KEY
    }
    val donationIdSuffix = when {
        isIosPlatform() || isMacOSPlatform()-> "_asr"
        else -> "_gp"
    }
    val localDonationChoiceList = DonationChoiceList.map { it.second + donationIdSuffix }

    Purchases.logLevel = if(BuildKonfig.appProfile == "DEV") { LogLevel.DEBUG } else { LogLevel.INFO }
    Purchases.configure(apiKey = apiKey) { appUserId = if(UserAccount.getUID() == "000000000") null else UserAccount.getUID() }
    Purchases.sharedInstance.getProducts(localDonationChoiceList, onSuccess = { list ->
        productList = list
    }, onError = {
        println("Error fetching products: $it")
    })
}

fun doPurchase(itemId: String, isSuccess: MutableState<Boolean>){
    // Suffix for the product ID based on the platform
    val donationIdSuffix = when {
        isIosPlatform() || isMacOSPlatform()-> "_asr"
        else -> "_gp"
    }

    try {
        val product = productList.find { it.id == itemId + donationIdSuffix }

        if(product == null){
            showWarningToast(
                message = DONATION_PRODUCT_NOT_FIND.replaceStrRes(itemId + donationIdSuffix),
                dismissPrevious = true
            )
        }

        Purchases.sharedInstance.purchase(
            storeProduct = product!!,
            onSuccess = { storeTransaction, customerInfo ->
                // Handle successful purchase
                isSuccess.value = true
                UserAccount.INSTANCE.donor = true
                Settings().putBoolean("donorNeedRedeem", UserAccount.getUID() == "000000000")
                doDonorRefresh.value = !doDonorRefresh.value
                Language().setAppLanguage()
            },
            onError = { error, isUserCancel ->
                if(!isUserCancel){
                    // Handle purchase error
                    showWarningToast(
                        message = DONATION_FAILED.replaceStrRes(error.message),
                        dismissPrevious = true
                    )
                }
                Language().setAppLanguage()
            }
        )
    }catch (e: Exception){
        e.printStackTrace()
    }
}

@Composable
fun DonationPopUp(
    isShowPopup: MutableState<Boolean>,
    hazeState: HazeState,
) {
    val donationMonthData = rememberSaveable(saver = JsonObjectSaver) { (StarbaseAPI().getDonationSumThisMonth()) }
    val isSuccessDonation = remember { mutableStateOf(false) }
    if(isShowPopup.value){
        val urlHandler = LocalUriHandler.current
        if(
            isWindowsPlatform() ||
            isLinuxPlatform() ||
            isAndroidPlatform() && BuildKonfig.appProfile != "PRODUCTION_GP"
        ){
            urlHandler.openUri("https://buymeacoffee.com/codingband")
            isShowPopup.value = false
            return
        }

        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = removeStrQuote(Res.string.DonateUs), //Downloading the assets
                hazeState = hazeState,
                components = {
                    if(isSuccessDonation.value){
                        DonationSuccessPopupContent()
                    }else{
                        DonationPopupContent(isSuccessDonation, donationMonthData)
                    }
                },
                isPopupShow = isShowPopup,
                isDialog = true
            )
        }
    }
}


@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun DonationPopupContent(
    isSuccess: MutableState<Boolean>,
    donationMonthData: JsonObject,
) {
    LazyColumn(
        modifier = Modifier
            .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            val richTextState = rememberRichTextState()
            val richTextState2 = rememberRichTextState()
            richTextState.setHtml(removeStrQuote(Res.string.DonationDesc))
            RichText(
                state = richTextState,
                fontSize = FontSizeNormal14().fontSize,
                fontFamily = FontSizeNormal14().fontFamily,
                fontWeight = FontSizeNormal14().fontWeight
            )
            Spacer(modifier = Modifier.height(6.dp))

            //进行任意一项捐赠即可获取特殊标识（需先绑定崩坏：星穹铁道账号），每月订阅用户还可使用其他進階功能（稍後公布）。

            richTextState2.setHtml(removeStrQuote(Res.string.DonationDesc2))
            RichText(
                state = richTextState2,
                fontSize = FontSizeNormal14().fontSize,
                fontFamily = FontSizeNormal14().fontFamily,
                fontWeight = FontSizeNormal14().fontWeight
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar with PomPom Chief

            val maxWidthSlider = remember{ mutableStateOf(240.dp) }
            val percent = remember { mutableStateOf(donationMonthData["percent"]?.jsonPrimitive?.floatOrNull ?: 0f) }
            BoxWithConstraints (modifier = Modifier.fillMaxWidth().wrapContentHeight()){
                maxWidthSlider.value = this.maxWidth
                Slider(
                    value = percent.value,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .align(Alignment.Center),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Transparent,
                        activeTrackColor = Color(0xFFC8A471),
                        inactiveTrackColor = Color(0xFFC8BFB1),
                        ),
                )
                Row {
                    Spacer(modifier = Modifier.width(max(0.dp, maxWidthSlider.value.times(min(1f, percent.value)) - 21.dp)))
                    Image(
                        painter = painterResource(Res.drawable.yunli_eating),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(38.dp)
                            .background(color = Color(0xFFC8A471), shape = CircleShape)
                            .padding(2.dp)
                            .clip(CircleShape)
                    )
                }
            }

            // Display the donation amount for this month

            Text(
                text = removeStrQuote(Res.string.DonationTargetFinishRate).replaceStrRes(arrayListOf(
                    formatDecimal(donationMonthData["percent"]?.jsonPrimitive?.floatOrNull ?: 0, 0),
                    "$"+formatDecimal(donationMonthData["total"]?.jsonPrimitive?.floatOrNull ?: 0,0),
                    "$"+formatDecimal(donationMonthData["target"]?.jsonPrimitive?.floatOrNull ?: 0,0))
                ),
                style = FontSizeNormal16(),
                color = Color(0xFF222222),
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        items(DonationChoiceList,
            key = { it.second }
        ) { item ->
            UIButton(
                text = "${removeStrQuote(Res.string.DonateUs)} ${item.first}",
                onClick = { doPurchase(item.second, isSuccess) },
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

    }


}


@Composable
private fun DonationSuccessPopupContent(
) {
    LazyColumn(
        modifier = Modifier
            .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Image(
                painter = painterResource(Res.drawable.pom_pom_gift),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        @TranslationPls
        item {
            Text(
                text = removeStrQuote(Res.string.DonationSuccessThanking),
                style = FontSizeNormal20(),
                color = Color(0xFF222222),
            )
        }

        @TranslationPls
        item {
            Text(
                text = removeStrQuote(Res.string.DonationSuccessFurtherNotes),
                style = FontSizeNormal16(),
                color = Color(0xFF222222),
            )
        }

    }


}