package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.NoDataYet
import files.RelicDetail
import files.RelicStatus2Pcs
import files.RelicStatus4Pcs
import files.Res
import files.ic_favourite_btn
import files.ic_favourite_btn_selected
import files.phorphos_chats_circle_regular
import files.phorphos_dice_four_regular
import files.phorphos_dice_two_regular
import files.phorphos_person_fill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.ImageFolder
import types.Relic
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.InfoBioColumn
import ui.components.InfoDisplayDialog
import ui.components.InfoNavigateItem
import ui.components.InfoNavigatorBar
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.TitleHeader
import ui.navigation.RelicInfoRoute
import utils.app.Constants
import utils.app.Constants.Companion.RELIC_CARD_WIDTH
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.Language
import utils.app.Preferences
import utils.app.SG3VerticalScrollbar
import utils.app.TextColorNormalDim
import utils.app.htmlDescApplier
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.showWarningToast

val relicInfoNavItemList = arrayOf(
    InfoNavigateItem(Res.drawable.phorphos_dice_two_regular, 1, Res.string.RelicStatus2Pcs),
    InfoNavigateItem(Res.drawable.phorphos_dice_four_regular, 2, Res.string.RelicStatus4Pcs),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 3, Res.string.RelicDetail),
)

// Small page data holder for async loading
private data class RelicInfoPageData(
    val relicInfoJson: JsonElement,
)

