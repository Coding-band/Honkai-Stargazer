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
    headerData: HeaderData = defaultHeaderData
) {
    var character = headerData.otherData as Character
    if (character.fileName === null) return;

    val hazeState = remember { HazeState() }
    val textLanguage = UtilTools.TextLanguage.ZH_HK //Later edit
    val charInfoJson = Character.getCharacterDataFromFileName(character.fileName!!, textLanguage)

    Box{
        CharacterInfoFullImgWithRare(character = character)
    }
}

@Composable
fun CharacterInfoFullImgWithRare(
    modifier: Modifier = Modifier,
    character: Character
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = Character.getCharacterImageFromFileName(
                UtilTools.ImageFolderType.CHAR_FULL,
                character.fileName!!
            ),
            contentDescription = "Character Full Image",
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(120.dp,420.dp),
        )
    }
}