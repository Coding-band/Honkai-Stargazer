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
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.stringResource
import types.Character
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
    val characterFileName = backStackEntry!!.arguments?.getString("fileName")
    val characterName = backStackEntry!!.arguments?.getString("charName")


    if (characterFileName === null || characterName === null) return;

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
            CharacterInfoBioColumn(charInfoJson)
        }
    }
}

@Composable
fun CharacterInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    fileName: String
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

@Composable
fun CharacterInfoBioColumn(charInfoJson: JsonElement) {
    Column {
        Box(Modifier.height(getScreenSizeInfo().hDP - PAGE_HEADER_HEIGHT)){}

        Row {
            Text(
                text = charInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
                color = Color.White,
                fontSize = 32.sp
            )
            Text(
                text = stringResource(Res.string.UserOwned),
                fontSize = 8.sp
            )
        }
    }
}