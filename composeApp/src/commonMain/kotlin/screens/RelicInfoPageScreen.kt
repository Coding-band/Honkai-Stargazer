package screens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import components.BackIcon
import components.CharacterEidolonBox
import components.HeaderData
import components.InfoAdviceCharacter
import components.InfoBasicStatus
import components.InfoBioColumn
import components.InfoDisplayDialog
import components.InfoLcMetamorphosis
import components.InfoNavigateItem
import components.InfoNavigatorBar
import components.InfoStory
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.StatusType
import components.TitleHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Eidolon
import files.RelicDetail
import files.RelicStatus2Pcs
import files.RelicStatus4Pcs
import files.Res
import files.ic_favourite_btn
import files.phorphos_chats_circle_regular
import files.phorphos_dice_four_regular
import files.phorphos_dice_two_regular
import files.phorphos_person_fill
import files.phorphos_star_half_regular
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Relic
import utils.FontSizeNormal
import utils.FontSizeNormal16
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
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    var density = LocalDensity.current.density
    val relicFileName = backStackEntry!!.arguments?.getString("fileName")!!
    val relicName = backStackEntry.arguments?.getString("relicName")!!.replace("_", " ")
    val path = types.Path.valueOf(backStackEntry.arguments?.getString("path")!!)

    val hazeState = remember { HazeState() }
    val relicInfoJson = Relic.getRelicDataFromJSON(relicFileName, UtilTools.TextLanguage.ZH_HK)

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        relicInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
        titleIconId = Res.drawable.phorphos_person_fill
    )

    val listState = rememberLazyListState()

    val isNaviBarVisible by remember {
        derivedStateOf {
            // whatever logic you need
            listState.canScrollBackward
        }
    }

    //It will be transfer from RelicTraceTree.kt !
    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }

    Box {

        RelicInfoFullImgWithRare(
            fileName = relicName,
            isVisible = !isNaviBarVisible //alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.haze(hazeState).align(Alignment.Center)) {
            item { RelicSetInfo(relicInfoJson, relicFileName.toInt() < 300) }
            //Don't forget to add "StatusBarPadding" !
            item {  }
            item {  }

        }

        PageHeader(
            navController = navController,
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

        Column(modifier = Modifier.padding(start = 18.dp, end = 18.dp)
            .onSizeChanged { item ->
                columnHeightDp = UtilTools().pxToDp(item.height, density)
            }) {
            Row() {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = itemName,
                    style = FontSizeNormal(),
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
    if(isShowing4Set && skill.size > 1){
        for(param in skill[1].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        descHTML = UtilTools().htmlDescApplier(skill[1].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }else{
        for(param in skill[0].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        descHTML = UtilTools().htmlDescApplier(skill[0].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
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
                style = FontSizeNormal(),
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
    val iconSize = (getScreenSizeInfo().wDP - 36.dp - 60.dp)/2;
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).align(Alignment.BottomCenter)
        ) {
            Box(modifier = Modifier.size(getScreenSizeInfo().wDP - 36.dp).sizeIn(200.dp, 450.dp)) {
                Image(
                    bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, if(isRelic) 5 else 1),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize).align(if(isRelic) Alignment.TopStart else Alignment.CenterStart)
                )
                Image(
                    bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, if(isRelic) 6 else 2),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize).align(if(isRelic) Alignment.TopEnd else Alignment.CenterEnd)
                )

                if(isRelic){
                    Image(
                        bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, 3),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize).align(Alignment.BottomStart)
                    )
                    Image(
                        bitmap = Relic.getRelicImageFromJSON(UtilTools.ImageFolderType.RELIC_ICON, fileName, 4),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize).align(Alignment.BottomEnd)
                    )
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
