package utils.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.voc.stargazer3.BuildKonfig
import dev.chrisbanes.haze.HazeState
import files.DonateUs
import files.Donation
import files.DonationDesc
import files.KCEFInstallTitle
import files.Res
import types.UserAccount
import ui.components.AppDialog
import ui.components.ThemedProgressBar
import ui.components.UIButton

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

fun initPurchase() {
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
        isIosPlatform() || isMacOSPlatform()-> "_as"
        else -> "_gp"
    }
    val localDonationChoiceList = DonationChoiceList.map { it.second + donationIdSuffix }

    Purchases.logLevel = if(BuildKonfig.appProfile == "DEV") { LogLevel.DEBUG } else { LogLevel.INFO }
    Purchases.configure(apiKey = apiKey) { appUserId = if(UserAccount.getUID() == "000000000") null else UserAccount.getUID() }
    Purchases.sharedInstance.getProducts(localDonationChoiceList, onSuccess = {
        productList
    }, onError = {
        println("Error fetching products: $it")
    })
}

fun doPurchase(itemId: String){
    val donationIdSuffix = when {
        isIosPlatform() || isMacOSPlatform()-> "_as"
        else -> "_gp"
    }

    try {
        println("productList : $productList")
        val product = productList.find { it.id == itemId + donationIdSuffix }

        if(product == null){
            showWarningToast(
                message = "Product not found : $itemId + donationIdSuffix",
                dismissPrevious = true
            )
        }

        Purchases.sharedInstance.purchase(
            storeProduct = product!!,
            onSuccess = { storeTransaction, customerInfo ->
                // Handle successful purchase
                println("Purchase successful: $storeTransaction")
            },
            onError = { error, isUserCancel ->
                if(!isUserCancel){
                    // Handle purchase error
                    showWarningToast(
                        message = "Purchase failed: ${error.message}",
                        dismissPrevious = true
                    )
                }else{
                    showWarningToast(
                        message = "Purchased Canceled",
                        dismissPrevious = true
                    )
                }
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
    if(isShowPopup.value){
        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = removeStrQuote(Res.string.DonateUs), //Downloading the assets
                hazeState = hazeState,
                modifier = Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
                components = {
                    DonationPopupContent()
                },
                isPopupShow = isShowPopup,
            )
        }
    }
}


@OptIn(ExperimentalRichTextApi::class)
@Composable
private fun DonationPopupContent(
) {
    LazyColumn(
        modifier = Modifier
            .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            val richTextState = rememberRichTextState()
            richTextState.setHtml(removeStrQuote(Res.string.DonationDesc))
            RichText(
                state = richTextState,
                fontSize = FontSizeNormal14().fontSize,
                fontFamily = FontSizeNormal14().fontFamily,
                fontWeight = FontSizeNormal14().fontWeight
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(DonationChoiceList,
            key = { it.second }
        ) { item ->
            UIButton(
                text = "${removeStrQuote(Res.string.DonateUs)} ${item.first}",
                onClick = { doPurchase(item.second) },
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

    }


}