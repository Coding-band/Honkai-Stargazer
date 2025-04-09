package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.voc.stargazer3.BuildKonfig
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.AbyssCharacterUsage
import files.AbyssTeamUsage
import files.MOCEffect
import files.MOCMissionInfoTitle
import files.Res
import files.bg_transparent
import files.ic_arrow_down_spinner
import files.ic_exchange_icon
import files.ic_moc_buff_icon
import files.ic_person_btn
import files.ic_selected_orange_circle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.AbyssCharUsageContent
import types.AbyssInfo
import types.AbyssInfoList
import types.AbyssInfoTimeContent
import types.AbyssInfoType
import types.AbyssInfoUsage
import types.AbyssMonsterInfoContent
import types.AbyssTeamUsageContent
import types.Character
import types.ImageFolder
import types.UserAccount
import ui.components.DropdownMenuNoPadding
import ui.components.InfoDisplayDialog
import ui.components.MonsterCard
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeaderAlpha
import ui.components.TitleHeader
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.components.horizontalFadingEdge
import ui.navigation.BattleChronicleRoute
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.Constants
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Language.Companion.TextLanguageInstance
import utils.app.formatDecimal
import utils.app.getMocPhaseStrListByMocLen
import utils.app.newImageRequest
import utils.app.pxToDp
import utils.app.rememberMutableStateListJsonOf
import utils.app.removeStrQuote
import utils.starbase.StarbaseAPI

