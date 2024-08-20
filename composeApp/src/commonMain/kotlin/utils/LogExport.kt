package utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dokar.sonner.ToastType
import com.dokar.sonner.ToasterState
import com.russhwolf.settings.Settings
import com.voc.honkaistargazer.BuildKonfig
import files.FunctionStillInDevelop
import files.Res
import files.pom_pom_failed_issue
import getDeviceInfo
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import types.AppInfo
import types.DeviceInfo
import utils.LogExportObj.Companion.SnackbarHostStateInstance
import utils.navigation.toastInstance
import utils.starbase.StarbaseAPI
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FormatStringsInDatetimeFormats::class)
val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]") }

lateinit var ToastStrFunctionStillInDevelop: String

@Composable
fun initToastStr(){
    ToastStrFunctionStillInDevelop = UtilTools().removeStringResDoubleQuotes(Res.string.FunctionStillInDevelop)
}

@Serializable
data class LogExportObj @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("class_name")
    var className: String,

    @SerialName("function_name")
    var functionName: String,

    @SerialName("error_time")
    var errorTime: String = dateFormat.format(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())),

    @SerialName("error_time_ms")
    var errorTimeMS: Long = Clock.System.now().toEpochMilliseconds(),

    @SerialName("device_info")
    @EncodeDefault
    var deviceInfo: DeviceInfo = getDeviceInfo(),

    @SerialName("app_info")
    @EncodeDefault
    var appInfo: AppInfo = AppInfo(BuildKonfig.appProfile,BuildKonfig.appVersionName, BuildKonfig.appVersionCode),

    @SerialName("exception_message")
    var exceptionMessage: String,

    @SerialName("exception_stack")
    var exceptionStack: String,
){
    companion object{
        var SnackbarHostStateInstance = SnackbarHostState()
    }
}

suspend fun raiseErrorMessageSnack(error: Exception) {
    SnackbarHostStateInstance.showSnackbar(
        message = (error.message) ?: "Unexpected Error",
        actionLabel = "CLOSE",
        duration = SnackbarDuration.Indefinite
    )
}

suspend fun raiseErrorMessageSnack(errorString: String, snackbarHostState: SnackbarHostState?) {
    snackbarHostState?.showSnackbar(message = errorString ?: "Undefined Error")

}

fun showErrorToast(errorLogExportObj: LogExportObj) {
    toastInstance.show(
        message = "${errorLogExportObj.className} - ${errorLogExportObj.functionName} : ${errorLogExportObj.exceptionMessage}",
        type = ToastType.Error,
        duration = 30000.milliseconds,
    )
}

fun showSuccessToast(toasterState: ToasterState = toastInstance, message: String) {
    toasterState.show(
        message = message,
        type = ToastType.Success,
        duration = 10000.milliseconds,
    )
}
fun showWarningToast(toasterState: ToasterState = toastInstance, message: String) {
    toasterState.show(
        message = message,
        type = ToastType.Warning,
        duration = 10000.milliseconds,
    )
}

fun showFunctionIsDevelopingToast(toasterState: ToasterState = toastInstance) {
    toasterState.show(
        message = ToastStrFunctionStillInDevelop,
        type = ToastType.Warning,
        duration = 5000.milliseconds,
    )
}

/**
 *      try {
 *         throw Exception("Testing Error")
 *     }catch (e : Exception){
 *         errorLogExport("CharacterInfoPageScreen", "CharacterInfoFullImgWithRare()", e)
 *     }
 */
@OptIn(FormatStringsInDatetimeFormats::class)
fun errorLogExport(className: String, functionName: String, error: Exception) {
    //Prepare what will be export
    val timeStamp = Clock.System.now();
    val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]") }
    val logExportObj = LogExportObj(
        className = className,
        functionName = functionName,
        errorTime = dateFormat.format(timeStamp.toLocalDateTime(TimeZone.currentSystemDefault())),
        errorTimeMS = timeStamp.toEpochMilliseconds(),
        deviceInfo = getDeviceInfo(),
        appInfo = AppInfo(BuildKonfig.appProfile,BuildKonfig.appVersionName, BuildKonfig.appVersionCode),
        exceptionMessage = (if (error.message === null) error.stackTraceToString().split("\n")[0] else error.message!!),
        exceptionStack = error.stackTraceToString()
    )

    //Error Log will save as Preference
    Settings().putString("errorLogExportObj", Json.encodeToString(logExportObj));
    Settings().putBoolean("errorLogDisplayed", false);

    showErrorToast(logExportObj)

    if(BuildKonfig.appProfile == "DEV" || BuildKonfig.appProfile == "C.BETA" || BuildKonfig.appProfile == "BETA"){
        error.printStackTrace()
    }
}

/**
 * This function is aims to show the error log after restart app
 * Which will ask user whether report or not
 */
@Composable
fun checkHasErrorLogFromLastCrash() {
    //Return if already shown to user
    val errorLogExportObj: LogExportObj
    try {
        errorLogExportObj = Json.decodeFromString<LogExportObj>(
            Settings().getString(
                "errorLogExportObj",
                "{}"
            )
        )
    }catch (_: Exception){
        return
    }

    println("errorLogExportObj : ${Settings().getString(
        "errorLogExportObj",
        "{}"
    )}")

    val openAlertDialog = remember { mutableStateOf(true) }
    openAlertDialog.value = !Settings().getBoolean("errorLogDisplayed",false);
    Settings().putBoolean("errorLogDisplayed",true)

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openAlertDialog.value = false;
            },
            confirmButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false;
                    sendLogToServer(errorLogExportObj);
                }) {
                    Text("Report")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false;
                }) {
                    Text("Cancel")
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(24.dp).padding(end = 8.dp),
                        painter = painterResource(Res.drawable.pom_pom_failed_issue),
                        contentDescription = "Error Log Icon"
                    )
                    Text(
                        text = "Error Log",
                        fontSize = 24.sp
                    )
                }
            },
            text = {
                Column {
                    Text(
                        "It seems there have some bugs caused crashes previously. Could you please sare the error log to us for bug-fixing?\n" +
                                "We will only collect:\n" +
                                "· Device's Model:\n" +
                                "· Device's OS Version:\n"
                    )

                    //No need for app-translation!
                    Box(
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                topEnd = 8.dp,
                                topStart = 8.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 8.dp
                            )
                        ).background(Color(0xFF333333))
                        //.verticalScroll(rememberScrollState())
                    ) {
                        Text(

                            "Class : " + errorLogExportObj.className + "\n" +
                                    "Function : " + errorLogExportObj.functionName + "\n" +
                                    "Time : " + errorLogExportObj.errorTime + "\n" +
                                    "TimeStamp : " + errorLogExportObj.errorTimeMS + "\n" +
                                    "DeviceInfo :  ${errorLogExportObj.deviceInfo.deviceModel} (${errorLogExportObj.deviceInfo.deviceOSName} ${errorLogExportObj.deviceInfo.deviceOSVersion})\n" +
                                    "AppInfo :  ${errorLogExportObj.appInfo.appVersionName})\n" +
                                    "Error Message :  ${errorLogExportObj.exceptionMessage}",
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier.padding(8.dp),
                            fontSize = FontSizeNormalSmall().fontSize,
                            maxLines = 10
                        )
                    }


                }
            }

        )
    }
}

fun sendLogToServer(errorLogExportObj: LogExportObj) {
    StarbaseAPI().sendErrorLogs(errorLogExportObj)
}