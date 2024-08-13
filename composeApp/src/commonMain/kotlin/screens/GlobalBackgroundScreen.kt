/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.BlackAlpha20
import utils.BlackAlpha80
import utils.Stargazer3Theme
import utils.UtilTools
import utils.navigation.Screen


val gradient = Brush.verticalGradient(
    colors = listOf(
        BlackAlpha20,
        BlackAlpha80
    )
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MakeBackground(modifier: Modifier = Modifier, screen: Screen) {
    val hazeState = remember { HazeState() }
    var isBlur = true;
    val backgroundImageBitmap = UtilTools().getAssetsWebpByFileName(
        UtilTools.ImageFolderType.BGS,
        Settings().getString("backgroundImage", "221000")
    );
    //var backgroundBitmap = UtilTools().getAssetsWebpByContext(context = LocalContext.current, "images/${UtilTools.ImageFolderType.BGS.folderPath}1006.webp")
    when(screen){
        Screen.HomePage -> {isBlur = false;}
        Screen.BackgroundSettingScreen -> {isBlur = false;}
        Screen.MemoryOfChaosMissionPageScreen -> {isBlur = false;}
        else -> {}
    }
    Box{
        Image(
            bitmap = when(screen){
                Screen.BackgroundSettingScreen -> UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.BGS, "bg_light")
                Screen.MemoryOfChaosMissionPageScreen -> UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.BGS, "memory_of_chaos_bg")
                else -> backgroundImageBitmap
            },
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().blur(if (isBlur) 20.dp else 0.dp)
        )
        Box(modifier = Modifier.matchParentSize().background(gradient))
    }
}

@Preview
@Composable
fun GlobalBackgroundPreview() {
    Stargazer3Theme {
        MakeBackground(screen = Screen.HomePage)
    }
}