@OptIn(FlowPreview::class)
@Composable
fun RelicInfoPage(
    navigator: NavHostController,
    hazeState: HazeState,
    backStackEntry: NavBackStackEntry,
    pageHeader: MutableState<@Composable () -> Unit>
) {
    var density = LocalDensity.current.density
    val route = backStackEntry.toRoute<RelicInfoRoute>()
    val relicName = route.relicName
    val relicFileName = route.fileName

    // Async load state: avoid blocking main thread for file I/O / JSON parsing
    val pageData = remember { mutableStateOf<RelicInfoPageData?>(null) }
    val isLoadError = remember { mutableStateOf(false) }

    LaunchedEffect(relicFileName) {
        val data = withContext(Dispatchers.Default) {
            try {
                Relic.getRelicDataFromJSON(relicFileName, Language.TextLanguageInstance) as JsonElement
            } catch (_: Exception) {
                null
            }
        }

        if (data == null || (data is JsonObject && data.jsonObject.isEmpty())) {
            isLoadError.value = true
        } else {
            pageData.value = RelicInfoPageData(data)
        }
    }

    // Error handling: show toast and navigate back
    if (isLoadError.value) {
        showWarningToast(message = removeStrQuote(Res.string.NoDataYet))
        navigator.popBackStack()
        return
    }

    // 提取已加載數據（可能為 null = 尚在加載中）
    val loadedData = pageData.value
    val relicInfoJson = loadedData?.relicInfoJson
    val isDataReady = loadedData != null
    val isRelic = try { relicFileName.toInt() < 300 } catch (_: Exception) { true }

    // Header：加載完成前用遺器名，加載完成後用 JSON 內的名字
    val headerDataPage = if (relicInfoJson != null) {
        HeaderData(
            relicInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
            titleIconId = Res.drawable.phorphos_person_fill
        )
    } else {
        HeaderData(
            title = relicName,
            titleIconId = Res.drawable.phorphos_person_fill
        )
    }

    val listState = rememberLazyListState()

    // 當數據加載完成後，滾動到頂部
    LaunchedEffect(isDataReady) {
        if (isDataReady) {
            listState.scrollToItem(0)
        }
    }

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

    pageHeader.value = {
        val isFavourite = remember { mutableStateOf(Preferences.FavouriteClass.checkIsFavourite(relicFileName, Preferences.FavouriteClass.TYPE.RELIC)) }
        PageHeader(
            navigator = navigator,
            headerData = headerDataPage,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = if(isFavourite.value) Res.drawable.ic_favourite_btn_selected else Res.drawable.ic_favourite_btn,
            onForward = {
                if(!isFavourite.value) {
                    Preferences.FavouriteClass.addToFavouriteList(relicFileName, Preferences.FavouriteClass.TYPE.RELIC)
                } else {
                    Preferences.FavouriteClass.removeFromFavouriteList(relicFileName, Preferences.FavouriteClass.TYPE.RELIC)
                }

                isFavourite.value = !isFavourite.value
            },
            listState = listState
        )
    }

    BoxWithConstraints {
        val pageSize = Pair(maxWidth, maxHeight)

        RelicInfoFullImgWithRare(
            fileName = relicName,
            isVisible = !isNaviBarVisible, //alpha = scrollToAlpha
            isRelic = isRelic
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.hazeSource(hazeState).align(Alignment.Center)) {
            if (isDataReady && relicInfoJson != null) {
                item(key = "InfoBioColumn") { InfoBioColumn(relicInfoJson, pageSize = pageSize, isUserOwned = false) }
                item(key = "RelicSetInfo2Pcs") { RelicSetInfo(relicInfoJson, false) }
                if (isRelic) {
                    item(key = "RelicSetInfo4Pcs") { RelicSetInfo(relicInfoJson, true) }
                }
                item(key = "RelicSetsCardDisplay") { RelicSetsCardDisplay(relicName, relicInfoJson, isRelic) }
            }
            item(key = "PaddingABox") { Box(modifier = Modifier.navigationBarsPadding().height(72.dp)) }
        }

        SG3VerticalScrollbar(listState = listState)

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
fun RelicSetInfo(infoJson: JsonElement, isShowing4Set: Boolean){
    val skill = infoJson.jsonObject["skills"]!!.jsonArray
    val richTextState : RichTextState = rememberRichTextState()
    var descHTML = ""
    val paramList : ArrayList<Float> = arrayListOf()
    descHTML = if(isShowing4Set && skill.size > 1){
        for(param in skill[1].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        htmlDescApplier(skill[1].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }else{
        for(param in skill[0].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        htmlDescApplier(skill[0].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)){
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
            modifier = Modifier.widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).wrapContentHeight().align(Alignment.Center)
        ) {
            Column(modifier = Modifier
                .wrapContentHeight()
                .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
                .align(Alignment.Center)
            ) {
                Row{
                    AsyncImage(
                        model = newImageRequest(context = LocalPlatformContext.current, Relic.getRelicImageFromJSON(if(isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON, fileName, if(isRelic) 1 else 5)),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = newImageRequest(context = LocalPlatformContext.current, Relic.getRelicImageFromJSON(if(isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON, fileName, if(isRelic) 2 else 6)),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )
                }
                Row {
                    if(isRelic){
                        AsyncImage(
                            model = newImageRequest(context = LocalPlatformContext.current, Relic.getRelicImageFromJSON(ImageFolder.RELIC_ICON, fileName, 3)),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = newImageRequest(context = LocalPlatformContext.current, Relic.getRelicImageFromJSON(ImageFolder.RELIC_ICON, fileName, 4)),
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
    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)) {
        Column(
            modifier = Modifier.wrapContentWidth().statusBarsPadding().align(Alignment.Center)
        ) {
            TitleHeader(
                iconRId = Res.drawable.phorphos_chats_circle_regular, titleRId = Res.string.RelicDetail
            )

            //Empty Blank
            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.fillMaxWidth().widthIn(RELIC_CARD_WIDTH / 2, RELIC_CARD_WIDTH).wrapContentHeight()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center).wrapContentSize()
                ) {
                    for (index in if (isRelic) { 1..4 } else { 5..6 }) {
                            Box(
                                modifier = Modifier.widthIn(RELIC_CARD_WIDTH, RELIC_CARD_WIDTH *1.5f).wrapContentHeight()
                                    .clip(
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
                                            indication = ripple(),
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                    ) {
                                        AsyncImage(
                                            model = newImageRequest(
                                                context = LocalPlatformContext.current,
                                                Relic.getRelicImageFromJSON(
                                                    if (isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON,
                                                    relicSetName, index
                                                )
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
                                            modifier = Modifier.widthIn(RELIC_CARD_WIDTH, RELIC_CARD_WIDTH *2).wrapContentHeight()
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
}