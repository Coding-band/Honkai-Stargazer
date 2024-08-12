package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.intl.Locale
import components.HeaderData
import components.PageHeaderAlpha
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


    Box(Modifier.fillMaxSize()) {
        Locale.current.language
        PageHeaderAlpha(
            navigator = navigator,
            hazeState = hazeState,
        )
    }
}