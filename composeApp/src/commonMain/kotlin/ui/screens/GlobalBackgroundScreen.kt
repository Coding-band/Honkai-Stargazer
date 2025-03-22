/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.Res
import files.apocalyptic_shadow_bg
import files.bg_default
import files.memory_of_chaos_bg
import files.pure_fiction_bg
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.ImageFolder
import ui.navigation.Screen
import ui.navigation.hazeStateRoot
import utils.app.BackgroundZIndex
import utils.app.Black
import utils.app.BlackAlpha20
import utils.app.BlackAlpha80
import utils.app.Stargazer3Theme
import utils.app.Transparent
import utils.app.getAssetsURLByFileName
import utils.app.newImageRequest


val gradient = Brush.verticalGradient(
    colors = listOf(
        BlackAlpha80,
        BlackAlpha20
    )
)
val gradientBottom = Brush.verticalGradient(
    colors = listOf(
        Transparent,
        Black
    )
)

//lateinit var backgroundScreenHazeState : HazeState
val bgModified = mutableStateOf(false)
val globalHazeBlur = mutableStateOf(Settings().getBoolean("useHazeBlurEffect", true))
val globalPadHomePageBg = mutableStateOf(Settings().getBoolean("padModeHomePageBG", true))

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MakeBackground(
    screen: Screen,
    forceBlur: Boolean? = null,
    hazeState: HazeState = hazeStateRoot
) {
    /*
    if(!::backgroundScreenHazeState.isInitialized){
        backgroundScreenHazeState = remember { HazeState() }
    }
     */
    var isBlur = true;
    //var isForceBlur = Settings().getBoolean("useBlurEffect", false) || forceBlur;
    var isGradient = true;
    val backgroundImage = remember {
        mutableStateOf(
            getAssetsURLByFileName(
                ImageFolder.BGS,
                Settings().getString("backgroundImage", "221000")
            )
        )
    }

    LaunchedEffect(bgModified.value) {
        if(bgModified.value){
            backgroundImage.value = getAssetsURLByFileName(
                ImageFolder.BGS,
                Settings().getString("backgroundImage", "221000")
            )
            bgModified.value = false
        }
    }

    //var backgroundBitmap = UtilTools().getAssetsWebpByContext(context = LocalContext.current, "images/${UtilTools.ImageFolderType.BGS.folderPath}1006.webp")
    when(screen){
        Screen.HomePage -> {isBlur = false;}
        Screen.BackgroundSettingScreen -> {isBlur = false;}
        Screen.MemoryOfChaosMissionPageScreen -> {isBlur = false; isGradient = false}
        Screen.PureFictionMissionPageScreen -> {isBlur = false; isGradient = false}
        else -> {}
    }
    if(forceBlur == true){ isBlur = true; }
    if(forceBlur == false){ isBlur = false; }

    Box(
        Modifier.hazeSource(hazeState, zIndex = BackgroundZIndex)
    ){
        if(listOf(
                Screen.BackgroundSettingScreen,
                Screen.PureFictionMissionPageScreen,
                Screen.MemoryOfChaosMissionPageScreen,
                Screen.ApocalypticShadowMissionPageScreen,
        ).contains(screen)){
            Image(
                painter = when(screen){
                    Screen.BackgroundSettingScreen -> painterResource(Res.drawable.bg_default)
                    Screen.MemoryOfChaosMissionPageScreen -> painterResource(Res.drawable.memory_of_chaos_bg)
                    Screen.PureFictionMissionPageScreen -> painterResource(Res.drawable.pure_fiction_bg)
                    Screen.ApocalypticShadowMissionPageScreen -> painterResource(Res.drawable.apocalyptic_shadow_bg)
                    else -> painterResource(Res.drawable.bg_default)
                },
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().let {
                    if(isBlur) it.blur(20.dp) else it
                }
            )
        }else{
            AsyncImage(
                model = newImageRequest(
                    context = LocalPlatformContext.current,
                    backgroundImage.value
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(Res.drawable.bg_default),
                modifier = Modifier.fillMaxSize().let {
                    if(isBlur) it.blur(20.dp) else it
                }
            )
        }
        Box(
            modifier = Modifier.matchParentSize().background(
                if (isGradient) gradientBottom else Brush.linearGradient(listOf(Color.Transparent,Color.Transparent))
            )
        )
    }
}

@Preview
@Composable
fun GlobalBackgroundPreview() {
    Stargazer3Theme {
        MakeBackground(screen = Screen.HomePage)
    }
}