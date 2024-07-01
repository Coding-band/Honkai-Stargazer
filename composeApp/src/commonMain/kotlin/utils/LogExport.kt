package utils

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
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

        Settings().putString("errorLog", logFinal);
        println(Settings().getString("errorLog", "nope"))

        //Not done yet, will finish second part
    }

}
