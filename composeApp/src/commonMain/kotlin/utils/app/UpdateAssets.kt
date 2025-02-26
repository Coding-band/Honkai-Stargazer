package utils.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ui.components.AppDialog
import ui.components.ThemedProgressBar
import ui.components.UIButton
import utils.starbase.StarbaseAPI

private var localCommit = Settings().getString("localCommit", "")
private lateinit var isProcessing: MutableState<Boolean>
private lateinit var downloadProgress: MutableState<Long>
private lateinit var lastSecDownloadProgress: MutableState<Long>

@Serializable
data class UpdateAssetsInfo (
    @SerialName("commit")
    val commit: String,

    @SerialName("create_at")
    val createAt: String,

    @SerialName("create_at_unix")
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
fun UpdateAssetsPopup(isShowPopup: MutableState<Boolean>, hazeState: HazeState) {
    //First, check what git commit is the user using
    val updateAssetsInfo = readFromOnlineURL("${StarbaseAPI().getGitHubStaticAssetURL()}/updates/info.json")
    if(updateAssetsInfo.isEmpty() || updateAssetsInfo == "{}") {
        //Cannot get the update info (Network error maybe)
        isShowPopup.value = false;

        //Show the warning dialog to the user
        //...
    }

    val infoList = Json.decodeFromString<ArrayList<UpdateAssetsInfo>>(updateAssetsInfo)
    val currIndex = infoList.indexOfFirst { it.commit == localCommit }
    val updateState = checkIsNeedUpdateAssets(infoList, currIndex)

    when(updateState){
        UpdateAssetsStatus.UP_TO_DATE -> {
            //The user is using the latest version
            isShowPopup.value = false
        }
        UpdateAssetsStatus.PATCH -> {
            //Found the current commit in the list,
            //The user is using the last release version
            isShowPopup.value = true
        }
        UpdateAssetsStatus.FULL -> {
            //The user is using an outdated version
            isShowPopup.value = true
        }
        UpdateAssetsStatus.SKIP -> {
            //Skipped, maybe the user cannot connect to GitHub?
            isShowPopup.value = false
        }
    }

    //UI Part
    val acceptUpdate = remember { mutableStateOf(false) }
    if(::isProcessing.isInitialized){
        isProcessing.value = false
    }else{
        isProcessing = rememberSaveable { mutableStateOf(false) }
    }
    //Show the update dialog to the user
    Popup(alignment = Alignment.Center) {
        AppDialog(
            titleString = if(!acceptUpdate.value) "檢測到更新檔案" else "下載中...",
            hazeState = hazeState,
            components = {
                if(!acceptUpdate.value) {
                    UpdateAssetsPopupAsking(isShowPopup, infoList.first(),updateState, acceptUpdate)
                }else {
                    UpdateAssetsPopupDownloading(isShowPopup, infoList.first(),updateState, acceptUpdate)
                }
            },
            isPopupShow = isShowPopup,
        )
    }

    if(!isProcessing.value){
        //Start the update process
        CoroutineScope(Dispatchers.Default).launch {
            async {
                isProcessing.value = true
                val downloadURL = "${StarbaseAPI().getGitHubStaticAssetURL()}/updates/${infoList.first().commit}-${updateState.name}.zip"
                //Download the file, maybe add a new readFromURLProgress function, keep updating the downloadProgress for every 0.2 second

                //Unzip the file

                //Update the local commit

                //Wait for 1 second for user to see the progress after done.
            }.await()
        }
    }
}

@Composable
fun UpdateAssetsPopupDownloading(showPopup: MutableState<Boolean>, latestAssetsInfo: UpdateAssetsInfo, updateState: UpdateAssetsStatus, acceptUpdate: MutableState<Boolean>) {
    val fileSize = latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L
    if(::downloadProgress.isInitialized){
        downloadProgress.value = 0
    }else{
        downloadProgress = rememberSaveable { mutableStateOf(0L) }
    }

    if(::lastSecDownloadProgress.isInitialized){
        lastSecDownloadProgress.value = 0
    }else{
        lastSecDownloadProgress = rememberSaveable { mutableStateOf(0L) }
    }

    //State can be "DOWNLOADING", "UNZIPPING", "FINISH", "ERROR-NETWORK", "ERROR-UNZIP"
    val downloadState = remember { mutableStateOf(DownloadAssetsState.DOWNLOADING) }
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        //下載進度: 18.5% (1.85MB / 10.0MB)
        Text("下載進度: ${formatDecimal(downloadProgress.value / fileSize * 100)}% (${formatDecimal(downloadProgress.value / 1048576f, 2)} / ${formatDecimal(fileSize / 1048576f,2) }", style = FontSizeNormal16(), color = Color(0xFF222222))

        Spacer(modifier = Modifier.height(12.dp))

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
    acceptUpdate: MutableState<Boolean>
) {
    val fileSizePretty = formatDecimal(
        (latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L) / 1048576f //Present By MB
        ,2
    )
    Column {
        Text(text = "更新檔案大小: $fileSizePretty MB", style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text("推薦在WI-FI環境下進行更新", style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text("黑塔女士舉世無雙！黑塔女士聰明絕頂！黑塔女士沉魚落雁！", style = FontSizeNormalSmall(), color = Color(0x33222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Row {
            UIButton(
                text = "之後吧",
                onClick = {
                    acceptUpdate.value = false
                    isShowPopup.value = false
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            UIButton(
                text = "確定",
                onClick = {
                    acceptUpdate.value = true
                }
            )
        }
    }
}

/**
 * This function will update the assets of the app
 */
fun UpdateAssetsProcess() {

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
            //The user is using an outdated version
            UpdateAssetsStatus.FULL
        }
    }
}