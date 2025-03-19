package utils.app

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import files.ConfirmBTN
import files.LaterBTN
import files.Res
import files.UpdateAssetDownloadFull
import files.UpdateAssetDownloadFullSizeReason
import files.UpdateAssetDownloadProgress
import files.UpdateAssetDownloadingUpdate
import files.UpdateAssetFoundUpdate
import files.UpdateAssetUpdateSize
import files.UpdateAssetUpdateSuggestionWiFi
import files.UpdateAssetUpdateTheHerta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import ui.components.AppDialog
import ui.components.ThemedProgressBar
import ui.components.UIButton
import ui.navigation.refreshInit
import ui.screens.doRefresh
import utils.starbase.StarbaseAPI

private var localCommit = Settings().getString("localCommit-${Language.TextLanguageInstance.folderName}", "")
private lateinit var isProcessing: MutableState<Boolean>
private var infoList = arrayListOf<UpdateAssetsInfo>()
private var updateState = UpdateAssetsStatus.SKIP

private lateinit var downloadProgress : MutableState<Long>

@Serializable
data class UpdateAssetsInfo (
    @SerialName("commit")
    val commit: String,

    @SerialName("created_at")
    val createAt: String,

    @SerialName("created_at_unix")
    val createAtUnix: Long,

    @SerialName("version")
    val version: String,

    @SerialName("size")
    val size: Map<String, Long>
)

@Serializable
enum class UpdateAssetsStatus {
    UP_TO_DATE,
    PATCH,
    FULL,
    SKIP
}

@Serializable
enum class DownloadAssetsState {
    DOWNLOADING,
    UNZIPPING,
    FINISH,
    ERROR_NETWORK,
    ERROR_UNZIP
}
@Composable
fun updateAssetsInit(){
    if(::isProcessing.isInitialized){
        isProcessing.value = false
    }else{
        isProcessing = rememberSaveable { mutableStateOf(false) }
    }
    if(::downloadProgress.isInitialized){
        downloadProgress.value = 0L
    }else{
        downloadProgress = rememberSaveable { mutableStateOf(0L) }
    }
}

