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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import components.BackIcon
import components.CharacterEidolon
import components.CharacterTraceTree.CharacterTraceTree
import components.HeaderData
import components.InfoAdviceLightcone
import components.InfoAdviceRelic
import components.InfoAdviceTeammate
import components.InfoBasicStatus
import components.InfoBioColumn
import components.InfoDisplayDialog
import components.InfoNavigateItem
import components.InfoNavigatorBar
import components.InfoStory
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.StatusType
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AdviceLightcones
import files.AdviceRelics
import files.AdviceTeams
import files.BasicStatus
import files.CharacterStory
import files.Eidolon
import files.Res
import files.TraceTree
import files.ic_favourite_btn
import files.phorphos_baseball_cap_regular
import files.phorphos_chats_circle_regular
import files.phorphos_info_regular
import files.phorphos_person_fill
import files.phorphos_person_regular
import files.phorphos_star_half_regular
import files.phorphos_sword_regular
import files.phorphos_tree_structure_regular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import types.Character
import types.CombatType
import utils.JsonElementSaver
import utils.Language
import utils.UtilTools
import utils.annotation.DoItLater

private lateinit var localCoroutineScope: CoroutineScope;
private lateinit var localSnackbarHostState: SnackbarHostState;

val charInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 1, Res.string.BasicStatus),
    InfoNavigateItem(Res.drawable.phorphos_tree_structure_regular, 2, Res.string.TraceTree),
    InfoNavigateItem(Res.drawable.phorphos_star_half_regular, 3, Res.string.Eidolon),
    InfoNavigateItem(Res.drawable.phorphos_sword_regular, 4, Res.string.AdviceLightcones),
    InfoNavigateItem(Res.drawable.phorphos_baseball_cap_regular, 5, Res.string.AdviceRelics),
    InfoNavigateItem(Res.drawable.phorphos_person_regular, 6, Res.string.CharacterStory),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 7, Res.string.AdviceTeams),

    )

private const val scrollPxTrigInvisible = 250f

@OptIn(FlowPreview::class)
@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    @DoItLater("Use rememberStatus")
    var density = LocalDensity.current.density
    val characterName = backStackEntry.path<String>("charName")!!.replace("_", " ")
    val characterFileName = backStackEntry.query<String>("fileName")!!
    val characterId = backStackEntry.query<String>("charId")!!
    val combatType = CombatType.valueOf(backStackEntry.query<String>("combatType")!!)
    val path = types.Path.valueOf(backStackEntry.query<String>("path")!!)

    val hazeState = remember { HazeState() }
    val charInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(Character.getCharacterDataFromFileName(characterFileName, Language.TextLanguageInstance) as JsonElement) }

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        charInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
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

    //It will be transfer from CharacterTraceTree.kt !
    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }
    val selectedSectIndex = remember { mutableStateOf(0) } //流派

    val singleCharWeightJsonElement = UtilTools.TemporaryFunction().getCharWeightListJson().jsonObject[characterId]
    var charWeightJsonObject : JsonObject? = null

    if(singleCharWeightJsonElement != null && singleCharWeightJsonElement.jsonArray.size > 0){
        charWeightJsonObject = singleCharWeightJsonElement.jsonArray[selectedSectIndex.value].jsonObject
    }

    Box {

        CharacterInfoFullImgWithRare(
            fileName = characterName,
            isVisible = !isNaviBarVisible //alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = listState, modifier = Modifier.haze(hazeState).align(Alignment.Center).navigationBarsPadding()) {
            item { InfoBioColumn(charInfoJson, combatType, path, isUserOwned = false, isFullEidolon = false) }
            //Don't forget to add "StatusBarPadding" !
            item { InfoBasicStatus(charInfoJson, StatusType.CHARACTER) }
            item { CharacterTraceTree(charInfoJson, path, characterName, dialogTitle, dialogDisplay,dialogLastTrigType,  dialogComponent) }
            item { CharacterEidolon(charInfoJson, characterName, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoAdviceLightcone(charWeightJsonObject) }
            item { InfoAdviceRelic(charWeightJsonObject) }
            item { InfoAdviceTeammate(charWeightJsonObject, characterId, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoStory(charInfoJson) }
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
                InfoNavigatorBar(charInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeState, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
            }
        }
    }
}

@Composable
fun CharacterInfoFullImgWithRare(
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
            /*

            AsyncImage(
                model = UtilTools().newImageRequest(context = LocalPlatformContext.current, data = Character.getCharacterImageByteArrayFromFileName(
                    UtilTools.ImageFolderType.CHAR_FULL, fileName
                ), crossFade = false),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
                imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
            )
             */
            Image(
                bitmap = Character.getCharacterImageFromFileName(
                    UtilTools.ImageFolderType.CHAR_FULL, fileName
                ),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
            )

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
