package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import components.HeaderData
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import types.Character
import utils.UtilTools


@Composable
fun CharacterInfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry : NavBackStackEntry? = null,
) {
    val characterFileName = backStackEntry!!.arguments?.getString("fileName")
    val characterName = backStackEntry!!.arguments?.getString("charName")

    if (characterFileName === null || characterName === null) return;

    val hazeState = remember { HazeState() }
    val textLanguage = UtilTools.TextLanguage.ZH_HK //Later edit
    val charInfoJson = Character.getCharacterDataFromFileName(characterFileName, textLanguage)

    Box{
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
                    .replace("_imaginary","")
                    .replace("_fire","")
                    .replace("_physical","")
            ),
            contentDescription = "Character Full Image",
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(120.dp,420.dp),
        )
    }
}