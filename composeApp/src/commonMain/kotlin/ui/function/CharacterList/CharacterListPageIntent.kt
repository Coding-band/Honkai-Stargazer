package ui.function.CharacterList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import type.Character
import type.ListFilter

data class CharacterListPageState(
    val characterList: MutableState<ArrayList<Character>> = mutableStateOf(arrayListOf()),
    //val characterEXTList: MutableState<ArrayList<Character>> = mutableStateOf(arrayListOf()),
    val characterListFilted: MutableState<ArrayList<Character>> = mutableStateOf(arrayListOf()),
    //val filter: ListFilter = ListFilter(),
)

sealed class CharacterListPageIntent {
    data object Initialize : CharacterListPageIntent()
    data object RefreshData : CharacterListPageIntent()
    //data class SetFilter(val filter: ListFilter) : CharacterListPageIntent()
}