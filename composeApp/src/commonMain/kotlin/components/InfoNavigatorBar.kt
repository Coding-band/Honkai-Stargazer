package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.FontSizeNormal12
import utils.UtilTools
import kotlin.math.min

data class InfoNavigateItem(
    val itemIcon: DrawableResource, val itemPosIndex: Int, val itemTitle: StringResource
)


@Preview
@Composable
fun InfoNavigatorBar(
    infoItemList: Array<InfoNavigateItem>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    hazeState: HazeState = remember { HazeState() },
    isVisible: Boolean = false,
    offSet: Dp = 0.dp
) {
    var currChoiceIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current.density
    val animationDuration = 1000L

    //不要忘了目录的提示默认应该是不显示的
    //显示的情况：滚动页面时显示，点击目录图标时显示
    var isHintVisible by remember { mutableStateOf(false) }
    var lastExpectInvisibleMS by remember { mutableStateOf(Clock.System.now().toEpochMilliseconds()) }

    LaunchedEffect(listState.firstVisibleItemIndex){
        if(currChoiceIndex != listState.firstVisibleItemIndex){
            isHintVisible = true
            lastExpectInvisibleMS = Clock.System.now().toEpochMilliseconds() + animationDuration
            coroutineScope.launch {
                delay(animationDuration)
                isHintVisible = !(Clock.System.now().toEpochMilliseconds() >= lastExpectInvisibleMS)
            }
        }
        currChoiceIndex = min(listState.firstVisibleItemIndex, infoItemList.size-1)
    }


    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.wrapContentSize().navigationBarsPadding()
    ) {
        Column(modifier = modifier.wrapContentSize()) {
            //Text of your choice
            androidx.compose.animation.AnimatedVisibility(
                visible = isHintVisible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally)
                        .background(Color(0xCC222222), shape = RoundedCornerShape(25.dp))
                        .border(
                            width = 2.dp,
                            color = Color(0xCC3C3C43),
                            shape = RoundedCornerShape(25.dp)
                        ).hazeChild(
                            hazeState,
                            style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE),
                            shape = RoundedCornerShape(25.dp)
                        ).clickable(indication = null, onClick = {}, interactionSource = remember { MutableInteractionSource() })
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                        ),
                        text = UtilTools().removeStringResDoubleQuotes(infoItemList[currChoiceIndex].itemTitle),
                        style = FontSizeNormal12(),
                        color = Color.White
                    )
                }
            }

            //Blank
            Box(modifier = Modifier.height(10.dp))

            //Button choices
            Box(modifier = Modifier.fillMaxWidth().clickable(indication = null, onClick = {}, interactionSource = remember { MutableInteractionSource() })) {
                Box(
                    modifier = Modifier.align(Alignment.Center)
                        .background(Color(0xCC222222), shape = RoundedCornerShape(25.dp))
                        .border(
                            width = 2.dp,
                            color = Color(0xCC3C3C43),
                            shape = RoundedCornerShape(25.dp)
                        ).hazeChild(
                            hazeState,
                            style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE),
                            shape = RoundedCornerShape(25.dp)
                        )
                ) {
                    Row(modifier = Modifier.padding(6.dp)) {
                        for ((index, item) in infoItemList.withIndex()) {
                            val startPadding = if (index == 0) 0.dp else 4.dp
                            val endPadding = if (index == infoItemList.size - 1) 0.dp else 4.dp
                            Box(
                                modifier = Modifier.padding(
                                    start = startPadding,
                                    end = endPadding
                                )
                                    .size(30.dp).background(
                                        Color(if (currChoiceIndex == index) 0x33FFFFFF else 0x00FFFFFF),
                                        shape = RoundedCornerShape(25.dp)
                                    ).clickable(
                                        onClick = {
                                            currChoiceIndex = index;
                                            coroutineScope.launch {
                                                listState.animateScrollToItem(index = item.itemPosIndex, scrollOffset = -UtilTools().DpToPx(offSet + 4.dp,density = density))
                                                delay(animationDuration)
                                                isHintVisible = Clock.System.now().toEpochMilliseconds() < lastExpectInvisibleMS

                                            }
                                        },
                                        indication = rememberRipple(),
                                        interactionSource = MutableInteractionSource()
                                    )
                            ) {
                                Image(
                                    painter = painterResource(item.itemIcon),
                                    contentDescription = "Navigator Item Icon",
                                    colorFilter = ColorFilter.tint(Color(if (currChoiceIndex == index) 0xFFFFFFFF else 0x99FFFFFF)),
                                    modifier = Modifier.size(24.dp).align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}