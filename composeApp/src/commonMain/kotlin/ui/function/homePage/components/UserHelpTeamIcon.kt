package ui.function.homePage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import moe.tlaster.precompose.navigation.Navigator
import type.Character
import type.ImageFolder
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.getAssetsURLByFileName


@Composable
fun UserHelpTeamIcon(
    modifier: Modifier = Modifier,
    character: Character,
    navigator: Navigator,
    uid: String
) {
    AsyncImage(
        model = getAssetsURLByFileName(ImageFolder.CHAR_ICON, character.registName!!),
        contentDescription = "Character Helper Icon",
        modifier = Modifier
            .size(30.dp)
            .background(Color(0xFFD9D9D9), CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
            .clickable {
                if (uid != "") {
                    navigator.navigateLimited("${Screen.UserCharacterPageScreen.route}?uid=${uid}&charId=${character.officialId}")
                }
            }
    )
    Spacer(modifier = Modifier.width(4.dp))
}