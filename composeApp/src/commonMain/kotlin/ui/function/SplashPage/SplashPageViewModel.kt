package ui.function.SplashPage

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import type.Character
import types.UserAbyssRecord.Companion.refreshMOCData
import types.UserAbyssRecord.Companion.refreshPFData
import ui.navigation.Screen
import ui.navigation.navigateLimited
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