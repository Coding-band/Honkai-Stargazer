package ui.function.splashPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import type.Character
import utils.app.Preferences

class SplashPageViewModel() : ViewModel() {
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
            Character.charListJson
            Character.charExtListJson
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
        }
    }

    private fun setShowPopup(showPopup: Boolean) {
        _state.value.showPopup.value = showPopup
    }
    private fun setHazeState(hazeState: HazeState) {
        _state.value = _state.value.copy(hazeState = hazeState)
    }
}