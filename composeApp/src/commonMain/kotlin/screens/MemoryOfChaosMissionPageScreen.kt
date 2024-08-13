package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
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
import files.ic_arrow_to_down
import files.ic_exchange_icon
import files.ic_moc_buff_icon
import files.ic_selected_orange_circle
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.MemoryOfChaos
import types.MemoryOfChaosList
import utils.FontSizeNormal14
import utils.FontSizeNormal16
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

    val mocChoiceIndex = remember { mutableStateOf(0) }
    val mocInfoDisplayIndex = remember { mutableStateOf(0) }
    val mocPhaseIndex = remember { mutableStateOf(0) }

    val mocList = MemoryOfChaosList.getMocList().sortedByDescending { it.id }
    val usageList = listOf("關卡資訊", "角色使用率", "隊伍使用率", )
    val mocInfoList = MemoryOfChaos.getMocItemByMocId(mocList[mocChoiceIndex.value].id)
    val mocPhaseList = UtilTools().getMocPhaseStrListByMocLen(mocInfoList?.missionList?.size ?: -1)


    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
        ) {
            item { Spacer(Modifier.height(PAGE_HEADER_HEIGHT + 24.dp)) }

            //Spinner of MOC
            item {
                //Spinner / Dropdown
                Row {
                    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
                    val isDropDownOpen = remember { mutableStateOf(false) }
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .defaultMinSize(100.dp, 30.dp)
                            .wrapContentSize()
                            .weight(1f)
                            .clickable { isDropDownOpen.value = !isDropDownOpen.value }
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .onSizeChanged { optionTextViewSize.value = it },
                        ){
                            UIButton(
                                buttonSize = UIButtonSize.NormalTextLeft,
                                text = mocList[mocChoiceIndex.value].nameList[TextLanguageInstance] ?: "?",
                                isAvailable = true,
                                onClick = { isDropDownOpen.value = !isDropDownOpen.value }
                            )
                            Spacer(Modifier.height(8.dp))
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
                                        //optionAction(schoolIndex.value)
                                    },
                                    modifier = Modifier.background(if(mocChoiceIndex.value == index)
                                        //Color(0x0F000000) else Color(0x00000000)
                                        Color(0x0F000000) else Color(0x00000000)
                                    )
                                ) {
                                    Row{
                                        Text(
                                            text = option.nameList[TextLanguageInstance] ?: "?",
                                            style = FontSizeNormal14(),
                                            color = Color.Black,
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

                    Spacer(Modifier.width(8.dp))

                    UIButton(
                        modifierTmp = Modifier.wrapContentHeight(),
                        buttonSize = UIButtonSize.SmallChoice,
                        isAvailable = true,
                        icon = Res.drawable.ic_moc_buff_icon,
                        onClick = {  }
                    )
                }
            }

            item { Spacer(Modifier.height(8.dp)) }

            //Mission Info / Usages
            item {
                Box(
                    Modifier.background(Brush.linearGradient(listOf(Color(0xFF000000), Color(0x00000000))))
                        .border(1.dp, Color(0x66DDDDDD), shape = RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .requiredHeight(400.dp)
                ) {
                    Row(Modifier.padding(12.dp)) {
                        Row {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable(
                                    enabled = true,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(radius = 4.dp),
                                    onClick = {
                                        mocInfoDisplayIndex.value = (mocInfoDisplayIndex.value + 1) % usageList.size
                                    }
                                )
                            ) {
                                Text(usageList[mocInfoDisplayIndex.value], style = FontSizeNormal16(), color = Color.White)
                                Spacer(Modifier.width(4.dp))
                                Image(painterResource(Res.drawable.ic_exchange_icon), modifier = Modifier.size(12.dp), contentDescription = null)
                            }

                            Spacer(Modifier.width(4.dp).weight(1f))


                            val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
                            val isDropDownOpen = remember { mutableStateOf(false) }
                            Box(
                                contentAlignment = Alignment.CenterEnd,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable { isDropDownOpen.value = !isDropDownOpen.value }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .onSizeChanged { optionTextViewSize.value = it },
                                ) {
                                    Spacer(Modifier.width(16.dp))
                                    Text(mocPhaseList[mocPhaseIndex.value], style = FontSizeNormal16(), color = Color.White)
                                    Spacer(Modifier.width(4.dp))
                                    Image(painterResource(Res.drawable.ic_arrow_to_down), modifier = Modifier.size(12.dp), contentDescription = null)
                                }
                                //對於DropdownItem沒法按照設計稿展示，暫時無解
                                DropdownMenuNoPadding(
                                    expanded = isDropDownOpen.value,
                                    onDismissRequest = { isDropDownOpen.value = false },
                                    modifier = Modifier
                                        .background(Color(0xFF3E3E47))
                                        .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
                                ) {
                                    mocPhaseList.forEachIndexed { index, option ->
                                        DropdownMenuItem(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            contentPadding = PaddingValues(
                                                vertical = 4.dp,
                                                horizontal = 0.dp
                                            ),
                                            onClick = {
                                                mocPhaseIndex.value = index
                                                isDropDownOpen.value = false
                                                //optionAction(schoolIndex.value)
                                            }
                                        ) {
                                            Text(
                                                text = option,
                                                style = FontSizeNormal16(),
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
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