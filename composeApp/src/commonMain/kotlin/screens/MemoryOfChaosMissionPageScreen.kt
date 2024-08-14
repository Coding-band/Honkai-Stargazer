package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import components.DropdownMenuNoPadding
import components.HeaderData
import components.InfoDisplayDialog
import components.PAGE_HEADER_HEIGHT
import components.PageHeaderAlpha
import components.TitleHeader
import components.UIButton
import components.UIButtonSize
import components.defaultHeaderData
import components.horizontalFadingEdge
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.MOCEffect
import files.Res
import files.bg_transparent
import files.ic_arrow_down_spinner
import files.ic_arrow_to_down
import files.ic_exchange_icon
import files.ic_moc_buff_icon
import files.ic_person_btn
import files.ic_selected_orange_circle
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Constants
import types.MemoryOfChaos
import types.MemoryOfChaosList
import types.MemoryOfChaosMonsterInfo
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.Language.Companion.TextLanguageInstance
import utils.UtilTools

@Composable
@Preview
fun MemoryOfChaosMissionPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {
    val hazeState = remember { HazeState() }

    val mocChoiceIndex = remember { mutableStateOf(0) }
    val mocList = MemoryOfChaosList.getMocList().sortedByDescending { it.id }
    val isDialogVisible = remember { mutableStateOf(false) }
    val mocInfoList = MemoryOfChaos.getMocItemByMocId(mocList[mocChoiceIndex.value].id)


    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING).haze(hazeState)
        ) {
            item { Spacer(Modifier.height(PAGE_HEADER_HEIGHT + 24.dp)) }

            //Spinner of MOC
            item { MemoryOfChaosIdSpinner(mocList, mocChoiceIndex, isDialogVisible) }

            item { Spacer(Modifier.height(8.dp)) }

            //Mission Info / Usages
            item { MemoryOfChaosContent(mocInfoList) }

            //Comments & Suggestions
        }

        PageHeaderAlpha(
            navigator = navigator,
            hazeState = hazeState,
            onForward = { },
            forwardIconId = Res.drawable.ic_person_btn
        ){
            TitleHeader(headerData.titleIconId,headerData.title,headerData.titleRId)
        }


        val richTextState = rememberRichTextState()
        richTextState.setHtml(mocInfoList?.descList?.get(TextLanguageInstance) ?: "?")

        InfoDisplayDialog(
            modifier = Modifier.align(Alignment.Center),
            titleString = stringResource(Res.string.MOCEffect),
            hazeState = hazeState,
            isDialogVisible = isDialogVisible,
            components = {
                RichText(state = richTextState,
                    style = FontSizeNormal14(),
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@Composable
fun MemoryOfChaosIdSpinner(
    mocList: List<MemoryOfChaosList>,
    mocChoiceIndex: MutableState<Int>,
    isDialogVisible: MutableState<Boolean>
){
    val density = LocalDensity.current.density

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
                    onClick = { isDropDownOpen.value = !isDropDownOpen.value },
                    icon = Res.drawable.ic_arrow_down_spinner
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
            onClick = { isDialogVisible.value = !isDialogVisible.value }
        )
    }
}

@Preview
@Composable
fun MemoryOfChaosContent(
    mocInfoList: MemoryOfChaos?
){
    val density = LocalDensity.current.density
    val mocPhaseList = UtilTools().getMocPhaseStrListByMocLen(mocInfoList?.missionList?.size ?: -1)
    val usageList = listOf("關卡資訊", "角色使用率", "隊伍使用率", )
    val mocInfoDisplayIndex = remember { mutableStateOf(0) }
    val mocPhaseIndex = remember { mutableStateOf(0) }

    Box(
        Modifier.background(Brush.linearGradient(listOf(Color(0xFF000000), Color(0x00000000))))
            .border(1.dp, Color(0x66DDDDDD), shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column (Modifier.padding(12.dp)) {
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

            Spacer(Modifier.height(8.dp))

            repeat(2){phase ->
                //Showing Floor & Phase
                val phaseInfo = when(phase){
                    0 -> mocInfoList?.missionList?.get(mocPhaseIndex.value)?.phase1
                    1 -> mocInfoList?.missionList?.get(mocPhaseIndex.value)?.phase2
                    else -> null
                }

                if(phaseInfo == null) return@repeat

                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(24.dp))
                    Column(Modifier.requiredWidth(40.dp).align(Alignment.CenterVertically).wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${mocPhaseIndex.value+1}-${phase+1}", style = FontSizeNormal16(), color = Color.White)
                        Spacer(Modifier.height(4.dp))
                        //Weakness Combat Type of Phase
                        Row {
                            repeat(phaseInfo.weaknessList.size) {
                                Image(painterResource(phaseInfo.weaknessList[it].iconColor), modifier = Modifier.size(16.dp) ,contentDescription = null)
                            }
                        }
                    }
                    Spacer(Modifier.width(24.dp))

                    //Monster Info
                    Column(Modifier.weight(1f).fillMaxWidth().wrapContentHeight()) {
                        repeat(2){
                            val monsterInfo = when(it){
                                0 -> phaseInfo.monsterWaveInfo1
                                1 -> phaseInfo.monsterWaveInfo2
                                else -> null
                            }

                            if(monsterInfo.isNullOrEmpty()) return

                            Row {
                                MonsterCard(monsterInfo[0])
                                Spacer(Modifier.width(8.dp))

                                val scrollState = rememberScrollState()
                                Row(Modifier.horizontalScroll(scrollState).horizontalFadingEdge(scrollState, 16.dp, Color.Black), verticalAlignment = Alignment.CenterVertically) {
                                    monsterInfo.forEachIndexed { index, monster ->
                                        if(index == 0) return@forEachIndexed
                                        Spacer(Modifier.width(8.dp))
                                        MonsterCard(monster)
                                        Spacer(Modifier.width(8.dp))
                                    }
                                    monsterInfo.forEachIndexed { index, monster ->
                                        if(index == 0) return@forEachIndexed
                                        Spacer(Modifier.width(8.dp))
                                        MonsterCard(monster)
                                        Spacer(Modifier.width(8.dp))
                                    }
                                }
                            }

                            if(it == 0){
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }

                if (phase == 0){
                    Spacer(Modifier.height(8.dp))
                    //Divider
                    Box(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp), contentAlignment = Alignment.Center) {
                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0x66F3F9FF)))
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }


            Column {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (mocInfoDisplayIndex.value == 0) {
                        val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd") }

                        "${
                            dateFormat.format(
                                Instant.fromEpochMilliseconds(mocInfoList?.timeInfo?.begin ?: 0L).toLocalDateTime(TimeZone.currentSystemDefault())
                            )
                        } - ${
                            dateFormat.format(
                                Instant.fromEpochMilliseconds(mocInfoList?.timeInfo?.end ?: 0L).toLocalDateTime(TimeZone.currentSystemDefault())
                            )
                        } (${TimeZone.currentSystemDefault().id})"
                    } else {
                        "角色使用率"
                    },
                    textAlign = TextAlign.Center,
                    style = FontSizeNormal16(),
                    color = Color(0xFFDDDDDD),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MonsterCard(monsterInfo: MemoryOfChaosMonsterInfo){
    val context = LocalPlatformContext.current
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(48.dp).background(Brush.verticalGradient(Constants.getCardBgColorByRare(1)), shape = RoundedCornerShape(4.dp)).wrapContentHeight(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = UtilTools().newImageRequest(
                    data = UtilTools().getAssetsWebpByteArrayByFileName(
                        UtilTools.ImageFolderType.MONSTER_ICON,
                        "monster_${UtilTools().getImageNameByRegistName(monsterInfo.registName)}"
                    ),
                    context = context,
                ),
                imageLoader = UtilTools().newImageLoader(context),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(Modifier.height(3.dp))
        Row{
            repeat(monsterInfo.monsterWeakness.size) {
                Image(painterResource(monsterInfo.monsterWeakness[it].iconColor), modifier = Modifier.size(16.dp) ,contentDescription = null)
            }
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