package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import moe.tlaster.precompose.navigation.Navigator
import utils.app.Language

@Composable
fun MapPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
) {
    val hazeState = remember { HazeState() }
    val webviewState = rememberWebViewState("https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=${Language.TextLanguageInstance.hoyolabName}")

    Box(modifier = Modifier.fillMaxSize()) {

        WebView(webviewState,
            modifier = Modifier.fillMaxSize().padding(top = PAGE_HEADER_HEIGHT).statusBarsPadding()
                .navigationBarsPadding()
        )

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )
    }
}