package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import files.Res
import files.SortByAtk
import files.SortByDef
import files.SortByEnergy
import files.SortByHp
import files.SortByName
import files.SortByRare
import files.SortByTime
import files.bg_transparent
import files.ic_selected_orange_circle
import files.ic_sort_asc
import files.ic_sort_desc
import files.ui_icon_filter
import files.ui_icon_search
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Lightcone
import utils.annotation.DoItLater
import utils.app.Constants
import utils.app.FontSizeNormal16
import utils.app.Language
import utils.app.pxToDp
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
    val density = LocalDensity.current.density

    //For Language Change -> Apply Sort and Filter
    key(Language.TextLanguageInstance){
        filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterType, isAsc.value)
    }

    // UI
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .wrapContentHeight()
            .align(Alignment.BottomCenter)
        ) {
            val sorterButtonWidth = remember { mutableStateOf(212.dp) }

            //Sorter Spinner Choice
            AnimatedVisibility(
                visible = isShowing.value == "SORT",
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
            ){
                Column(modifier = Modifier
                    .width(sorterButtonWidth.value - 24.dp)
                    .background(Color(0xFFDDDDDD))
                    .align(Alignment.CenterHorizontally)
                ) {
                    sortChoiceList.forEachIndexed() { index, it ->
                        Row(modifier = Modifier
                            .background(if(sortChoiceIndex.value == index) Color(0x1A000000) else Color(0x00000000))
                            .clickable {
                                sortChoiceIndex.value = index
                                filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterType, isAsc.value)
                            }
                            .padding(10.dp)
                        ) {
                            Text(
                                text = removeStrQuote(it),
                                style = FontSizeNormal16(),
                                color = Color(0xFF222222),
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                            )
                            Image(
                                painterResource(if (sortChoiceIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                contentDescription = null
                            )
                        }
                    }
                }

            }

            Box(modifier = Modifier.height(8.dp))

            Row(Modifier
                .wrapContentHeight()
                .navigationBarsPadding()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                .wrapContentWidth()
            ) {
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
                    modifierTmp = Modifier
                        .widthIn(100.dp,212.dp)
                        .height(46.dp)
                        .onSizeChanged { sorterButtonWidth.value = pxToDp(it.width, density = density) },
                    text = removeStrQuote(sortChoiceList[sortChoiceIndex.value]),
                    icon = if(isAsc.value){ Res.drawable.ic_sort_asc }else{ Res.drawable.ic_sort_desc },
                    buttonSize = UIButtonSize.NormalTextLeftWithLine,
                    onClick = {
                        isShowing.value = if("SORT" == isShowing.value) "NOPE" else "SORT"
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

        //Popup
        @DoItLater("收藏 & 已擁有選擇")
        AnimatedVisibility(
            visible = isShowing.value == "FILTER",
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize(),
        ){
            Box(modifier = Modifier
                .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH)
                .wrapContentHeight()
                .align(Alignment.Center)
                .background(Color(0xCCF3F9FF), RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
                .clip(shape = RoundedCornerShape(4.dp, 20.dp, 4.dp, 4.dp))
                .padding(16.dp)
            ) {

            }
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