fun updateCheckInit(forceDownload: Boolean = false) : Boolean{
    val updateAssetsInfo = readFromOnlineURL("${StarbaseAPI().getGitHubStaticAssetURL()}/updates/info.json")
    if(updateAssetsInfo.isEmpty() || updateAssetsInfo == "{}") {
        //Cannot get the update info (Network error maybe)

        //Show the warning dialog to the user
        //...
        showWarningToast(ERR_NETWORK_UNSTABLE_CONNECTION)
        return false
    }

    val infoJson = Json.parseToJsonElement(updateAssetsInfo).jsonObject["updates"]
    infoList.clear()
    infoList = Json.decodeFromJsonElement<ArrayList<UpdateAssetsInfo>>(infoJson!!.jsonArray)
    infoList.sortByDescending { it.createAtUnix }
    val currIndex = infoList.indexOfFirst { it.commit == localCommit }
    updateState = if(forceDownload) UpdateAssetsStatus.FULL else checkIsNeedUpdateAssets(infoList, currIndex)

    when(updateState){
        UpdateAssetsStatus.UP_TO_DATE -> {
            //The user is using the latest version
            return false
        }
        UpdateAssetsStatus.PATCH -> {
            //Found the current commit in the list,
            //The user is using the last release version
            return true
        }
        UpdateAssetsStatus.FULL -> {
            //The user is using an outdated version
            return true
        }
        UpdateAssetsStatus.SKIP -> {
            //Skipped, maybe the user cannot connect to GitHub?
            return false
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UpdateAssetsPopup(
    isShowPopup: MutableState<Boolean>,
    hazeState: HazeState,
    forceDownload: Boolean = false,
) {
    if(forceDownload){ //Force download the assets
        updateCheckInit(forceDownload)
    }

    if(isShowPopup.value){
        //First, check is latestAssetsInfo empty or not
        if(infoList.isEmpty()){
            //Cannot get the update info (Network error maybe)
            //Show the warning dialog to the user
            //...
            showWarningToast(ERR_NETWORK_UNSTABLE_CONNECTION)
            isShowPopup.value = false
            return
        }

        //UI Part
        val acceptUpdate = remember { mutableStateOf(false) }
        val denyUpdate = remember { mutableStateOf(false) }
        //Show the update dialog to the user

        if(!denyUpdate.value && isShowPopup.value){
            Popup(alignment = Alignment.Center) {
                AppDialog(
                    titleString = removeStrQuote(
                        if(!acceptUpdate.value) { //Asking the user to update or not
                            if(updateState == UpdateAssetsStatus.FULL) Res.string.UpdateAssetDownloadFull else Res.string.UpdateAssetFoundUpdate
                        } else Res.string.UpdateAssetDownloadingUpdate), //Downloading the assets
                    hazeState = hazeState,
                    modifier = Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
                    components = {
                        if(!acceptUpdate.value) {
                            UpdateAssetsPopupAsking(isShowPopup, infoList.first(),updateState, acceptUpdate, denyUpdate)
                        }else {
                            UpdateAssetsPopupDownloading(isShowPopup, infoList.first(),updateState, downloadProgress)
                        }
                    },
                    isPopupShow = isShowPopup,
                )
            }
        }else{
            isShowPopup.value = false
        }

        LaunchedEffect(acceptUpdate.value){
            if(!isProcessing.value && acceptUpdate.value){
                isProcessing.value = true

                CoroutineScope(Dispatchers.IO).async {
                    val isSuccess = mutableStateOf(false)
                    downloadFromURLProgress(url = "${StarbaseAPI().getGitHubStaticAssetURL()}/updates/${infoList.first().commit}/${infoList.first().commit}-${Language.TextLanguageInstance.folderName}-${updateState.name}.zip", downloadProgress, isSuccess = isSuccess)

                    if(!isSuccess.value) {
                        //Warning ...
                    }else {
                        //Update the local commit
                        Settings().putString("localCommit-${Language.TextLanguageInstance.folderName}", infoList.first().commit)
                    }

                    refreshInit()
                    doRefresh.value = true
                    isProcessing.value = false
                    isShowPopup.value = false

                }.await()
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
fun UpdateAssetsPopupDownloading(showPopup: MutableState<Boolean>, latestAssetsInfo: UpdateAssetsInfo, updateState: UpdateAssetsStatus, downloadProgress: MutableState<Long>) {
    val fileSize = latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L
    //State can be "DOWNLOADING", "UNZIPPING", "FINISH", "ERROR-NETWORK", "ERROR-UNZIP"
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        //下載進度: 18.5% (1.85MB / 10.0MB)
        Text(removeStrQuote(Res.string.UpdateAssetDownloadProgress).replaceStrRes(arrayListOf(
            formatDecimal(downloadProgress.value * 100 / fileSize),
            formatDecimalByte(downloadProgress.value, 2),
            formatDecimalByte(fileSize,2))
        ), style = FontSizeNormal16(), color = Color(0xFF222222))

        Spacer(modifier = Modifier.height(12.dp))

        //println("From UI : ${downloadProgress.value} / $fileSize")

        ThemedProgressBar(
            progress = downloadProgress.value,
            max = fileSize,
        )

        Spacer(modifier = Modifier.height(12.dp))
    }


}

@Composable
fun UpdateAssetsPopupAsking(
    isShowPopup: MutableState<Boolean>,
    latestAssetsInfo: UpdateAssetsInfo,
    updateState: UpdateAssetsStatus,
    acceptUpdate: MutableState<Boolean>,
    denyUpdate: MutableState<Boolean>
) {
    val fileSizePretty = formatDecimalByte(
        (latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L)
        ,2,
    )
    Column {
        Text(text = removeStrQuote(Res.string.UpdateAssetUpdateSize).replaceStrRes(fileSizePretty), style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        if(updateState == UpdateAssetsStatus.FULL){
            Text(removeStrQuote(Res.string.UpdateAssetDownloadFullSizeReason), style = FontSizeNormalSmall(), color = Color(0x66222222), modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(removeStrQuote(Res.string.UpdateAssetUpdateSuggestionWiFi), style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text(removeStrQuote(Res.string.UpdateAssetUpdateTheHerta), style = FontSizeNormalSmall(), color = Color(0x33222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            UIButton(
                text = removeStrQuote(Res.string.LaterBTN),
                onClick = {
                    denyUpdate.value = true
                    isShowPopup.value = false
                },
                modifierTmp = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            UIButton(
                text = removeStrQuote(Res.string.ConfirmBTN),
                onClick = {
                    acceptUpdate.value = true
                },
                modifierTmp = Modifier.weight(1f)
            )
        }
    }
}

fun checkIsNeedUpdateAssets(infoList : ArrayList<UpdateAssetsInfo>, currIndex: Int): UpdateAssetsStatus {
    return when (currIndex) {
        0 -> {
            //The user is using the latest version
            UpdateAssetsStatus.UP_TO_DATE
        }
        1 -> {
            //Found the current commit in the list,
            UpdateAssetsStatus.PATCH
        }
        else -> {
            if(infoList.isEmpty()){
                //Cannot get the update info (Network error maybe)
                UpdateAssetsStatus.SKIP
            }else{
                //The user is using an outdated version
                UpdateAssetsStatus.FULL
            }
        }
    }
}