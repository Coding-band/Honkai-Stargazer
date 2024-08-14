package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import com.voc.honkai_stargazer.component.RelicCard
import components.BackIcon
import components.HeaderData
import components.InfoDisplayDialog
import components.InfoNavigateItem
import components.InfoNavigatorBar
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.TitleHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.RelicDetail
import files.RelicStatus2Pcs
import files.RelicStatus4Pcs
import files.Res
import files.ic_favourite_btn
import files.phorphos_chats_circle_regular
import files.phorphos_dice_four_regular
import files.phorphos_dice_two_regular
import files.phorphos_person_fill
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.Constants
import types.Relic
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.JsonElementSaver
import utils.Language
import utils.TextColorNormalDim
import utils.UtilTools

private lateinit var localCoroutineScope: CoroutineScope;
private lateinit var localSnackbarHostState: SnackbarHostState;

val relicInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_dice_two_regular, 1, Res.string.RelicStatus2Pcs),
    InfoNavigateItem(Res.drawable.phorphos_dice_four_regular, 2, Res.string.RelicStatus4Pcs),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 3, Res.string.RelicDetail),
)

private const val scrollPxTrigInvisible = 250f

@OptIn(FlowPreview::class)
@Composable
fun RelicInfoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    var density = LocalDensity.current.density
    val relicName = backStackEntry.path<String>("relicName")!!.replace("_", " ")
    val relicFileName = backStackEntry.query<String>("fileName")!!

    val hazeState = remember { HazeState() }
    val relicInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(Relic.getRelicDataFromJSON(relicFileName, Language.TextLanguageInstance) as JsonElement) }

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        relicInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
        titleIconId = Res.drawable.phorphos_person_fill
    )

    val listState = rememberLazyListState()

    var isNaviBarVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollBackward }
            .distinctUntilChanged()
            .collect {
                isNaviBarVisible = it
            }
    }

    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }

    Box {

        val isRelic = relicFileName.toInt() < 300
        RelicInfoFullImgWithRare(
            fileName = relicName,
            isVisible = !isNaviBarVisible, //alpha = scrollToAlpha
            isRelic = isRelic
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.haze(hazeState).align(Alignment.Center).navigationBarsPadding()) {
            item { RelicBasicInfo(relicInfoJson) }
            item { RelicSetInfo(relicInfoJson, false) }
            //Don't forget to add "StatusBarPadding" !
            item { if(isRelic) RelicSetInfo(relicInfoJson, isRelic) }
            item { RelicSetsCardDisplay(relicName, relicInfoJson, isRelic) }
            item { Box(modifier = Modifier.height(72.dp)) }
        }

        PageHeader(
            navigator = navigator,
            headerData = headerDataPage,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.ic_favourite_btn,
            onForward = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if(dialogDisplay.value){
                InfoDisplayDialog(dialogTitle.value, dialogComponent.value, modifier = Modifier.align(Alignment.BottomCenter), hazeState, isNavBarVisible = (isNaviBarVisible), isDialogVisible = (dialogDisplay))
            } else {
                InfoNavigatorBar(relicInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeState, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
            }
        }
    }
}

@Composable
fun RelicBasicInfo(infoJson: JsonElement){

    var columnHeightDp by remember { mutableStateOf(110.dp) }
    val density = LocalDensity.current.density
    val itemName = remember { infoJson.jsonObject["name"]!!.jsonPrimitive.content }
    val itemRarity = remember { infoJson.jsonObject["rarity"]!!.jsonPrimitive.int }

    Column {
        Box(modifier = Modifier.height(getScreenSizeInfo().hDP - columnHeightDp))

        Column(modifier = Modifier.padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
            .onSizeChanged { item ->
                columnHeightDp = UtilTools().pxToDp(item.height, density)
            }) {
            Row() {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = itemName,
                    style = FontSizeNormal14(),
                    fontSize = 32.sp,
                    color = Color.White,
                )
            }

            Box(Modifier.height(8.dp))

            Row {
                repeat(itemRarity) {
                    Image(
                        modifier = Modifier.size(24.dp,28.dp),
                        painter = painterResource(Res.drawable.ui_icon_star),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = "Stars to represent Rarity"
                    )
                }
            }
        }
    }
}

