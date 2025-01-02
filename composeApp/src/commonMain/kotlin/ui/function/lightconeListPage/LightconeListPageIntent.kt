package ui.function.lightconeListPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import type.Lightcone

data class LightconeListPageState(
    val lightconeList: MutableState<ArrayList<Lightcone>> = mutableStateOf(arrayListOf()),
    val lightconeListFilted: MutableState<ArrayList<Lightcone>> = mutableStateOf(arrayListOf()),
    //val filter: ListFilter = ListFilter(),
)

sealed class LightconeListPageIntent {
    data object Initialize : LightconeListPageIntent()
    data object RefreshData : LightconeListPageIntent()
    //data class SetFilter(val filter: ListFilter) : CharacterListPageIntent()
}