package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import dev.chrisbanes.haze.HazeState
import getDeviceInfo
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import utils.app.Language
import utils.app.showWarningToast

@Composable
fun MapPageScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData,
) {
    val hazeState = remember { HazeState() }
    val webviewState = rememberWebViewState("https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=${Language.TextLanguageInstance.hoyolabName}")

    val uriHandler = LocalUriHandler.current
    LaunchedEffect(Unit) {
        if(getDeviceInfo().deviceOSName.lowercase().let {
                it.contains("mac") || it.contains("windows") || it.contains("linux")
            }){
            //Since this is temporately, dont need translation
            showWarningToast(message = "PC端暫不支援內嵌瀏覽器\nCurrently PC does not support WebView")
            uriHandler.openUri("https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=${Language.TextLanguageInstance.hoyolabName}")
            navigator.popBackStack()
        }
    }

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