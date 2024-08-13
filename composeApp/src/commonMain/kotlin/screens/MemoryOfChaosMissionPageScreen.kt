package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import components.DropdownMenuNoPadding
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeaderAlpha
import components.TitleHeader
import components.UIButton
import components.UIButtonSize
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import files.Res
import files.bg_transparent
import files.ic_moc_buff_icon
import files.ic_selected_orange_circle
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import types.MemoryOfChaosList
import utils.FontSizeNormal14
import utils.Language.Companion.TextLanguageInstance
import utils.UtilTools

@Composable
fun MemoryOfChaosMissionPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {
    var density = LocalDensity.current.density
    val hazeState = remember { HazeState() }

    val mocList = arrayListOf<MemoryOfChaosList>()

    println(MemoryOfChaosList.getMocList())


    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            item { Spacer(Modifier.height(PAGE_HEADER_HEIGHT + 8.dp)) }

            item {
                //Spinner / Dropdown
                val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
                val isDropDownOpen = remember { mutableStateOf(false) }
                val mocChoiceIndex = remember { mutableStateOf(0) }
                Box {
                    Row(Modifier.fillMaxWidth().wrapContentHeight()){
                        UIButton(
                            buttonSize = UIButtonSize.NormalLargeText,
                            modifierTmp = Modifier.weight(1f),
                            text = if(mocList.isEmpty()) "?" else mocList[mocChoiceIndex.value].nameList[TextLanguageInstance] ?: "?",
                            onClick = {
                                isDropDownOpen.value = !isDropDownOpen.value
                            }
                        )

                        UIButton(
                            buttonSize = UIButtonSize.SmallChoice,
                            icon = Res.drawable.ic_moc_buff_icon,
                            onClick = {
                                isDropDownOpen.value = !isDropDownOpen.value
                            }
                        )

                    }

                    //對於DropdownItem沒法按照設計稿展示，暫時無解
                    DropdownMenuNoPadding(
                        expanded = isDropDownOpen.value,
                        onDismissRequest = { isDropDownOpen.value = false },
                        modifier = Modifier
                            .background(Color(0xFFDDDDDD))
                            .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
                    ) {
                        mocList.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                onClick = {
                                    mocChoiceIndex.value = index
                                    isDropDownOpen.value = false
                                    //optionAction(optionIndex.value)
                                },
                                modifier = Modifier.background(if(mocChoiceIndex.value == index) Color(0x0F000000) else Color(0x00000000))
                            ) {
                                Row{
                                    Text(
                                        text = option.nameList[TextLanguageInstance] ?: "?",
                                        style = FontSizeNormal14(),
                                        color = Color(0xFF222222),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Image(
                                        painterResource(if (mocChoiceIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }


        PageHeaderAlpha(
            navigator = navigator,
            hazeState = hazeState,
        ){
            TitleHeader(headerData.titleIconId,headerData.title,headerData.titleRId)
        }
    }
}

@Composable
fun MemoryOfChaosEnemyList(
    modifier: Modifier = Modifier,
) {
    var density = LocalDensity.current.density
    val hazeState = remember { HazeState() }

    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            item {


            }
        }
    }
}