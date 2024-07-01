package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import components.HeaderData
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import types.Character
import utils.UtilTools

lateinit var localCoroutineScope : CoroutineScope;
lateinit var localSnackbarHostState : SnackbarHostState;
@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry : NavBackStackEntry? = null,
    snackbarHostState : SnackbarHostState? = remember { SnackbarHostState() },
) {
    val characterFileName = backStackEntry!!.arguments?.getString("fileName")
    val characterName = backStackEntry!!.arguments?.getString("charName")

    if (characterFileName === null || characterName === null) return;

    val hazeState = remember { HazeState() }
    val textLanguage = UtilTools.TextLanguage.ZH_HK //Later edit
    val charInfoJson = Character.getCharacterDataFromFileName(characterFileName, textLanguage)

    localCoroutineScope  = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    Box{
        PageHeader()
        CharacterInfoFullImgWithRare(fileName = characterName)
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
                .align(Alignment.BottomCenter)
            ,
            contentScale = ContentScale.Fit,

        )
    }
}