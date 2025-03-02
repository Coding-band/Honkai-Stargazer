package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeSource
import files.ConfirmBTN
import files.FilterTitle
import files.NoDataYet
import files.Res
import files.Reset
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
import files.pom_pom_failed_issue
import files.ui_icon_checkbox_checked
import files.ui_icon_checkbox_empty
import files.ui_icon_close
import files.ui_icon_filter
import files.ui_icon_search
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.CombatType
import types.FilterEnum
import types.Lightcone
import types.Path
import ui.navigation.hazeStateRoot
import ui.screens.UserCharPageDivider
import ui.screens.globalHazeBlur
import utils.annotation.DoItLater
import utils.app.Constants
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.DialogPopUpZIndex
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormal20
import utils.app.HazeBlurDp10
import utils.app.Language
import utils.app.hazeEffectSG3
import utils.app.pxToDp
import utils.app.rememberMutableStateListJsonOf
import utils.app.removeStrQuote
import utils.app.showFunctionIsDevelopingToast
import kotlin.math.max


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
    filterChoiceArray: SnapshotStateList<FilterEnum>,
    filtedList: MutableState<ArrayList<T>>,
    hazeState: HazeState = hazeStateRoot
) {
    val isShowing = rememberSaveable { mutableStateOf("NOPE") }
    val isAsc = rememberSaveable { mutableStateOf(false) }
    val sortChoiceIndex = rememberSaveable { mutableStateOf(0) }
    val isReloadState = rememberSaveable { mutableStateOf(true) }
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
    val filterChoiceList = arrayListOf<FilterEnum>().apply {
        for(i in 0 until max(
            if(filterType == ListFilterType.CHARACTER) CombatType.entries.filterNot { it == CombatType.Unspecified }.size else 0,
            Path.entries.filterNot { it == Path.Unspecified }.size
        )){
            if(i < CombatType.entries.filterNot { it == CombatType.Unspecified }.size && filterType == ListFilterType.CHARACTER){
                add(CombatType.entries[i])
            }
            if(i < Path.entries.filterNot { it == Path.Unspecified }.size){
                add(Path.entries[i])
            }
        }
    }

    val density = LocalDensity.current.density

    //For Language Change -> Apply Sort and Filter
    key(Language.TextLanguageInstance, isReloadState.value){
        if(isReloadState.value){
            filtedList.value = applySortAndFilter(originList, sortChoiceList[sortChoiceIndex.value], filterChoiceArray, filterType, isAsc.value)
            isReloadState.value = false
        }
    }

    // UI
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .wrapContentHeight()
            .align(Alignment.BottomCenter)
        ) {
            val sorterButtonWidth = remember { mutableStateOf(212.dp) }

            @DoItLater("收藏 & 已擁有選擇")
            AnimatedVisibility(
                visible = isShowing.value == "SORT" || isShowing.value == "FILTER",
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
                    .align(Alignment.CenterHorizontally),
            ){
                if (isShowing.value == "FILTER"){
                    AppDialog(modifier = Modifier
                        .widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH),
                        isPopupShow = mutableStateOf(isShowing.value == "SORT" || isShowing.value == "FILTER"),
                        titleString = removeStrQuote(Res.string.FilterTitle),
                        components = {
                            VerticalGrid(
                                columns = SimpleGridCells.Fixed(2),
                                modifier = modifier.fillMaxWidth().wrapContentHeight(),
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                            ){
                                filterChoiceList.mapIndexed { index, any ->
                                    if(filterChoiceList.size == index+1 && (filterChoiceList.size) % 2 == 1 && any is Path){
                                        Row {  }
                                    }

                                    Row(modifier = Modifier
                                        .background(color = Color(0xFFFFFFFF))
                                        .clickable {
                                            if(filterChoiceArray.contains(any)){
                                                filterChoiceArray.remove(any)
                                            }else{
                                                filterChoiceArray.add(any)
                                            }
                                        }
                                        .padding(8.dp)
                                    ) {
                                        Image(
                                            painterResource(
                                                when(any){
                                                    is CombatType -> any.iconColor
                                                    is Path ->  any.iconAbyss
                                                    else -> Res.drawable.pom_pom_failed_issue
                                                }
                                            ),
                                            contentDescription = "Filter Choice Icon",
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = removeStrQuote(
                                                when(any){
                                                    is CombatType -> any.resName
                                                    is Path ->  any.resName
                                                    else -> Res.string.NoDataYet
                                                }
                                            ),
                                            style = FontSizeNormal14(),
                                            color = Color(0xFF222222),
                                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                                        )

                                        Image(
                                            painterResource(if(filterChoiceArray.contains(any)) Res.drawable.ui_icon_checkbox_checked else Res.drawable.ui_icon_checkbox_empty),
                                            contentDescription = "Filter Choice Checkbox",
                                            modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            }
                        },
                        componentsBottom = {
                            Column(modifier = Modifier.background(Color(0xFF222222)).padding(16.dp)) {
                                //Checkboxes for "Owned" and "Favorite" ONLY
                                Row {

                                }
                                //Spacer(Modifier.height(12.dp))
                                Row {
                                    UIButton(
                                        modifierTmp = Modifier.fillMaxWidth().weight(1f),
                                        text = removeStrQuote(Res.string.Reset),
                                        buttonSize = UIButtonSize.Normal,
                                        onClick = {
                                            //Reset All Filters
                                            filterChoiceArray.clear()
                                            isReloadState.value = true
                                            isShowing.value = "NOPE"
                                        }
                                    )
                                    Spacer(Modifier.width(16.dp))
                                    UIButton(
                                        modifierTmp = Modifier.fillMaxWidth().weight(1f),
                                        text = removeStrQuote(Res.string.ConfirmBTN),
                                        buttonSize = UIButtonSize.Normal,
                                        onClick = {
                                            //Reset All Filters
                                            isReloadState.value = true
                                            isShowing.value = "NOPE"
                                        }
                                    )
                                }
                            }
                        }
                    )
                }else if (isShowing.value == "SORT"){
                    //Sorter Spinner Choice
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
                                    isReloadState.value = true
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
            }


            Box(modifier = Modifier.height(8.dp))

            //Bottom Tool Row
            Row(Modifier
                .wrapContentHeight()
                .navigationBarsPadding()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                .wrapContentWidth()
                .widthIn(72.dp + 116.dp, 116.dp + 212.dp)
                .align(Alignment.CenterHorizontally)
            ) {
                UIButton(
                    modifierTmp = Modifier.size(46.dp),
                    icon = Res.drawable.ui_icon_filter,
                    buttonSize = UIButtonSize.SmallChoice,
                    onClick = {
                        isShowing.value = if("FILTER" == isShowing.value) "NOPE" else "FILTER"
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Row(modifier = Modifier.weight(1f)) {
                    UIButton(
                        modifierTmp = Modifier
                            .fillMaxWidth()
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
                            isReloadState.value = true
                        }
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))

                @DoItLater("ListFilterTool Search Button")
                UIButton(
                    modifierTmp = Modifier.size(46.dp),
                    icon = Res.drawable.ui_icon_search,
                    buttonSize = UIButtonSize.SmallChoice,
                    onClick = {
                        //isShowing.value = "SEARCH"
                        showFunctionIsDevelopingToast()
                    }
                )
            }
        }
    }
}

fun <T> applySortAndFilter(
    originList: List<T>,
    sortChoice: StringResource,
    filterChoiceArray: SnapshotStateList<FilterEnum>,
    filterType: ListFilterType,
    isAsc: Boolean
): ArrayList<T> {

    //if isAllPathAllow and isAllCombatTypeAllow, then return originList
    //else if isAllPathAllow, then filter by CombatType, return originList with only checked CombatType
    //else if isAllCombatTypeAllow, then filter by Path, return originList with only checked Path
    //else, return originList with only checked Path and CombatType
    val filterPaths = filterChoiceArray.filterIsInstance<Path>()
    val filterCombatType = filterChoiceArray.filterIsInstance<CombatType>()


    val originListFilted = ArrayList(
        originList.filter {
            when (filterType) {
                ListFilterType.CHARACTER -> {
                    val character = it as Character
                    (filterPaths.isEmpty() || filterPaths.contains(character.path)) &&
                            (filterCombatType.isEmpty() || filterCombatType.contains(character.combatType))
                }
                ListFilterType.LIGHTCONE -> {
                    val lightcone = it as Lightcone
                    (filterPaths.isEmpty() || filterPaths.contains(lightcone.path))
                }
                else -> true
            }
        }
    )

    val sortedList = originListFilted.sortedWith(compareBy<T> {
        when (filterType) {
            ListFilterType.CHARACTER -> when (sortChoice) {
                Res.string.SortByName -> (it as Character).registName
                Res.string.SortByAtk -> (it as Character).characterAttrData!!.atk
                Res.string.SortByDef -> (it as Character).characterAttrData!!.def
                Res.string.SortByHp -> (it as Character).characterAttrData!!.hp
                Res.string.SortByEnergy -> (it as Character).characterAttrData!!.energy
                Res.string.SortByRare -> (it as Character).rarity
                else -> null
            }
            ListFilterType.LIGHTCONE -> when (sortChoice) {
                Res.string.SortByName -> (it as Lightcone).registName
                Res.string.SortByAtk -> (it as Lightcone).lcAttrData!!.atk
                Res.string.SortByDef -> (it as Lightcone).lcAttrData!!.def
                Res.string.SortByHp -> (it as Lightcone).lcAttrData!!.hp
                Res.string.SortByEnergy -> (it as Lightcone).lcAttrData!!.energy
                Res.string.SortByRare -> (it as Lightcone).rarity
                else -> null
            }
            else -> null
        }
    })

    return if (!isAsc) {
        ArrayList(sortedList)
    } else {
        ArrayList(sortedList.reversed())
    }
}