package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeSource
import files.AdviceLightcones
import files.AdviceRelics
import files.AdviceTeams
import files.BasicStatus
import files.CharacterStory
import files.Eidolon
import files.NoOnlineData
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
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.ImageFolder
import ui.components.BackIcon
import ui.components.CharacterEidolon
import ui.components.CharacterTraceTree.CharacterTraceTree
import ui.components.HeaderData
import ui.components.InfoAdviceLightcone
import ui.components.InfoAdviceRelic
import ui.components.InfoAdviceTeammate
import ui.components.InfoBasicStatus
import ui.components.InfoBioColumn
import ui.components.InfoDisplayDialog
import ui.components.InfoNavigateItem
import ui.components.InfoNavigatorBar
import ui.components.InfoStory
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.StatusType
import ui.components.defaultHeaderData
import ui.navigation.hazeStateRoot
import utils.annotation.DoItLater
import utils.app.CharWeightList
import utils.app.Constants.Companion.LOST_IMAGE_DRAWABLE
import utils.app.DefaultZIndex
import utils.app.JsonElementSaver
import utils.app.Language
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.showWarningToast
import utils.app.valueOfWithDefaultCombatType
import utils.app.valueOfWithDefaultPath

private lateinit var localCoroutineScope: CoroutineScope;
private lateinit var localSnackbarHostState: SnackbarHostState;

val charInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 1, Res.string.BasicStatus),
    InfoNavigateItem(Res.drawable.phorphos_tree_structure_regular, 2, Res.string.TraceTree),
    InfoNavigateItem(Res.drawable.phorphos_star_half_regular, 3, Res.string.Eidolon),
    InfoNavigateItem(Res.drawable.phorphos_sword_regular, 4, Res.string.AdviceLightcones),
    InfoNavigateItem(Res.drawable.phorphos_baseball_cap_regular, 5, Res.string.AdviceRelics),
    InfoNavigateItem(Res.drawable.phorphos_person_regular, 6, Res.string.AdviceTeams),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 7, Res.string.CharacterStory),

    )

private const val scrollPxTrigInvisible = 250f

@OptIn(FlowPreview::class)
@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    @DoItLater("Use rememberStatus")
    var density = LocalDensity.current.density
    val characterName = backStackEntry.arguments?.getString("charName")!!.replace("_", " ")
    val characterFileName = backStackEntry.arguments?.getString("fileName")!!
    val characterId = backStackEntry.arguments?.getString("charId")!!
    val combatType = valueOfWithDefaultCombatType(backStackEntry.arguments?.getString("combatType")!!)
    val path = valueOfWithDefaultPath(backStackEntry.arguments?.getString("path")!!)

    val charInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(Character.getCharacterDataFromFileName(characterFileName, Language.TextLanguageInstance) as JsonElement) }

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;


    //Maybe we should make a PomPom Image with "Please Check your Network" Text
    if (charInfoJson !is JsonObject || charInfoJson.jsonObject.isEmpty()) {
        showWarningToast(message = removeStrQuote(Res.string.NoOnlineData))
        navigator.popBackStack()
        return
    }

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

    val singleCharWeightJsonElement = CharWeightList.INSTANCE.jsonObject[characterId]
    var charWeightJsonObject : JsonObject? = null

    if(singleCharWeightJsonElement != null && singleCharWeightJsonElement.jsonArray.size > 0){
        charWeightJsonObject = singleCharWeightJsonElement.jsonArray[selectedSectIndex.value].jsonObject
    }

    BoxWithConstraints {
        val pageSize = Pair(maxWidth, maxHeight)
        CharacterInfoFullImgWithRare(
            fileName = characterName,
            isVisible = !isNaviBarVisible //alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            modifier = Modifier
                .hazeSource(hazeStateRoot, zIndex = DefaultZIndex)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            item { InfoBioColumn(charInfoJson, combatType, path, isUserOwned = false, isFullEidolon = false, pageSize = pageSize) }
            item { InfoBasicStatus(charInfoJson, StatusType.CHARACTER) }
            item { CharacterTraceTree(charInfoJson, path, characterName, dialogTitle, dialogDisplay,dialogLastTrigType,  dialogComponent) }
            item { CharacterEidolon(charInfoJson, characterName, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoAdviceLightcone(charWeightJsonObject) }
            item { InfoAdviceRelic(charWeightJsonObject) }
            item { InfoAdviceTeammate(charWeightJsonObject, characterId, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoStory(charInfoJson) }
            item { Box(modifier = Modifier.navigationBarsPadding().height(72.dp)) }

        }

        PageHeader(
            navigator = navigator,
            headerData = headerDataPage,
            hazeState = hazeStateRoot,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.ic_favourite_btn,
            onForward = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if(dialogDisplay.value){
                InfoDisplayDialog(dialogTitle.value, dialogComponent.value, modifier = Modifier.align(Alignment.BottomCenter), hazeStateRoot, isNavBarVisible = (isNaviBarVisible), isDialogVisible = (dialogDisplay))
            } else {
                InfoNavigatorBar(charInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeStateRoot, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
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
    val imageURL = mutableStateOf(Character.getCharacterImageFromFileName(ImageFolder.CHAR_FULL, fileName))
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).align(Alignment.BottomCenter)
        ) {
            AsyncImage(
                model = newImageRequest(
                    context = LocalPlatformContext.current,
                    data = imageURL.value,
                    crossFade = false
                ),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,onError = { error ->
                    imageURL.value = Character.getCharacterImageFromFileName(ImageFolder.CHAR_SPLASH, fileName)
                },
                error = painterResource(LOST_IMAGE_DRAWABLE)
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
