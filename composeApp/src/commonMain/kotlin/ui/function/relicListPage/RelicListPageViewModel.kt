package ui.function.relicListPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import type.Relic

class RelicListPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(RelicListPageState())
    val state: StateFlow<RelicListPageState> get() = _state

    fun handleIntent(intent: RelicListPageIntent) {
        when (intent) {
            is RelicListPageIntent.Initialize -> initialize()
            is RelicListPageIntent.RefreshData -> refreshData()
            //is RelicListPageIntent.SetFilter -> setFilter(intent.filter)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
            _state.value.relicList.value =
                (Relic.relicListJson as JsonArray).map { jsonElement ->
                    (Relic.getRelicItemFromJSON(jsonElement.jsonObject["fileName"]?.jsonPrimitive?.content!!))
                } as ArrayList<Relic>
            _state.value.relicListFilted.value = _state.value.relicList.value
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.

        }
    }
}