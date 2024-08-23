package components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import files.Res
import files.SortByAtk
import files.SortByDef
import files.SortByEnergy
import files.SortByHp
import files.SortByName
import files.SortByRare
import files.SortByTime
import files.ic_sort_asc
import files.ic_sort_desc
import files.ui_icon_filter
import files.ui_icon_search
import types.Character
import utils.UtilTools


enum class ListFilterType {
    CHARACTER,
    LIGHTCONE,
    RELIC,
}

val LIST_FILTER_TOOL_HEIGHT = (46.dp + 16.dp + 8.dp)

@Composable
fun <T> ListFilterTool(
    modifier: Modifier = Modifier,
    filterList: ArrayList<T>,
    filterType: ListFilterType,
    onFilterApplied: (ArrayList<T>) -> Unit
) {
    val isShowing = rememberSaveable { mutableStateOf("NOPE") }
    val isAsc = rememberSaveable { mutableStateOf(false) }
    val sortChoiceIndex = rememberSaveable { mutableStateOf(0) }
    val sortChoiceList = arrayListOf(
        Res.string.SortByTime,
        Res.string.SortByName,
        Res.string.SortByAtk,
        Res.string.SortByDef,
        Res.string.SortByHp,
        Res.string.SortByEnergy,
        Res.string.SortByRare,
    ).filter {
        if(filterType == ListFilterType.LIGHTCONE){
            it != Res.string.SortByEnergy
        }else{
            true
        }
    }

    fun applySortAndFilter() {
        val sortedList = when (filterType) {
            ListFilterType.CHARACTER -> {
                when (sortChoiceList[sortChoiceIndex.value]) {
                    Res.string.SortByTime -> filterList
                    Res.string.SortByName -> filterList.sortedBy { (it as Character).registName }
                    Res.string.SortByAtk -> filterList.sortedBy { (it as Character).characterAttrData?.atk }
                    Res.string.SortByDef -> filterList.sortedBy { (it as Character).characterAttrData?.def }
                    Res.string.SortByHp -> filterList.sortedBy { (it as Character).characterAttrData?.hp }
                    Res.string.SortByEnergy -> filterList.sortedBy { (it as Character).characterAttrData?.energy }
                    Res.string.SortByRare -> filterList.sortedBy { (it as Character).rarity }
                    else -> filterList
                }
            }

            else -> {
                filterList
            }
        }

        val finalList = if (isAsc.value) sortedList else sortedList.reversed()
        onFilterApplied(ArrayList(finalList))
    }

    // UI
    Box(Modifier.fillMaxSize()) {
        Row(Modifier.wrapContentHeight().align(Alignment.BottomCenter).navigationBarsPadding().padding(start = 32.dp, end = 32.dp, bottom = 16.dp)) {
            UIButton(
                icon = Res.drawable.ui_icon_filter,
                buttonSize = UIButtonSize.SmallChoice,
                onClick = {
                    isShowing.value = "FILTER"
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            UIButton(
                modifierTmp = Modifier.weight(1f).height(46.dp),
                text = UtilTools().removeStringResDoubleQuotes(sortChoiceList[sortChoiceIndex.value]),
                icon = if(isAsc.value){ Res.drawable.ic_sort_asc }else{ Res.drawable.ic_sort_desc },
                buttonSize = UIButtonSize.NormalTextLeftWithLine,
                onClick = {
                    isShowing.value = "SORT"
                    sortChoiceIndex.value = (sortChoiceIndex.value + 1) % sortChoiceList.size
                    applySortAndFilter()
                },
                iconOnClick = {
                    isAsc.value = !isAsc.value
                    applySortAndFilter()
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            UIButton(
                icon = Res.drawable.ui_icon_search,
                buttonSize = UIButtonSize.SmallChoice,
                onClick = {
                    isShowing.value = "SEARCH"
                }
            )
        }
    }
}