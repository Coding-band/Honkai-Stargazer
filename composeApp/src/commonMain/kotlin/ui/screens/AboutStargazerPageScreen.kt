package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.AboutTheApp
import files.Res
import files.codingband
import files.phorphos_film_slate_fill
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.defaultHeaderData
import utils.app.Constants
import utils.app.LongStringXML
import utils.app.removeStrQuote

@Composable
fun AboutStargazerPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {
    val headerHTML =
            "<style>"+
                    "  * {\n" +
                    "    color: #DDD;\n" +
                    "  }\n"+

                    "  h2 {\n" +
                    "    font-size: 20;\n" +
                    "    color: #e9ba79;\n" +
                    "  }\n"+
            "</style>"
    val hazeState = remember { HazeState() }
    val htmlData = (headerHTML + "<body>" +
            (LongStringXML().AboutTheAppContent()
                .replace("&lt;t class=\"t_lc\"&gt;","")
                .replace("&lt;t class=\"t_gl\"&gt;","")
                .replace("&lt;/t&gt;",""))
            +
            "</body>").trimIndent()

    val webViewState = rememberWebViewStateWithHTMLData(htmlData)


    Box(modifier = modifier.fillMaxSize()){

        LazyColumn(modifier = Modifier.fillMaxSize().haze(hazeState)) {
            item {
                Spacer(Modifier.padding(top = PAGE_HEADER_HEIGHT).statusBarsPadding())
            }
            item {
                WebView(webViewState, )
            }
            item {
                BoxWithConstraints {
                    Image(
                        painter = painterResource(Res.drawable.codingband),
                        contentDescription = null,
                        modifier = Modifier.widthIn(maxWidth, androidx.compose.ui.unit.min(maxWidth, 480.dp)).aspectRatio(1f),
                    )
                }
            }
            item {
                Spacer(Modifier.navigationBarsPadding())
            }
        }

        PageHeader(
            navigator = navigator,
            headerData = HeaderData(title = removeStrQuote(Res.string.AboutTheApp), titleIconId = Res.drawable.phorphos_film_slate_fill),
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )

    }
}