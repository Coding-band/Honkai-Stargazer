package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import components.HeaderData
import components.PAGE_HEADER_ALPHA_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Eidolon
import files.Res
import files.ui_icon_share
import files.ui_icon_star
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Constants
import types.UserAccount
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.FontSizeNormalLarge32
import utils.UtilTools
import utils.annotation.DoItLater

@DoItLater("Get User Data from Database / API")
@Composable
fun UserCharacterPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
) {
    val uid = backStackEntry!!.arguments?.getString("uid")!!
    val userAccount by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAccount.INSTANCE
        } else {
            UserAccount.INSTANCE
        }
    ) }
    val characterId = backStackEntry!!.arguments?.getInt("charId")!!
    val characterFilter = userAccount.characterList.filter { it.officialId == characterId }
    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            // whatever logic you need
            listState.canScrollBackward
        }
    }
    var character = if(characterFilter.isEmpty()) null else characterFilter[0]

    if(character == null){ navController.popBackStack() }else{
        Box(modifier = modifier.fillMaxSize()){
            CharacterInfoFadeImg(
                fileName = character.registName!!,
                isVisible = !isScrolling //alpha = scrollToAlpha
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(
                    start = Constants.SCREEN_SAVE_PADDING,
                    end = Constants.SCREEN_SAVE_PADDING
                ).haze(hazeState).navigationBarsPadding()
            ) {
                item { Spacer(Modifier.statusBarsPadding().height(PAGE_HEADER_ALPHA_HEIGHT + 240.dp)) }
                item { CharBioSkillInfo(character) }
            }


            PageHeaderAlpha(
                navController = navController,
                onForward = {
                    //TODO : Remember to add the Share Function
                },
                forwardIconId = Res.drawable.ui_icon_share
            ) {
                Column(Modifier.fillMaxSize()) {
                    Text(
                        userAccount.username,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                        style = FontSizeNormal16(),
                        color = Color.White
                    )

                    Text(
                        text = "${userAccount.uid}·${
                            UtilTools().removeStringResDoubleQuotes(
                                userAccount.server.localeName)}",
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                            .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                            .clip(RoundedCornerShape(49.dp)).padding(8.dp),
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                }
            }
        }
    }


}

@Composable
fun CharBioSkillInfo(character: Character) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = Character.getCharacterItemFromJSON(character.officialId.toString()).displayName!!,
            style = FontSizeNormalLarge32(),
            color = Color.White
        )

        Spacer(modifier = Modifier.size(6.dp))

        Row {
            repeat(character.rarity) {
                Image(
                    modifier = Modifier.size(12.dp,12.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "Stars to represent Rarity"
                )
            }
        }

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            "Lv ${character.characterStatus?.characterLevel} · ${UtilTools().removeStringResDoubleQuotes(Res.string.Eidolon)} ${character.characterStatus?.eidolon}",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
            style = FontSizeNormal16(),
            color = Color.White
        )

    }
}

@Composable
fun CharacterInfoFadeImg(
    modifier: Modifier = Modifier,
    fileName: String,
    isVisible: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.TopCenter)
        ) {
            AsyncImage(
                model = UtilTools().newImageRequest(context = LocalPlatformContext.current, data = Character.getCharacterImageByteArrayFromFileName(
                    UtilTools.ImageFolderType.CHAR_FADE, fileName
                )),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
                imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
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
