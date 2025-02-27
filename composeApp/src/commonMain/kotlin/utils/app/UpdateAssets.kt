package utils.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
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
import getAppSpecificDirectory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import ui.components.AppDialog
import ui.components.ThemedProgressBar
import ui.components.UIButton
import utils.starbase.StarbaseAPI

private var localCommit = Settings().getString("localCommit", "")
private lateinit var isProcessing: MutableState<Boolean>
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

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UpdateAssetsPopup(isShowPopup: MutableState<Boolean>,canUpdatePopup: MutableState<Boolean>, hazeState: HazeState) {
    if(isShowPopup.value){
        //First, check what git commit is the user using
        val updateAssetsInfo = readFromOnlineURL("${StarbaseAPI().getGitHubStaticAssetURL()}/updates/info.json")
        if(updateAssetsInfo.isEmpty() || updateAssetsInfo == "{}") {
            //Cannot get the update info (Network error maybe)
            isShowPopup.value = false;

            //Show the warning dialog to the user
            //...
        }

        val infoJson = Json.parseToJsonElement(updateAssetsInfo).jsonObject["updates"]
        val infoList = Json.decodeFromJsonElement<ArrayList<UpdateAssetsInfo>>(infoJson!!.jsonArray)
        val currIndex = infoList.indexOfFirst { it.commit == localCommit }
        val updateState = checkIsNeedUpdateAssets(infoList, currIndex)
        var url = ""
        when(updateState){
            UpdateAssetsStatus.UP_TO_DATE -> {
                //The user is using the latest version
                isShowPopup.value = false
            }
            UpdateAssetsStatus.PATCH -> {
                //Found the current commit in the list,
                //The user is using the last release version
                isShowPopup.value = true
                url = "${StarbaseAPI().getGitHubStaticAssetURL()}/updates/${infoList.first().commit}/${infoList.first().commit}-${Language.TextLanguageInstance.folderName}-PATCH.zip"
            }
            UpdateAssetsStatus.FULL -> {
                //The user is using an outdated version
                isShowPopup.value = true
                url = "${StarbaseAPI().getGitHubStaticAssetURL()}/updates/${infoList.first().commit}/${infoList.first().commit}-${Language.TextLanguageInstance.folderName}-FULL.zip"
            }
            UpdateAssetsStatus.SKIP -> {
                //Skipped, maybe the user cannot connect to GitHub?
                isShowPopup.value = false
            }
        }

        //UI Part
        val acceptUpdate = remember { mutableStateOf(false) }
        val denyUpdate = remember { mutableStateOf(false) }
        //Show the update dialog to the user

        if(!denyUpdate.value){
            Popup(alignment = Alignment.Center) {
                AppDialog(
                    titleString = if(!acceptUpdate.value) "檢測到更新檔案" else "下載中...",
                    hazeState = hazeState,
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

                runBlocking {
                    delay(500)
                    val isSuccess = mutableStateOf(false)
                    val job = async {
                        downloadFromURLProgress(url = url, downloadProgress = downloadProgress, isSuccess)


                        if(!isSuccess.value) {
                            //Warning ...
                        }else {
                            //Update the local commit
                            Settings().putString("localCommit", infoList.first().commit)
                        }
                        isProcessing.value = false
                        isShowPopup.value = false
                        canUpdatePopup.value = false
                    }

                    job.await()
                    job.getCompleted()

                }
            }
        }
    }
}

@Composable
fun UpdateAssetsPopupDownloading(showPopup: MutableState<Boolean>, latestAssetsInfo: UpdateAssetsInfo, updateState: UpdateAssetsStatus, downloadProgress: MutableState<Long>) {
    val fileSize = latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L

    //State can be "DOWNLOADING", "UNZIPPING", "FINISH", "ERROR-NETWORK", "ERROR-UNZIP"
    val downloadState = remember { mutableStateOf(DownloadAssetsState.DOWNLOADING) }
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        //下載進度: 18.5% (1.85MB / 10.0MB)
        Text("下載進度: ${formatDecimal(downloadProgress.value / fileSize * 100)}% (${formatDecimalByte(downloadProgress.value / 1048576f, 2, isUnited = true)} / ${formatDecimalByte(fileSize / 1048576f,2, isUnited = true) })", style = FontSizeNormal16(), color = Color(0xFF222222))

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
    acceptUpdate: MutableState<Boolean>,
    denyUpdate: MutableState<Boolean>
) {
    val fileSizePretty = formatDecimalByte(
        (latestAssetsInfo.size["${Language.TextLanguageInstance.folderName}-${updateState.name}"] ?: 0L) / 1048576f //Present By MB
        ,2,
        isUnited = true
    )
    Column {
        Text(text = "更新檔案大小: $fileSizePretty", style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text("推薦在WI-FI環境下進行更新", style = FontSizeNormal16(), color = Color(0xFF222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Text("黑塔女士舉世無雙！黑塔女士聰明絕頂！黑塔女士沉魚落雁！", style = FontSizeNormalSmall(), color = Color(0x33222222), modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            UIButton(
                text = "之後吧",
                onClick = {
                    denyUpdate.value = true
                    isShowPopup.value = false
                },
                modifierTmp = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            UIButton(
                text = "確定",
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