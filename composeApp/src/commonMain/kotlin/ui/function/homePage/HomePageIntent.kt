package ui.function.HomePage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import dev.chrisbanes.haze.HazeState
import ui.function.HomePage.components.HomePageBlocks
import utils.app.Preferences

data class HomePageState(
    val showPopup: MutableState<Boolean> = mutableStateOf(true),
    val hazeState: HazeState = HazeState(),
    val threeDotDialogDisplay: MutableState<Boolean> = mutableStateOf(false),
    val threeDotDialogPos: MutableState<Offset> = mutableStateOf(Offset(0f, 0f)),
    val homeMenuBlockList: MutableState<ArrayList<HomePageBlocks.HomePageBlockItem>> = mutableStateOf(Preferences().HomePageMenu.getHomePageMenuArray())
)

sealed class HomePageIntent {
    data object Initialize : HomePageIntent()
    data object RefreshData : HomePageIntent()
    data class SetShowPopup(val showPopup: Boolean) : HomePageIntent()
    data class SetHazeState(val hazeState: HazeState) : HomePageIntent()
    data class SetThreeDotDialogDisplay(val threeDotDialogDisplay: Boolean) : HomePageIntent()
    data class SetThreeDotDialogPos(val threeDotDialogPos: Offset) : HomePageIntent()
    data class SetHomeMenuBlockList(val homeMenuBlockList: ArrayList<HomePageBlocks.HomePageBlockItem>) : HomePageIntent()
}