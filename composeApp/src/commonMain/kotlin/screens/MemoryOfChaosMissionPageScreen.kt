package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import components.HeaderData
import components.PageHeaderAlpha
import components.TitleHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MemoryOfChaosMissionPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {
    var density = LocalDensity.current.density
    val hazeState = remember { HazeState() }

    val mocList =

    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Row(Modifier.fillMaxWidth().wrapContentHeight()){
                    //UIButton(Modifier.weight(1f), text = )
                }
            }
        }


        PageHeaderAlpha(
            navigator = navigator,
            hazeState = hazeState,
        ){
            TitleHeader(headerData.titleIconId,headerData.title,headerData.titleRId)
        }
    }
}

@Composable
fun MemoryOfChaosEnemyList(
    modifier: Modifier = Modifier,
) {
    var density = LocalDensity.current.density
    val hazeState = remember { HazeState() }

    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            item {


            }
        }
    }
}