package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import types.EventItem.Companion.EventListInstance

@Composable
fun EventContentPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
) {
    val hazeState = remember { HazeState() }

    val eventId = backStackEntry!!.arguments?.getInt("eventId")!!

    val eventItem = EventListInstance.filter { it.ann_id == eventId }[0]

    var richTextState = rememberRichTextState()
    richTextState.setHtml(eventItem.content)

    Box(modifier = modifier.fillMaxSize()){
        Column {
            Spacer(Modifier.padding(top = PAGE_HEADER_HEIGHT))

            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(eventItem.banner)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = eventItem.title,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                imageLoader = ImageLoader(context = LocalPlatformContext.current)
            )
            RichText(richTextState)
        }

        PageHeader(
            navController = navController,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
        )
    }
}