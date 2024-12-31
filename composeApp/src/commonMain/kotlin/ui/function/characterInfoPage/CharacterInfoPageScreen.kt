package ui.function.characterInfoPage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.CharacterTraceTree.CharacterTraceTree
import ui.components.InfoAdviceLightcone
import ui.components.InfoAdviceRelic
import ui.components.InfoAdviceTeammate
import components.InfoBasicStatus
import components.InfoBioColumn
import components.InfoNavigateItem
import components.InfoNavigatorBar
import components.InfoStory
import components.StatusType
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import type.Character
import type.CombatType
import type.Path
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.InfoDisplayDialog
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import ui.function.characterInfoPage.components.CharacterEidolon
import ui.function.characterInfoPage.components.CharacterInfoFullImg
import utils.app.CharWeightList
import utils.app.Language

val charInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 1, Res.string.BasicStatus),
    InfoNavigateItem(Res.drawable.phorphos_tree_structure_regular, 2, Res.string.TraceTree),
    InfoNavigateItem(Res.drawable.phorphos_star_half_regular, 3, Res.string.Eidolon),
    InfoNavigateItem(Res.drawable.phorphos_sword_regular, 4, Res.string.AdviceLightcones),
    InfoNavigateItem(Res.drawable.phorphos_baseball_cap_regular, 5, Res.string.AdviceRelics),
    InfoNavigateItem(Res.drawable.phorphos_person_regular, 6, Res.string.CharacterStory),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 7, Res.string.AdviceTeams),
    )

@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    backStackEntry: BackStackEntry,
    headerData: HeaderData = defaultHeaderData,
)  {
    val viewModel = CharacterInfoPageViewModel(navigator = navigator)
    val state by viewModel.state.collectAsState()

    val characterName = backStackEntry.path<String>("charName")!!.replace("_", " ")
    val characterFileName = backStackEntry.query<String>("fileName")!!
    val characterId = backStackEntry.query<String>("charId")!!
    val combatType = CombatType.valueOf(backStackEntry.query<String>("combatType")!!)
    val path = Path.valueOf(backStackEntry.query<String>("path")!!)

    viewModel.handleIntent(CharacterInfoPageIntent.SetCharacterInfoJson(Character.getCharacterDataFromFileName(characterFileName, Language.TextLanguageInstance)))
    viewModel.handleIntent(CharacterInfoPageIntent.SetCharOnlyWeightJson(CharWeightList.INSTANCE.jsonObject[characterId] ?: Json.parseToJsonElement("{}")))

    val listState = rememberLazyListState()
    var isNaviBarVisible by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollBackward }
            .distinctUntilChanged()
            .collect {
                isNaviBarVisible = it
            }
    }

    val headerDataPage = HeaderData(
        state.charInfoJson.value.jsonObject["name"]!!.jsonPrimitive.content,
        titleIconId = Res.drawable.phorphos_person_fill
    )
    //It will be transfer from CharacterTraceTree.kt !
    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }
    val selectedSectIndex = remember { mutableStateOf(0) } //流派

    Box {
        CharacterInfoFullImg(
            fileName = characterName,
            isVisible = !isNaviBarVisible //alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = listState, modifier = Modifier.haze(state.hazeState).align(
            Alignment.Center).navigationBarsPadding()) {
            item { InfoBioColumn(state.charInfoJson.value, combatType, path, isUserOwned = false, isFullEidolon = false) }
            //Don't forget to add "StatusBarPadding" !
            item { InfoBasicStatus(state.charInfoJson.value, StatusType.CHARACTER) }
            item { CharacterTraceTree(state.charInfoJson.value, path, characterName, dialogTitle, dialogDisplay,dialogLastTrigType,  dialogComponent) }
            item { CharacterEidolon(state.charInfoJson.value, characterName, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoAdviceLightcone(state.charOnlyWeightJson.value.jsonArray[selectedSectIndex.value].jsonObject) }
            item { InfoAdviceRelic(state.charOnlyWeightJson.value.jsonArray[selectedSectIndex.value].jsonObject) }
            item { InfoAdviceTeammate(state.charOnlyWeightJson.value.jsonArray[selectedSectIndex.value].jsonObject, characterId, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent) }
            item { InfoStory(state.charInfoJson.value) }
            item { Box(modifier = Modifier.height(72.dp)) }

        }

        PageHeader(
            navigator = navigator,
            headerData = headerDataPage,
            hazeState = state.hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.ic_favourite_btn,
            onForward = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if(dialogDisplay.value){
                InfoDisplayDialog(dialogTitle.value, dialogComponent.value, modifier = Modifier.align(
                    Alignment.BottomCenter), state.hazeState, isNavBarVisible = (isNaviBarVisible), isDialogVisible = (dialogDisplay))
            } else {
                InfoNavigatorBar(charInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = state.hazeState, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
            }
        }
    }
}