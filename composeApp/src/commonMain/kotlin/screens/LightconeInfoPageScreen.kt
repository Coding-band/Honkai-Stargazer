package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import components.BackIcon
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
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AdviceCharacters
import files.BasicStatus
import files.LightconeEffect
import files.LightconeStory
import files.Res
import files.bg_lightcone_artwork_back
import files.bg_lightcone_artwork_front
import files.ic_favourite_btn
import files.phorphos_chats_circle_regular
import files.phorphos_info_regular
import files.phorphos_person_fill
import files.phorphos_person_regular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import types.Lightcone
import utils.JsonElementSaver
import utils.Language
import utils.UtilTools

private lateinit var localCoroutineScope: CoroutineScope;
private lateinit var localSnackbarHostState: SnackbarHostState;

val lcInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 1, Res.string.BasicStatus),
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 2, Res.string.LightconeEffect),
    InfoNavigateItem(Res.drawable.phorphos_person_regular, 3, Res.string.AdviceCharacters),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 4, Res.string.LightconeStory),
)

private const val scrollPxTrigInvisible = 250f

@OptIn(FlowPreview::class)
@Composable
fun LightconeInfoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    var density = LocalDensity.current.density
    val lightconeName = backStackEntry.path<String>("lcName")!!.replace("_", " ")
    val lightconeFileName = backStackEntry.query<String>("fileName")!!
    val path = types.Path.valueOf(backStackEntry.query<String>("path")!!)

    val hazeState = remember { HazeState() }
    val lcInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(Lightcone.getLightconeDataFromJSON(lightconeFileName, Language.TextLanguageInstance) as JsonElement) }

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        lcInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
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

    //It will be transfer from LightconeTraceTree.kt !
    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }

    Box {

        LightconeInfoFullImgWithRare(
            fileName = lightconeName,
            isVisible = !isNaviBarVisible //alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.haze(hazeState).align(Alignment.Center).navigationBarsPadding()) {
            item { InfoBioColumn(lcInfoJson, combatType = null, path, isUserOwned = false, isFullEidolon = false) }
            //Don't forget to add "StatusBarPadding" !
            item { InfoBasicStatus(lcInfoJson, StatusType.LIGHTCONE) }
            item { InfoLcMetamorphosis(lcInfoJson) }
            item { InfoAdviceCharacter(lightconeFileName) }
            item { InfoStory(lcInfoJson, isLcStory = true) }
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
                InfoNavigatorBar(lcInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeState, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
            }
        }
    }
}

@Composable
fun LightconeInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    fileName: String,
    isVisible: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).align(Alignment.BottomCenter)
        ) {
            Box(Modifier.fillMaxWidth(0.5f), contentAlignment = Alignment.TopCenter) {
                LightconeInfoFullImageContent(modifier = Modifier.fillMaxWidth(0.85f).rotate(5f).padding(16.dp).align(Alignment.TopCenter), fileName = fileName)
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
fun LightconeInfoFullImageContent(
    modifier: Modifier = Modifier,
    fileName: String,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.bg_lightcone_artwork_back),
            modifier = modifier.offset((12).dp, (12).dp),
            contentDescription = "Lightcone Back Image",
            contentScale = ContentScale.FillBounds,
        )
        Image(
            bitmap = Lightcone.getLightconeImageFromJSON(
                UtilTools.ImageFolderType.LC_ARTWORK, fileName
            ),
            modifier = modifier,
            contentDescription = "Lightcone Full Image",
            contentScale = ContentScale.Fit,
        )
        Image(
            painter = painterResource(Res.drawable.bg_lightcone_artwork_front),
            contentDescription = "Lightcone Front Image",
            modifier = modifier.offset((-12).dp, (-12).dp),
            contentScale = ContentScale.FillBounds,
        )

    }
}