lateinit var asList : MutableState<ArrayList<AbyssInfoList>>

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun initASList(){
    asList = rememberSaveable(stateSaver = AbyssInfoList.ListSaver) { mutableStateOf(arrayListOf()) }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun refreshASList(){
    asList.value = runBlocking {
        val job = async(Dispatchers.Default) {
            return@async AbyssInfoList.getAbyssList(type = AbyssInfoType.ApocalypticShadow)
                .sortedByDescending { it.id }
                .filter { (BuildKonfig.appProfile != "DEV") && it.time.begin <= Clock.System.now().toEpochMilliseconds() } as ArrayList<AbyssInfoList>
        }
        job.await()
        job.getCompleted()
    }
}

@Composable
@Preview
fun ApocalypticShadowMissionPageScreen(
    navigator: NavHostController,
    hazeState: HazeState
) {
    val asChoiceIndex = remember { mutableStateOf(0) }
    val isDialogVisible = remember { mutableStateOf(false) }
    val asInfoList = AbyssInfo.getAbyssItemById(abyssId = asList.value[asChoiceIndex.value].id, type = AbyssInfoType.ApocalypticShadow, abyssFileName = asList.value[asChoiceIndex.value].fileName)
    val asCharUsageList = rememberMutableStateListJsonOf<AbyssInfoUsage>()
    val asTeamUsageList = rememberMutableStateListJsonOf<AbyssInfoUsage>()

    val asInfoDisplayIndex = remember { mutableStateOf(0) } //0: Mission Info, 1: Char Usage, 2: Team Usage
    val asFloorIndex = remember { mutableStateOf(0) } //0: Phase 1, 1: Phase 2
    val asUsageUpdateMS = remember { mutableStateOf(0L) }

    LaunchedEffect(asChoiceIndex.value, asFloorIndex.value) {
        withContext(Dispatchers.IO) {
            asCharUsageList.clear()
            asTeamUsageList.clear()

            val charUsage = StarbaseAPI().getAbyssCharUsage(
                abyssId = asList.value[asChoiceIndex.value].id,
                floor = asFloorIndex.value+1,
                abyssInfoType = AbyssInfoType.ApocalypticShadow,
            )

            val teamUsage = StarbaseAPI().getAbyssTeamUsage(
                abyssId = asList.value[asChoiceIndex.value].id,
                floor = asFloorIndex.value+1,
                abyssInfoType = AbyssInfoType.ApocalypticShadow,
            )

            withContext(Dispatchers.Main) {
                asCharUsageList.addAll(charUsage)
                asTeamUsageList.addAll(teamUsage)
                asUsageUpdateMS.value = Clock.System.now().toEpochMilliseconds()
            }
        }
    }

    val isDisplayPageHeader = remember { mutableStateOf(true) }
    val listState = remember { LazyListState() }
    //Check if it can scroll up (forward), then hide the header
    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        isDisplayPageHeader.value = ((listState.firstVisibleItemIndex == 0) && (listState.firstVisibleItemScrollOffset <= 0))
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING).hazeSource(hazeState, zIndex = DefaultZIndex)
        ) {
            item { Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(PAGE_HEADER_HEIGHT)
            ) }

            //Spinner of AS
            item { ApocalypticShadowIdSpinner(asList.value, asChoiceIndex, isDialogVisible) }

            item { Spacer(Modifier.height(8.dp)) }

            //Mission Info / Usages
            item { ApocalypticShadowContent(asInfoList, asCharUsageList, asTeamUsageList, asInfoDisplayIndex, asFloorIndex, asUsageUpdateMS) }

            //Comments & Suggestions
        }

        AnimatedVisibility(
            isDisplayPageHeader.value,
            enter = fadeIn(),
            exit = fadeOut()
        ){
            PageHeaderAlpha(
                navigator = navigator,
                hazeState = hazeState,
                onForward = { navigator.navigateLimited(BattleChronicleRoute(
                    UserAccount.INSTANCE.uid,
                    AbyssInfoType.ApocalypticShadow.name
                )) },
                forwardIconId = Res.drawable.ic_person_btn
            ){
                val headerData = Screen.ApocalypticShadowMissionPageScreen.headerData
                TitleHeader(headerData.titleIconId,headerData.title,headerData.titleRId)
            }
        }


        val richTextState = rememberRichTextState()
        richTextState.setHtml(asInfoList?.descList?.get(TextLanguageInstance) ?: "?")

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
fun ApocalypticShadowIdSpinner(
    asList: List<AbyssInfoList>,
    asChoiceIndex: MutableState<Int>,
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
                .clip(RoundedCornerShape(23.dp))
                .clickable { isDropDownOpen.value = !isDropDownOpen.value }
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .onSizeChanged { optionTextViewSize.value = it },
            ){
                UIButton(
                    buttonSize = UIButtonSize.NormalTextLeft,
                    text = asList[asChoiceIndex.value].nameList[TextLanguageInstance] ?: "?",
                    isAvailable = true,
                    onClick = { isDropDownOpen.value = !isDropDownOpen.value },
                    icon = Res.drawable.ic_arrow_down_spinner
                )
            }
            //對於DropdownItem沒法按照設計稿展示，暫時無解
            DropdownMenuNoPadding(
                expanded = isDropDownOpen.value,
                onDismissRequest = { isDropDownOpen.value = false },
                modifier = Modifier
                    .background(Color(0xFFDDDDDD))
                    .width(pxToDp(optionTextViewSize.value.width, density)),
            ) {
                asList.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        onClick = {
                            asChoiceIndex.value = index
                            isDropDownOpen.value = false
                            //optionAction(schoolIndex.value)
                        },
                        modifier = Modifier.background(if(asChoiceIndex.value == index)
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
                                painterResource(if (asChoiceIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
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
fun ApocalypticShadowContent(
    asInfoList: AbyssInfo?,
    asCharUsageList: SnapshotStateList<AbyssInfoUsage>,
    asTeamUsageList: SnapshotStateList<AbyssInfoUsage>,
    asInfoDisplayIndex: MutableState<Int>,
    asFloorIndex: MutableState<Int>,
    asUsageUpdateMS: MutableState<Long>
){
    val density = LocalDensity.current.density
    val asPhaseList = getMocPhaseStrListByMocLen(asInfoList?.missionList?.size ?: -1)

    val usageTextList = listOf(
        removeStrQuote(Res.string.MOCMissionInfoTitle),
        removeStrQuote(Res.string.AbyssCharacterUsage),
        removeStrQuote(Res.string.AbyssTeamUsage)
    )

    if(asPhaseList.isEmpty() || asInfoList == null) return

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
                        indication = ripple(radius = 4.dp),
                        onClick = {
                            asInfoDisplayIndex.value = (asInfoDisplayIndex.value + 1) % usageTextList.size
                        }
                    )
                ) {
                    Text(usageTextList[asInfoDisplayIndex.value], style = FontSizeNormal16(), color = Color.White)
                    Spacer(Modifier.width(4.dp))
                    Image(painterResource(Res.drawable.ic_exchange_icon), modifier = Modifier.size(12.dp).align(Alignment.CenterVertically), colorFilter = ColorFilter.tint(Color.White), contentDescription = null)
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
                        Text(asPhaseList[asFloorIndex.value], style = FontSizeNormal16(), color = Color.White)
                        Spacer(Modifier.width(4.dp))
                        Image(painterResource(Res.drawable.ic_arrow_down_spinner), modifier = Modifier.size(12.dp).align(Alignment.CenterVertically), colorFilter = ColorFilter.tint(Color.White), contentDescription = null)
                    }
                    //對於DropdownItem沒法按照設計稿展示，暫時無解
                    DropdownMenuNoPadding(
                        expanded = isDropDownOpen.value,
                        onDismissRequest = { isDropDownOpen.value = false },
                        modifier = Modifier
                            .background(Color(0xFF3E3E47))
                            .width(pxToDp(optionTextViewSize.value.width, density)),
                    ) {
                        asPhaseList.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                contentPadding = PaddingValues(
                                    vertical = 4.dp,
                                    horizontal = 0.dp
                                ),
                                onClick = {
                                    asFloorIndex.value = index
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

            //Only for Char Usage & Mission Info

            repeat(2){phase ->
                //Showing Floor & Phase
                val phaseInfo = when(phase){
                    0 -> asInfoList.missionList[asFloorIndex.value].part1
                    1 -> asInfoList.missionList[asFloorIndex.value].part2
                    else -> null
                }

                if(phaseInfo == null) return@repeat

                // Summary of Weakness Combat Type in This Phase
                Row(Modifier.fillMaxWidth()) {

                    //Phase & Weakness Combat Type, dont show in Team Usage
                    if(asInfoDisplayIndex.value < 2) {
                        Spacer(Modifier.width(24.dp))
                        Column(Modifier.requiredWidth(40.dp).align(Alignment.CenterVertically).wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${asFloorIndex.value+1}-${phase+1}", style = FontSizeNormal16(), color = Color.White)
                            Spacer(Modifier.height(4.dp))
                            //Weakness Combat Type of Phase
                            FlowRow(modifier = Modifier.wrapContentSize(), maxItemsInEachRow = 2)  {
                                repeat(phaseInfo.weaknessList.size) {
                                    Image(painterResource(phaseInfo.weaknessList[it].iconColor), modifier = Modifier.size(16.dp) ,contentDescription = null)
                                }
                            }
                        }
                        Spacer(Modifier.width(24.dp))
                    }

                    when (asInfoDisplayIndex.value) {
                        0 -> AbyssMonsterInfoContent(modifier = Modifier.weight(1f), phaseInfo = phaseInfo)
                        1 -> AbyssCharUsageContent(charUsageList = asCharUsageList, phase = phase)
                        2 -> AbyssTeamUsageContent(teamUsageList = asTeamUsageList, infoList = asInfoList, phase = phase)
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

            AbyssInfoTimeContent(infoList = asInfoList, infoDisplayIndex = asInfoDisplayIndex, infoUsageUpdateMS = asUsageUpdateMS)

        }
    }
}