package ui.function.characterListPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.Navigator
import type.Character
import type.CombatType
import type.ListFilter
import type.Path

class CharacterListPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(CharacterListPageState())
    val state: StateFlow<CharacterListPageState> get() = _state

    fun handleIntent(intent: CharacterListPageIntent) {
        when (intent) {
            is CharacterListPageIntent.Initialize -> initialize()
            is CharacterListPageIntent.RefreshData -> refreshData()
            //is CharacterListPageIntent.SetFilter -> setFilter(intent.filter)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
            _state.value.characterList.value =
                (Character.charListJson as JsonArray).map { jsonElement ->
                    (Character.getCharacterItemFromJSON(jsonElement.jsonObject["charId"]?.jsonPrimitive?.content!!, requireAttrData = true))
                } as ArrayList<Character>
            _state.value.characterListFilted.value = _state.value.characterList.value
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.

        }
    }

    @Deprecated("This function is not used right now, ListFilterTool instead.")
    private fun setFilter(filter: ListFilter) {
        viewModelScope.launch {
            // Set filter logic here
            _state.value.characterListFilted.value = _state.value.characterList.value.filter { char ->
                (if(filter.rarity != 0) char.rarity == filter.rarity else true) &&
                (if(filter.combatType != CombatType.Unspecified) char.combatType == filter.combatType else true) &&
                (if(filter.path != Path.Unspecified) char.path == filter.path else true) &&
                (if(filter.nameKeyWord != "") {
                    char.localName!!.contains(filter.nameKeyWord)
                            || char.registName!!.contains(filter.nameKeyWord)
                            || char.officialId.toString().contains(filter.nameKeyWord)
                } else true)
            } as ArrayList<Character>
        }
    }
}