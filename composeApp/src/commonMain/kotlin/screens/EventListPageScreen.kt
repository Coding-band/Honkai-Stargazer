package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.russhwolf.settings.Settings
import components.BackIcon
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Res
import files.StatusDays
import files.StatusHours
import files.StatusMinutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import moe.tlaster.precompose.navigation.Navigator
import types.Constants
import types.EventItem
import types.EventItem.Companion.EventListInstance
import utils.UtilTools
import utils.navigation.Screen
import utils.navigation.navigateLimited

@Composable
fun EventListPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
) {
    val isDateOutside = remember { mutableStateOf(Settings().getBoolean("isDateOutside",true)) }
    val hazeState = remember { HazeState() }
    val eventList = EventListInstance.filter { it.end_unix > Clock.System.now().toEpochMilliseconds() }.sortedBy { it.end_unix }

    Box(modifier = modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier
            .haze(state = hazeState)
        ) {
            item {
                Spacer(Modifier.padding(top = PAGE_HEADER_HEIGHT).statusBarsPadding())
            }
            items(eventList.size) { index ->
                EventItemCard(eventList[index], isDateOutside, navigator)
            }
            item {
                Spacer(Modifier.padding(top = 16.dp).navigationBarsPadding())
            }

        }

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
        )
    }
}

@Composable
fun EventItemCard(eventItem: EventItem, isDateOutside: MutableState<Boolean>, navigator: Navigator) {
    val currentTime = Clock.System.now()
    val endTime = LocalDateTime.parse(eventItem.end_time,
        LocalDateTime.Format { date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO) }
    ).toInstant(TimeZone.of("UTC+8"))
    val duration = currentTime.periodUntil(endTime, TimeZone.of("UTC+8"))

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

    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        var isLongClick = false

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(1000)
                    isLongClick = true
                    isDateOutside.value = isDateOutside.value.not()
                }

                is PressInteraction.Release -> {
                    if (isLongClick.not()) {
                        navigator.navigateLimited("${Screen.EventContentPageScreen.route}?eventId=${eventItem.ann_id}")
                    }

                }

            }
        }
    }

    Row(modifier = Modifier
        .padding(top = 6.dp, bottom = 6.dp, start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
    ) {
        if (isDateOutside.value) {
            Text(
                modifier = Modifier.wrapContentSize().background(Color((0xCCF3F9FF)))
                    .padding(4.dp).height(46.dp).width(40.dp),
                text = (
                        if (duration.days > 0) UtilTools().removeStringResDoubleQuotes(Res.string.StatusDays)
                            .replace("$" + "{1}", "${duration.days}\n")
                        else if (duration.hours > 0) UtilTools().removeStringResDoubleQuotes(Res.string.StatusHours)
                            .replace("$" + "{1}", "${duration.hours}\n")
                        else UtilTools().removeStringResDoubleQuotes(Res.string.StatusMinutes)
                            .replace("$" + "{1}", "${duration.minutes}\n")
                        ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Box(modifier = Modifier
            .wrapContentHeight().weight(1f)
            .clickable(interactionSource = interactionSource, indication = rememberRipple(), onClick = {})
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = eventItem.title,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                imageLoader = imageLoader
            )

            if (!isDateOutside.value) {
                Text(
                    modifier = Modifier.wrapContentSize().background(Color((0xCCF3F9FF)))
                        .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
                    text = (
                            if (duration.days > 0) UtilTools().removeStringResDoubleQuotes(Res.string.StatusDays)
                                .replace("$" + "{1}", "${duration.days}")
                            else if (duration.hours > 0) UtilTools().removeStringResDoubleQuotes(Res.string.StatusHours)
                                .replace("$" + "{1}", "${duration.hours}")
                            else UtilTools().removeStringResDoubleQuotes(Res.string.StatusMinutes)
                                .replace("$" + "{1}", "${duration.minutes}")
                            ),
                    color = Color.Black
                )
            }
        }
    }
}