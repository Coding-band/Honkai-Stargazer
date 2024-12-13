package ui.function.SplashPage

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
import types.UserAbyssRecord.Companion.refreshMOCData
import types.UserAbyssRecord.Companion.refreshPFData
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.Preferences

class SplashPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(SplashPageState())
    val state: StateFlow<SplashPageState> get() = _state

    fun handleIntent(intent: SplashPageIntent) {
        when (intent) {
            is SplashPageIntent.Initialize -> initialize()
            is SplashPageIntent.RefreshData -> refreshData()
            is SplashPageIntent.SetShowPopup -> setShowPopup(intent.showPopup)
            is SplashPageIntent.SetHazeState -> setHazeState(intent.hazeState)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            val showPopup = !Preferences().AppSettings.isLangInitialized()
            _state.value.showPopup.value = showPopup
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.
            /*
            if (INSTANCE.uid != "000000000") {
                async { refreshCharacterList() }.await()
                async { refreshNoteData() }.await()
                async { refreshMOCData() }.await()
                async { refreshPFData() }.await()
            }
             */

            withContext(Dispatchers.Main) {
                /*
                if(INSTANCE.uid != "000000000" && !hasRefreshed.value ){
                    Preferences().Leaderboard.updatedLeaderboard()
                }
                 */

                if (!_state.value.showPopup.value) {
                    // Temporate Delay, will remove after the API is ready
                    kotlinx.coroutines.delay(2000)
                    navigator.navigateLimited(
                        Screen.HomePage.route,
                        options = NavOptions(
                            popUpTo = PopUpTo(Screen.SplashPage.route, true)
                        )
                    )
                }
            }
        }
    }

    private fun setShowPopup(showPopup: Boolean) {
        _state.value.showPopup.value = showPopup
    }
    private fun setHazeState(hazeState: HazeState) {
        _state.value = _state.value.copy(hazeState = hazeState)
    }
}