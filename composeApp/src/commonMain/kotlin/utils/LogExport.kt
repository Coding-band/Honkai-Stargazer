package utils

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings
import getDeviceName
import getTimeStamp

class LogExport {
    enum class ErrorLevel() {
        TEST, WARNING, DANGER, INTERRUPT
    }

    suspend fun raiseErrorMessageSnack(error: Exception, snackbarHostState: SnackbarHostState) {
        snackbarHostState.showSnackbar(
            message = (error.message) ?: "Unexpected Error",
            actionLabel = "CLOSE",
            duration = SnackbarDuration.Indefinite
        )
    }

    suspend fun raiseErrorMessageSnack(errorString: String, snackbarHostState: SnackbarHostState) {
        snackbarHostState.showSnackbar(message = errorString ?: "Undefined Error")

    }

    /**
     *      try {
     *         throw Exception("Testing Error")
     *     }catch (e : Exception){
     *         LogExport().errorLogExport("CharacterInfoPageScreen", "CharacterInfoFullImgWithRare()", e)
     *     }
     */
    fun errorLogExport(className: String, functionName: String, error: Exception) {
        //Prepare what will be export
        val logFinal = "[ERROR LOG]" + "\n" +
                "Class : " + className + "\n" +
                "Function : " + functionName + "\n" +
                "Time : " + UtilTools().unixTimestampToFormattedString((getTimeStamp())) + "\n" +
                "TimeStamp : " + getTimeStamp() + "\n" +
                "Device Name : " + getDeviceName() + "\n" +
                "App Version : " + "BETA 2.4.0 (1234)" + "\n\n" +
                error.stackTraceToString()

        //Error Log will save as Preference
        Settings().putString("errorLogContent", logFinal);
        Settings().putString("errorLogTimestamp", logFinal);
    }

    /**
     * This function is aims to
     */
    @Composable
    fun checkHasErrorLogFromLastCrash(){

    }

}
