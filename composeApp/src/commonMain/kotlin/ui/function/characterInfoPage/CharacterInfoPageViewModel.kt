package ui.function.characterInfoPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator

class CharacterInfoPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(CharacterInfoPageState())
    val state: StateFlow<CharacterInfoPageState> get() = _state

    fun handleIntent(intent: CharacterInfoPageIntent) {
        when (intent) {
            is CharacterInfoPageIntent.Initialize -> initialize()
            is CharacterInfoPageIntent.RefreshData -> refreshData()
            is CharacterInfoPageIntent.SetHazeState -> setHazeState(intent.hazeState)
            is CharacterInfoPageIntent.SetCharacterInfoJson -> setCharacterInfoJson(intent.charInfoJson)
            is CharacterInfoPageIntent.SetCharOnlyWeightJson -> setCharOnlyWeightJson(intent.charOnlyWeightJson)
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

        }
    }
    private fun setHazeState(hazeState: HazeState) {
        _state.value = _state.value.copy(hazeState = hazeState)
    }
    private fun setCharacterInfoJson(charInfoJson: JsonElement) {
        _state.value.charInfoJson.value = charInfoJson
    }
    private fun setCharOnlyWeightJson(charOnlyWeightJson: JsonElement) {
        _state.value.charOnlyWeightJson.value = charOnlyWeightJson
    }
}