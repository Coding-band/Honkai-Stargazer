package ui.function.relicListPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import type.Relic

data class RelicListPageState(
    val relicList: MutableState<ArrayList<Relic>> = mutableStateOf(arrayListOf()),
    val relicListFilted: MutableState<ArrayList<Relic>> = mutableStateOf(arrayListOf()),
    //val filter: ListFilter = ListFilter(),
)

sealed class RelicListPageIntent {
    data object Initialize : RelicListPageIntent()
    data object RefreshData : RelicListPageIntent()
    //data class SetFilter(val filter: ListFilter) : CharacterListPageIntent()
}