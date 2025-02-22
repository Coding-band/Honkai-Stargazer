package ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
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
import org.jetbrains.compose.resources.StringResource
import types.Character
import types.Lightcone
import utils.app.Language
import utils.app.removeStrQuote


enum class ListFilterType {
    CHARACTER,
    LIGHTCONE,
    RELIC,
}

val LIST_FILTER_TOOL_HEIGHT = (46.dp + 16.dp + 8.dp)

@Composable
fun <T> ListFilterTool(
    modifier: Modifier = Modifier,
    originList: ArrayList<T>,
    filterType: ListFilterType,
    filtedList: MutableState<ArrayList<T>>,
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

    key(Language.TextLanguageInstance){
        filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterType, isAsc.value)
    }

    // UI
    Box(Modifier.fillMaxSize()) {
        Row(Modifier.wrapContentHeight().align(Alignment.BottomCenter).navigationBarsPadding().padding(start = 32.dp, end = 32.dp, bottom = 16.dp)) {
            UIButton(
                modifierTmp = Modifier.size(46.dp),
                icon = Res.drawable.ui_icon_filter,
                buttonSize = UIButtonSize.SmallChoice,
                onClick = {
                    isShowing.value = "FILTER"
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            UIButton(
                modifierTmp = Modifier.weight(1f).height(46.dp),
                text = removeStrQuote(sortChoiceList[sortChoiceIndex.value]),
                icon = if(isAsc.value){ Res.drawable.ic_sort_asc }else{ Res.drawable.ic_sort_desc },
                buttonSize = UIButtonSize.NormalTextLeftWithLine,
                onClick = {
                    isShowing.value = "SORT"
                    sortChoiceIndex.value = (sortChoiceIndex.value + 1) % sortChoiceList.size
                    filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterType, isAsc.value)
                },
                iconOnClick = {
                    isAsc.value = !isAsc.value
                    filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterType, isAsc.value)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            UIButton(
                modifierTmp = Modifier.size(46.dp),
                icon = Res.drawable.ui_icon_search,
                buttonSize = UIButtonSize.SmallChoice,
                onClick = {
                    isShowing.value = "SEARCH"
                }
            )
        }
    }
}

fun <T> applySortAndFilter(
    originList: List<T>,
    sortChoice: StringResource,
    filterType: ListFilterType,
    isAsc: Boolean
): ArrayList<T> {
    val sortedList = when (filterType) {
        ListFilterType.CHARACTER -> {
            when (sortChoice) {
                Res.string.SortByName -> originList.sortedBy { (it as Character).registName }
                Res.string.SortByAtk -> originList.sortedBy { (it as Character).characterAttrData!!.atk }
                Res.string.SortByDef -> originList.sortedBy { (it as Character).characterAttrData!!.def }
                Res.string.SortByHp -> originList.sortedBy { (it as Character).characterAttrData!!.hp }
                Res.string.SortByEnergy -> originList.sortedBy { (it as Character).characterAttrData!!.energy }
                Res.string.SortByRare -> originList.sortedBy { (it as Character).rarity }
                else -> originList
            }
        }

        ListFilterType.LIGHTCONE -> {
            when (sortChoice) {
                Res.string.SortByName -> originList.sortedBy { (it as Character).registName }
                Res.string.SortByAtk -> originList.sortedBy { (it as Character).characterAttrData!!.atk }
                Res.string.SortByDef -> originList.sortedBy { (it as Character).characterAttrData!!.def }
                Res.string.SortByHp -> originList.sortedBy { (it as Character).characterAttrData!!.hp }
                Res.string.SortByEnergy -> originList.sortedBy { (it as Character).characterAttrData!!.energy }
                Res.string.SortByRare -> originList.sortedBy { (it as Character).rarity }
                else -> originList
            }
        }
        else -> originList
    }

    return if (!isAsc) {
        ArrayList(sortedList)
    } else {
        ArrayList(sortedList.reversed())
    }
}