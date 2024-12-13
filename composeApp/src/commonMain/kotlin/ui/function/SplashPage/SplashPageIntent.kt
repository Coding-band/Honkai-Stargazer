package ui.function.SplashPage

import dev.chrisbanes.haze.HazeState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SplashPageState(
    val showPopup: MutableState<Boolean> = mutableStateOf(true),
    val hasRefreshed: Boolean = false,
    val hazeState: HazeState = HazeState()
)

sealed class SplashPageIntent {
    data object Initialize : SplashPageIntent()
    data object RefreshData : SplashPageIntent()
    data class SetShowPopup(val showPopup: Boolean) : SplashPageIntent()
    data class SetHazeState(val hazeState: HazeState) : SplashPageIntent()
}