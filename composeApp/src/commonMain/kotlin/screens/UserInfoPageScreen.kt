package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.voc.honkai_stargazer.component.CharacterCard
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import files.PlayerLevel
import files.PublicChars
import files.Res
import files.UserInfoGameData
import files.UserInfoGameWorldLevel
import files.UserInfoOwnedCharacters
import files.ui_icon_share
import types.Constants
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.UserAccount
import utils.FontSizeNormal12
import utils.FontSizeNormal16
import utils.FontSizeNormalLarge24
import utils.UtilTools

@Composable
fun UserInfoPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
) {
    val hazeState = remember { HazeState() }
    val context = LocalPlatformContext.current
    val userAccount by remember { mutableStateOf(UserAccount.INSTANCE) }
    val bioUIHeight = remember { mutableStateOf(0f) }

    Box(modifier = modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)) {
            Spacer(Modifier.height(PAGE_HEADER_HEIGHT + 8.dp))
            UserInfoBioUI(context, userAccount)
            UserInfoCharacterList(userAccount)
        }

        PageHeaderAlpha(
            navController = navController,
            forwardIconId = Res.drawable.ui_icon_share,
            onForward = { /* TODO : Share Function*/ },
        ){
            Box(Modifier
                .fillMaxSize()
            ){
                Text(
                    UtilTools().removeStringResDoubleQuotes(Res.string.UserInfoGameData),
                    modifier = Modifier
                        .background(Color(0x33FFFFFF),RoundedCornerShape(16.dp))
                        .padding(top = 6.dp, bottom = 6.dp, start = 12.dp, end = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.Center),
                    color = Color.White,
                    style = FontSizeNormal16()
                )
            }
        }
    }
}

@Composable
fun UserInfoBioUI(context: PlatformContext, userAccount: UserAccount) {
    Column (Modifier.fillMaxWidth()) {
        //User Avatar (With Reducing Padding's Scale)
        Box(Modifier.requiredSize(72.dp)
            .align(Alignment.CenterHorizontally)
            .background(Color(0xFFCAB89E), CircleShape)
            .clip(CircleShape)
            .border(1.dp, Color(0x66907C54), CircleShape)
            .clickable {
                if (userAccount.nickname != "") {
                   //Print Nickname
                }
            }, contentAlignment = Alignment.Center
        ) {

            val scale = if(userAccount.icon.startsWith("http")) 1.142857f else 1f
            Box(Modifier.requiredSize(72.dp * scale)) {
                // User Avatar
                AsyncImage(
                    modifier = Modifier.size(72.dp * scale),
                    model = UtilTools().newImageRequest(context, userAccount.icon),
                    imageLoader = UtilTools().newImageLoader(context),
                    contentDescription = "",
                )
            }
        }

        Text(
            userAccount.username,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
            style = FontSizeNormalLarge24(),
            color = Color.White
        )

        Text(
            text = "${userAccount.uid}·${UtilTools().removeStringResDoubleQuotes(
                userAccount.server.localeName)}",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                .clip(RoundedCornerShape(49.dp)).padding(8.dp),
            style = FontSizeNormal12(),
            color = Color.White
        )

        Row(Modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.Center) {
            val rowData = arrayListOf(
                "${userAccount.level}" to Res.string.PlayerLevel,
                "${userAccount.ascLevel}" to Res.string.UserInfoGameWorldLevel,
                "${userAccount.unlockedCharCount}" to Res.string.UserInfoOwnedCharacters,
            )

            for ((index, data) in rowData.withIndex()){

                Spacer(modifier = Modifier.width(24.dp))

                Column(Modifier.wrapContentHeight()) {
                    Text(
                        data.first,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                        style = FontSizeNormalLarge24(),
                        color = Color.White
                    )

                    Text(
                        UtilTools().removeStringResDoubleQuotes(data.second),
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                        style = FontSizeNormal12(),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                if (index < rowData.size - 1) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .background(Color(0x66F3F9FF))
                            .padding(top = 16.dp, bottom = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoCharacterList(userAccount: UserAccount) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(top = 4.dp, bottom = 4.dp)) {
            Text(
                UtilTools().removeStringResDoubleQuotes(Res.string.PublicChars),
                modifier = Modifier.padding(end = 8.dp),
                style = FontSizeNormal16(),
                color = Color.White
            )
        }


    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = rememberLazyGridState(),
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        for (character in userAccount.characterList) {
            item {
                CharacterCard(character = character)
            }
        }
    }


}