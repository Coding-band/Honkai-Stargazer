/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import files.Res
import files.bg_transparent
import files.phorphos_sun_fill
import files.ui_icon_back
import files.ui_icon_close
import files.ui_icon_share
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource

val PAGE_HEADER_HEIGHT = 72.dp
val PAGE_HEADER_ALPHA_HEIGHT = 64.dp
val defaultHeaderData = HeaderData(title = "?", titleIconId = Res.drawable.phorphos_sun_fill)

enum class BackIcon(var res: DrawableResource) {
    BACK(res = Res.drawable.ui_icon_back),
    CANCEL(res = Res.drawable.ui_icon_close),
    //... More Icon will be added later
}

class HeaderData(
    var title: String? = null,
    var titleRId: StringResource? = null,
    var titleIconId: DrawableResource = Res.drawable.phorphos_sun_fill,
    var otherData: Any? = null //But seem unable to use...
)

@Composable
fun PageHeader(
    navigator: Navigator = rememberNavigator(),
    onBack: ((navigator : Navigator) -> Unit) = { navigator: Navigator -> navigator.popBackStack() },
    backIconId: BackIcon = BackIcon.BACK,
    onForward: (() -> Unit) = {},
    forwardIconId: DrawableResource = Res.drawable.bg_transparent,
    headerData: HeaderData = defaultHeaderData,
    hazeState: HazeState? = HazeState()
) {
    //Background
    DropShadow(
        color = Color.Black.copy(alpha = 0.5f),
        offset = DpOffset(0.dp, 4.dp),
        radius = 4.dp
    ) {
        //BlurView can place in there
        //Now will use Pure Color Background
        Box(
            Modifier
                .hazeChild(
                    state = hazeState!!,
                    style = HazeStyle(Color.Unspecified, 10.dp, 0f)
                )
                .background(Color(0x33FFFFFF))
                //.clippedShadow(elevation = 2.dp)
                .statusBarsPadding()
                .requiredHeight(PAGE_HEADER_HEIGHT)


        ){
            Column {
                Row(
                    Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    OutlinedButton(
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(0.dp, Color(0x00FFFFFF)),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        onClick = { onBack(navigator) },
                    ) {
                        Image(
                            painter = painterResource(resource = backIconId.res),
                            contentDescription = "Back Icon",
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.White),

                            )
                    }
                    Box(Modifier.weight(1f)){
                        TitleHeader(headerData.titleIconId,headerData.title,headerData.titleRId)
                    }
                    OutlinedButton(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        onClick = { onForward },
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(0.dp, Color(0x00FFFFFF)),
                        shape = CircleShape,
                    ) {
                        Image(
                            painter = painterResource(resource = forwardIconId),
                            contentDescription = "Forward Icon",
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.White),

                            )
                    }
                }
            }
        }

    }
}

@Composable
fun PageHeaderAlpha(
    navigator: Navigator = rememberNavigator(),
    onBack: ((navigator: Navigator) -> Unit) = { navigator: Navigator -> navigator.popBackStack() },
    backIconId: BackIcon = BackIcon.CANCEL,
    onForward: (() -> Unit) = {},
    hazeState: HazeState? = HazeState(),
    forwardIconId: DrawableResource = Res.drawable.ui_icon_share,
    isListScrolling: Boolean = false,
    components: @Composable () -> Unit = {}
){
    //Background
    Box(
        Modifier
            .statusBarsPadding()
            .requiredHeight(PAGE_HEADER_ALPHA_HEIGHT)
    ) {
        Column {
            Row(
                Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxSize()
                    .weight(1f)
            ) {
                OutlinedButton(
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0x33FFFFFF)),
                    border = BorderStroke(0.dp, Color(0x00FFFFFF)),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically),
                    onClick = { onBack(navigator) },
                ) {
                    Image(
                        painter = painterResource(resource = backIconId.res),
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .hazeChild(
                                shape = CircleShape,
                                state = hazeState!!,
                                style = HazeStyle(Color.Unspecified, 10.dp, 0f)
                            )
                            .align(Alignment.CenterVertically),
                        colorFilter = ColorFilter.tint(Color.White),

                        )
                }
                Box(Modifier.weight(1f)){
                    components()
                }
                OutlinedButton(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically),
                    onClick = { onForward() },
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0x33FFFFFF)),
                    border = BorderStroke(0.dp, Color(0x00FFFFFF)),
                    shape = CircleShape,
                ) {
                    Image(
                        painter = painterResource(resource = forwardIconId),
                        contentDescription = "Forward Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically)
                            .hazeChild(
                                shape = CircleShape,
                                state = hazeState!!,
                                style = HazeStyle(Color.Unspecified, 10.dp, 0f)
                            ),
                        colorFilter = ColorFilter.tint(Color.White),

                        )
                }
            }
        }

    }
}
