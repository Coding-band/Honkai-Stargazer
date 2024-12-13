package ui.function.HomePage

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import ui.function.HomePage.components.HomePageBlocks
import ui.function.SplashPage.SplashPageIntent
import ui.function.SplashPage.SplashPageState
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.Preferences
import utils.starbase.StarbaseAPI

class HomePageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(HomePageState())
    val state: StateFlow<HomePageState> get() = _state

    fun handleIntent(intent: HomePageIntent) {
        when (intent) {
            is HomePageIntent.Initialize -> initialize()
            is HomePageIntent.RefreshData -> refreshData()
            is HomePageIntent.SetShowPopup -> setShowPopup(intent.showPopup)
            is HomePageIntent.SetHazeState -> setHazeState(intent.hazeState)
            is HomePageIntent.SetThreeDotDialogDisplay -> setThreeDotDialogDisplay(intent.threeDotDialogDisplay)
            is HomePageIntent.SetThreeDotDialogPos -> setThreeDotDialogPos(intent.threeDotDialogPos)
            is HomePageIntent.SetHomeMenuBlockList -> setHomeMenuBlockList(intent.homeMenuBlockList)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.

            /*
            var inited by rememberSaveable { mutableStateOf(false) }
            if(!inited){
                LaunchedEffect(Unit){
                    CoroutineScope(Dispatchers.Default).launch {
                        if(INSTANCE.uid != "000000000"){
                            async { StarbaseAPI().updateUserAccountInfo() }.await()
                            async { StarbaseAPI().updateCharData() }.await()
                            async { StarbaseAPI().updateMOCData() }.await()
                            async { StarbaseAPI().updatePFData() }.await()
                        }
                        inited = true
                    }
                }
                initCharList()
                initLcList()
                initRelicList()
                initMOCList()
                initPFList()
            }
             */
        }
    }

    private fun setShowPopup(showPopup: Boolean) {
        _state.value.showPopup.value = showPopup
    }
    private fun setHazeState(hazeState: HazeState) {
        _state.value = _state.value.copy(hazeState = hazeState)
    }
    private fun setThreeDotDialogDisplay(threeDotDialogDisplay: Boolean) {
        _state.value.threeDotDialogDisplay.value = threeDotDialogDisplay
    }
    private fun setThreeDotDialogPos(threeDotDialogPos: Offset) {
        _state.value.threeDotDialogPos.value = threeDotDialogPos
    }
    private fun setHomeMenuBlockList(homeMenuBlockList: ArrayList<HomePageBlocks.HomePageBlockItem>) {
        _state.value.homeMenuBlockList.clear()
        _state.value.homeMenuBlockList.addAll(homeMenuBlockList)
    }
}