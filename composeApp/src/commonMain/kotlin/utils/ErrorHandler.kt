package utils

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class ErrorHandler {
    enum class ErrorLevel(){
        TEST,WARNING,DANGER,INTERRUPT
    }

    companion object ErrorHandler {
        @Composable
        fun raiseErrorMessageSnack(error: Error) {
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope();

            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = error.message ?: "Unexpected Error")
            }

        }
        @Composable
        fun raiseErrorMessageSnack(errorStr: String) {
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope();

            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = errorStr ?: "Undefined Error")
            }

        }



    }
}