@Composable
fun RelicSetInfo(infoJson: JsonElement, isShowing4Set: Boolean){
    val skill = infoJson.jsonObject["skills"]!!.jsonArray
    val richTextState : RichTextState = rememberRichTextState()
    var descHTML = ""
    val paramList : ArrayList<Float> = arrayListOf()
    descHTML = if(isShowing4Set && skill.size > 1){
        for(param in skill[1].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        UtilTools().htmlDescApplier(skill[1].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }else{
        for(param in skill[0].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        UtilTools().htmlDescApplier(skill[0].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)){
        if (isShowing4Set){
            TitleHeader(iconRId = Res.drawable.phorphos_dice_four_regular, titleRId = Res.string.RelicStatus4Pcs)
        }else{
            TitleHeader(iconRId = Res.drawable.phorphos_dice_two_regular, titleRId = Res.string.RelicStatus2Pcs)
        }

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
            RichText(
                state = richTextState.setHtml(descHTML),
                color = Color.White,
                style = FontSizeNormal14(),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }


}
@Composable
fun RelicInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    fileName: String,
    isRelic: Boolean = false,
    isVisible: Boolean = true
) {
    //val iconSize = (getScreenSizeInfo().wDP - 36.dp - 32.dp)/2;
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.Center)
        ) {
            Column(modifier = Modifier.width(getScreenSizeInfo().wDP - 36.dp).aspectRatio(1f).sizeIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)) {
                Row{
                    Image(
                        bitmap = Relic.getRelicImageFromJSON(if(isRelic) UtilTools.ImageFolderType.RELIC_ICON else UtilTools.ImageFolderType.ORMANENT_ICON, fileName, if(isRelic) 1 else 5),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        bitmap = Relic.getRelicImageFromJSON(if(isRelic) UtilTools.ImageFolderType.RELIC_ICON else UtilTools.ImageFolderType.ORMANENT_ICON, fileName, if(isRelic) 2 else 6),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )
                }
                Row {
                    if(isRelic){
                        Image(
                            bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, 3),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, 4),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000))
                )
            ).align(Alignment.BottomCenter),
        )

    }
}



@Composable
fun RelicSetsCardDisplay(
    relicSetName: String,
    relicJson: JsonElement,
    isRelic: Boolean = true,
) {
    Column(
        modifier = Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).fillMaxWidth().statusBarsPadding().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
    ) {
        TitleHeader(
            iconRId = Res.drawable.phorphos_chats_circle_regular, titleRId = Res.string.RelicDetail
        )

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in if (isRelic) { 1..4 } else { 5..6 }) {
                item{
                    Box(
                        modifier = Modifier.size(
                            Constants.RELIC_CARD_WIDTH,
                            Constants.RELIC_CARD_HEIGHT
                        ).clip(
                            RoundedCornerShape(
                                topEnd = 15.dp,
                                topStart = 4.dp,
                                bottomEnd = 4.dp,
                                bottomStart = 4.dp
                            )
                        )
                    ) {
                        Column {
                            Box(
                                modifier = Modifier.defaultMinSize(
                                    Constants.RELIC_CARD_WIDTH, Constants.RELIC_CARD_WIDTH
                                ).clip(
                                    RoundedCornerShape(
                                        topEnd = 15.dp,
                                        topStart = 4.dp,
                                        bottomEnd = 4.dp,
                                        bottomStart = 4.dp
                                    )
                                ).clickable(
                                    onClick = { },
                                    indication = rememberRipple(),
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                            ) {
                                Image(
                                    bitmap = Relic.getRelicImageFromJSON(
                                        if (isRelic) UtilTools.ImageFolderType.RELIC_ICON else UtilTools.ImageFolderType.ORMANENT_ICON,
                                        relicSetName, index
                                    ),
                                    contentDescription = "Relic Icon",
                                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(
                                        Brush.verticalGradient(
                                            colors = Constants.getCardBgColorByRare(relicJson.jsonObject["rarity"]!!.jsonPrimitive.int)
                                        )
                                    ),
                                    contentScale = ContentScale.Crop

                                )
                            }
                            Row(
                                Modifier.fillMaxWidth().wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = relicJson.jsonObject["pieces"]!!.jsonObject[index.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                                    textAlign = TextAlign.Center,
                                    style = FontSizeNormal12(),
                                    color = TextColorNormalDim,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.wrapContentWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RelicCardPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(80.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(count = 4) {
            RelicCard(
                relic = Relic(
                    officialId = 312,
                    registName = "Penacony, Land of the Dreams",
                    fileName = "312",
                ),
            )
        }
    }
}