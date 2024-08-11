package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Res
import files.phorphos_film_slate_fill
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import types.Constants
import types.EventItem
import types.EventItem.Companion.EventListInstance
import utils.UtilTools

//This Header was copy from "rn-branch\src\components\EventScreen\Event\EventWebView\EventWebView.tsx
val headerHTML = "<meta name=\"viewport\" content=\"initial-scale=1.0, maximum-scale=1.0\" />\n" +
        "<style>\n" +
        "  * {\n" +
        "    margin: 0;\n" +
        "    padding: 0;\n" +
        "  }\n" +
        "  body {\n" +
        "    font-family: \"Gill Sans\", sans-serif;\n" +
        "    font-weight: 600;\n" +
        "    color: #ddd;\n" +
        "  }\n" +
        "  .title {\n" +
        "    color: white;\n" +
        "    font-size: 150%;\n" +
        "    font-weight: bold;\n" +
        "    text-align: center;\n" +
        "    margin-bottom: 32px;\n" +
        "  }\n" +
        "  strong{\n" +
        "    color: #DD8200;\n" +
        "  }\n" +
        "  h1 {\n" +
        "    font-size: 125%;\n" +
        "    color: #e9ba79;\n" +
        "    margin-bottom: 8px;\n" +
        "  }\n" +
        "  img {\n" +
        "    width: 100%;\n" +
        "    margin-top: 20px;\n" +
        "    margin-bottom: 20px;\n" +
        "   \n" +
        "  }\n" +
        "  \n" +
        "table{\n" +
        "   font-weight: 600;\n" +
        "}\n" +
        "\n" +
        "td{\n" +
        "  background-color:#f3f3f3;\n" +
        "  color:  rgb(157, 133, 99);\n" +
        "  height: 50px;\n" +
        "  text-align:center;\n" +
        "}\n" +
        "\n" +
        "tr{\n" +
        "  border-bottom: 1px solid #dddddd;\n" +
        "}\n" +
        "\n" +
        "tr:last-of-type{\n" +
        "  border-bottom: 2px solid #009879;\n" +
        "}\n" +
        "\n" +
        "tr:nth-of-type(1) td{\n" +
        "  background-color:#d0d0d0;\n" +
        "} \n" +
        "\n" +
        "</style>"

@Composable
fun EventContentPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
) {
    val hazeState = remember { HazeState() }

    val eventId = backStackEntry.query<Int>("eventId")

    val eventItem = EventListInstance.filter { it.ann_id == eventId }[0]

    val htmlData = (headerHTML + "<body>" +
            "<div class=\"title\">${eventItem.title}</div>" +
            (eventItem.content
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
                eventImageDisplay(eventItem)
            }
            item {
                WebView(webViewState, modifier = Modifier.padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING))

                Spacer(Modifier.navigationBarsPadding())
            }
        }

        PageHeader(
            navigator = navigator,
            headerData = HeaderData(title = eventItem.title, titleIconId = Res.drawable.phorphos_film_slate_fill),
            hazeState = hazeState,
            backIconId = BackIcon.BACK,
        )

    }
}

@Composable
fun eventImageDisplay(eventItem: EventItem){
    val context = LocalPlatformContext.current
    val imageLoader = remember {
        UtilTools().newImageLoader(context = context)
    }
    val imageRequest =  remember {
        ImageRequest.Builder(context)
            .data(eventItem.banner)
            .networkCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

    }

    Row{
        AsyncImage(
            imageLoader = imageLoader,
            model = imageRequest,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
}