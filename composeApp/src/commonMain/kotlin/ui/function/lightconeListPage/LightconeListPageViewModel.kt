package ui.function.lightconeListPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import type.Lightcone

class LightconeListPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(LightconeListPageState())
    val state: StateFlow<LightconeListPageState> get() = _state

    fun handleIntent(intent: LightconeListPageIntent) {
        when (intent) {
            is LightconeListPageIntent.Initialize -> initialize()
            is LightconeListPageIntent.RefreshData -> refreshData()
            //is LightconeListPageIntent.SetFilter -> setFilter(intent.filter)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
            _state.value.lightconeList.value =
                (Lightcone.lcListJson as JsonArray).map { jsonElement ->
                    (Lightcone.getLightconeItemFromJSON(jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!, requireAttrData = true))
                } as ArrayList<Lightcone>
            _state.value.lightconeListFilted.value = _state.value.lightconeList.value
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.

        }
    }
}