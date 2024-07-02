package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import files.Res
import files.UserOwned
import files.phorphos_person_fill
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import types.Character
import types.CombatType
import utils.FontSizeNormal
import utils.FontSizeNormal16
import utils.UtilTools

lateinit var localCoroutineScope: CoroutineScope;
lateinit var localSnackbarHostState: SnackbarHostState;

@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {
    val characterFileName = backStackEntry!!.arguments?.getString("fileName")!!
    val characterName = backStackEntry.arguments?.getString("charName")!!.replace("_"," ")
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

    Box {
        PageHeader(
            navController = navController,
            headerData = headerDataPage,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL
        )
        CharacterInfoFullImgWithRare(fileName = characterName)

        //ScrollView
        Column {
            CharacterInfoBioColumn(charInfoJson,combatType,path)
        }
    }
}

@Composable
fun CharacterInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    fileName: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(

            bitmap = Character.getCharacterImageFromFileName(
                UtilTools.ImageFolderType.CHAR_FULL,
                fileName
            ),
            contentDescription = "Character Full Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Fit,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0x00000000), Color(0xCC000000))
                    )
                )
                .align(Alignment.BottomCenter),
        )

    }
}

//This will repack as Componemts later
@Composable
fun CharacterInfoBioColumn(
    infoJson: JsonElement,
    combatType: CombatType? = null,
    path: types.Path? = null,
) {
    Column {
        Box(Modifier.height(getScreenSizeInfo().hDP - PAGE_HEADER_HEIGHT)) {}

        Row(modifier = Modifier.padding(start = 28.dp, end = 28.dp)) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = infoJson.jsonObject["name"]!!.jsonPrimitive.content,
                style = FontSizeNormal(),
                fontSize = 32.sp,
                color = Color.White,
            )

            //Database Required
            //UserOwned || FullEidolon
            Box(Modifier.background(Color(0xFFF3F9FF)).clip(RoundedCornerShape(34.dp)).padding(start = 6.dp)) {
                Text(
                    text = stringResource(Res.string.UserOwned),
                    style = FontSizeNormal(),
                    fontSize = 8.sp,
                    color = Color(0xFF393A5C),
                )
            }
        }

        Row {
            Row {

                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentDescription = "Stars to represent Rarity"
                )
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentDescription = "Stars to represent Rarity"
                )
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentDescription = "Stars to represent Rarity"
                )
                if(rarity >= 4){
                    Image(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(Res.drawable.ui_icon_star),
                        contentDescription = "Stars to represent Rarity"
                    )
                }
                if(rarity >= 5){
                    Image(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(Res.drawable.ui_icon_star),
                        contentDescription = "Stars to represent Rarity"
                    )
                }
            }
            Text(
                text = infoJson.jsonObject["archive"]!!.jsonObject["camp"]!!.jsonPrimitive.content,
                style = FontSizeNormal16(),
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }



        Row {
            if (combatType !== null) {
                Image(
                    painter = painterResource(combatType.iconWhite),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "CombatType Icon"
                )
                Text(
                    text = combatType.chName,
                    style = FontSizeNormal16(),
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

        }

    }
}