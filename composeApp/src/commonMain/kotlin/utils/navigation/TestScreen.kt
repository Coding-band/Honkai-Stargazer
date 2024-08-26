package utils.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.BackIcon
import components.HeaderData
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun BlankScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    Box(Modifier.fillMaxSize().background(Color(0xFFCCCCCC))) {
        Button(onClick = {
            navigator.navigateLimited(Screen.BlankScreen.route)
        }) {
             Text("Go Next Blank Screen")
        }
    }
}


@Composable
fun WithBGScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    haveHeader : Boolean = false,
) {
    val hazeState = remember { HazeState() }

    Box(Modifier.fillMaxSize()){
        if(haveHeader){
            PageHeader(navigator = navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.CANCEL)
        }
    }
}