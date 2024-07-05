package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import components.BackIcon
import components.HeaderData
import components.InfoBioColumn
import components.InfoNavigateItem
import components.InfoNavigatorBar
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.ThemedSlider
import components.TitleHeader
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
import files.ic_aggro
import files.ic_arrow_to_down
import files.ic_atk
import files.ic_def
import files.ic_energy
import files.ic_hp
import files.ic_speed
import files.phorphos_baseball_cap_regular
import files.phorphos_chats_circle_regular
import files.phorphos_info_regular
import files.phorphos_person_fill
import files.phorphos_person_regular
import files.phorphos_star_half_regular
import files.phorphos_sword_regular
import files.phorphos_tree_structure_regular
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.CombatType
import utils.FontSizeNormal16
import utils.UtilTools
import utils.annotation.DoItLater
import utils.calculator.CharacterAttrData
import utils.calculator.getCharAttrData
import kotlin.math.max

lateinit var localCoroutineScope: CoroutineScope;
lateinit var localSnackbarHostState: SnackbarHostState;

val charInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_info_regular, 1, Res.string.BasicStatus),
    InfoNavigateItem(Res.drawable.phorphos_tree_structure_regular, 2, Res.string.TraceTree),
    InfoNavigateItem(Res.drawable.phorphos_star_half_regular, 3, Res.string.Eidolon),
    InfoNavigateItem(Res.drawable.phorphos_sword_regular, 4, Res.string.AdviceLightcones),
    InfoNavigateItem(Res.drawable.phorphos_baseball_cap_regular, 5, Res.string.AdviceRelics),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 6, Res.string.AdviceTeams),
    InfoNavigateItem(Res.drawable.phorphos_person_regular, 7, Res.string.CharacterStory),
)

const val scrollPxTrigInvisible = 250f

@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    var density = LocalDensity.current.density
    val characterFileName = backStackEntry!!.arguments?.getString("fileName")!!
    val characterName = backStackEntry.arguments?.getString("charName")!!.replace("_", " ")
    val combatType = CombatType.valueOf(backStackEntry.arguments?.getString("combatType")!!)
    val path = types.Path.valueOf(backStackEntry.arguments?.getString("path")!!)

    val hazeState = remember { HazeState() }
    val textLanguage = UtilTools.TextLanguage.ZH_HK //Later edit
    val charInfoJson = Character.getCharacterDataFromFileName(characterFileName, textLanguage)

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        charInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
        titleIconId = Res.drawable.phorphos_person_fill
    )

    val listState = rememberLazyListState()
    var scrollToAlpha = if(listState.firstVisibleItemIndex == 0) max(0f, (scrollPxTrigInvisible - listState.firstVisibleItemScrollOffset) / 200f) else 0f
    Box {

        CharacterInfoFullImgWithRare(
            fileName = characterName,
            alpha = scrollToAlpha
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.haze(hazeState)) {
            item {InfoBioColumn(charInfoJson, combatType, path, isUserOwned = false, isFullEidolon = false) }
            //Don't forget to add "StatusBarPadding" !
            item { charBasicStatusUI(charInfoJson) }
            item {
                Text("我是Index 2", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
            item {
                Text("我是Index 3", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
            item {
                Text("我是Index 4", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
            item {
                Text("我是Index 5", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
            item {
                Text("我是Index 6", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
            item {
                Text("我是Index 7", fontSize = 32.sp, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(600.dp).statusBarsPadding())
            }
        }

        PageHeader(
            navController = navController,
            headerData = headerDataPage,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL
        )

        Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
            InfoNavigatorBar(charInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeState, alpha = (1 - scrollToAlpha), offSet = PAGE_HEADER_HEIGHT)
        }
    }
}

@Composable
fun CharacterInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    fileName: String,
    alpha: Float = 1f
) {
    Box(modifier = Modifier.fillMaxSize().alpha(alpha)) {
        Image(

            bitmap = Character.getCharacterImageFromFileName(
                UtilTools.ImageFolderType.CHAR_FULL, fileName
            ),
            contentDescription = "Character Full Image",
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).align(Alignment.BottomCenter),
            contentScale = ContentScale.Fit,
        )
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
fun charBasicStatusUI(charInfoJson : JsonElement){
    var charStatusCal : CharacterAttrData by remember { mutableStateOf(CharacterAttrData(0f,0f,0f,0f,0,0)) }

    charStatusCal = getCharAttrData(charInfoJson, 80)
    var basicStatusLvBegin by remember { mutableStateOf(1f) }
    var basicStatusLvEnd by remember { mutableStateOf(80f) }

    if(basicStatusLvBegin > basicStatusLvEnd){
        basicStatusLvBegin = basicStatusLvEnd
    }else if(basicStatusLvEnd < basicStatusLvBegin){
        basicStatusLvEnd = basicStatusLvBegin
    }

    Column (modifier = Modifier.statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_info_regular, titleRId = Res.string.BasicStatus)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Character Attr Data
        DoItLater("")
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_hp), contentDescription = "HP Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.hp).toInt().toString(), color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_atk), contentDescription = "ATK Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.atk).toInt().toString(), color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_def), contentDescription = "DEF Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.def).toInt().toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_speed), contentDescription = "SPEED Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.spd).toInt().toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_energy), contentDescription = "ENERGY Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.energy).toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_aggro), contentDescription = "AGGRO Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (charStatusCal.aggro).toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Level Slider
        Column {
            Row(Modifier.height(20.dp)){
                Text("Lv.${basicStatusLvBegin.toInt()}", modifier = Modifier.width(60.dp).align(Alignment.CenterVertically), color = Color.White)
                ThemedSlider(basicStatusLvBegin, { basicStatusLvBegin = it})
            }

            Image(painterResource(Res.drawable.ic_arrow_to_down), contentDescription = "Arrow Down", modifier = Modifier.padding(8.dp))

            Row(Modifier.height(20.dp)){
                Text("Lv.${basicStatusLvEnd.toInt()}", modifier = Modifier.width(60.dp).align(Alignment.CenterVertically), color = Color.White)
                ThemedSlider(basicStatusLvEnd, { basicStatusLvEnd = it})
            }
        }

    }
}
