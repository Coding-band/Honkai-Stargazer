package utils.app

import KCEFStatus
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import dev.chrisbanes.haze.HazeState
import exitApp
import files.ConfirmBTN
import files.KCEFInstallDescription
import files.KCEFInstallDoneMessage
import files.KCEFInstallTitle
import files.LaterBTN
import files.NetworkErrorUnstableConnection
import files.Res
import files.UpdateAssetUpdateSuggestionWiFi
import files.UpdateAssetUpdateTheHerta
import kcefSetUpActual
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.components.AppDialog
import ui.components.ThemedProgressBar
import ui.components.UIButton
import ui.navigation.refreshInit
import ui.screens.doRefresh

private lateinit var isProcessing: MutableState<Boolean>
private lateinit var downloadProgress : MutableState<Float>

@Composable
fun kcefPopUpInit(){
    if(::isProcessing.isInitialized){
        isProcessing.value = false
    }else{
        isProcessing = rememberSaveable { mutableStateOf(false) }
    }
    if(::downloadProgress.isInitialized){
        downloadProgress.value = 0f
    }else{
        downloadProgress = rememberSaveable { mutableStateOf(0f) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun KCEFPopup(isShowPopup: MutableState<Boolean>, hazeState: HazeState) {
    if(isShowPopup.value){
        //UI Part
        val kcefState: MutableState<KCEFStatus> = rememberSaveable { mutableStateOf(KCEFStatus.ASKING) }
        val fileSize: MutableState<Long> = rememberSaveable { mutableStateOf(1L) }
        //Show the update dialog to the user

        if(kcefState.value != KCEFStatus.DENY && isShowPopup.value){
            Popup(alignment = Alignment.Center) {
                AppDialog(
                    titleString = removeStrQuote(Res.string.KCEFInstallTitle), //Downloading the assets
                    hazeState = hazeState,
                    modifier = Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
                    components = {
                        when(kcefState.value){
                            KCEFStatus.ASKING -> KCEFPopupAsking(isShowPopup, kcefState)
                            KCEFStatus.ACCEPT_DOWNLOAD -> KCEFPopupDownloading(isShowPopup, downloadProgress, kcefState)
                            KCEFStatus.DENY -> { isShowPopup.value = false }
                            KCEFStatus.FINISH -> { isShowPopup.value = false }
                            KCEFStatus.REQUIRE_RESTART -> KCEFPopupRequestRestart(isShowPopup, kcefState)
                        }
                    },
                    isPopupShow = isShowPopup,
                )
            }
        }else{
            isShowPopup.value = false
        }

        key(kcefState.value){
            if(!isProcessing.value && kcefState.value == KCEFStatus.ACCEPT_DOWNLOAD){
                isProcessing.value = true
                kcefSetUpActual(downloadProgress, isProcessing,kcefState)
            }else if (kcefState.value == KCEFStatus.FINISH){
                isShowPopup.value = false
            }
        }
    }else{
        if(!doRefresh.value){
            doRefresh.value = true
            refreshInit()
        }
    }
}

@Composable
private fun KCEFPopupDownloading(
    showPopup: MutableState<Boolean>,
    downloadProgress: MutableState<Float>,
    kcefState: MutableState<KCEFStatus>
) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        //下載進度: 18.5%
        Text("下載進度：${formatDecimal(downloadProgress.value)}%", style = FontSizeNormal16(), color = Color(0xFF222222))

        Spacer(modifier = Modifier.height(12.dp))

        //println("From UI : ${downloadProgress.value} / $fileSize")

        ThemedProgressBar(
            progress = downloadProgress.value,
            max = 100f,
        )

        Spacer(modifier = Modifier.height(12.dp))
    }


}

@Composable
private fun KCEFPopupAsking(
    isShowPopup: MutableState<Boolean>,
    kcefStatus: MutableState<KCEFStatus>,
) {
    Column {
        Text(text = removeStrQuote(Res.string.KCEFInstallDescription), style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text(removeStrQuote(Res.string.UpdateAssetUpdateSuggestionWiFi), style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text(removeStrQuote(Res.string.UpdateAssetUpdateTheHerta), style = FontSizeNormalSmall(), color = Color(0x33222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            UIButton(
                text = removeStrQuote(Res.string.LaterBTN),
                onClick = {
                    kcefStatus.value = KCEFStatus.DENY
                    isShowPopup.value = false
                },
                modifierTmp = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            UIButton(
                text = removeStrQuote(Res.string.ConfirmBTN),
                onClick = {
                    kcefStatus.value = KCEFStatus.ACCEPT_DOWNLOAD
                },
                modifierTmp = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun KCEFPopupRequestRestart(
    isShowPopup: MutableState<Boolean>,
    kcefStatus: MutableState<KCEFStatus>,
) {
    Column {
        Text(text = removeStrQuote(Res.string.KCEFInstallDoneMessage), style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            UIButton(
                text = removeStrQuote(Res.string.ConfirmBTN),
                onClick = {
                    exitApp();
                },
                modifierTmp = Modifier.weight(1f)
            )
        }
    }
}