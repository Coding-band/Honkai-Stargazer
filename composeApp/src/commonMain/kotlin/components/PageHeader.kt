/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import files.AppStatusLostConnect
import files.Res
import files.bg_transparent
import files.phorphos_sun_fill
import files.ui_icon_back
import files.ui_icon_close
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.FontSizeNormal
import utils.TextColorNormal

val PAGE_HEADER_HEIGHT = 64.dp
val defaultHeaderData = HeaderData(title = "?", titleIconId = Res.drawable.phorphos_sun_fill)

enum class BackIcon(var res: DrawableResource) {
    BACK(res = Res.drawable.ui_icon_back),
    CANCEL(res = Res.drawable.ui_icon_close)
}

class HeaderData(
    var title: String? = null,
    var titleRId: StringResource? = null,
    var titleIconId: DrawableResource = Res.drawable.phorphos_sun_fill,
    var otherData : Any? = null //But seem unable to use...
)

@Composable
fun PageHeader(
    navController: NavController = rememberNavController(),
    onBack: ((navController: NavController) -> Unit) = { navController: NavController -> navController.popBackStack() },
    backIconId: BackIcon = BackIcon.BACK,
    onForward: (() -> Unit) = {},
    forwardIconId: DrawableResource = Res.drawable.bg_transparent,
    headerData: HeaderData = defaultHeaderData,
    hazeState: HazeState? = HazeState()
) {
    //Background
    Box(
        Modifier
            .hazeChild(
                state = hazeState!!,
                style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE)
            )
            .background(Color(0x66FFFFFF))
            //.clippedShadow(elevation = 2.dp)
            .statusBarsPadding()
            .requiredHeight(PAGE_HEADER_HEIGHT)

    ) {
        //BlurView can place in there
        //Now will use Pure Color Background
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
                    onClick = { onBack(navController) },
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
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(resource = headerData.titleIconId),
                        contentDescription = "Title Icon",
                        modifier = Modifier.size(32.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Row {
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .width(50.dp)
                                .background(Color(0x66FFFFFF))
                                .align(Alignment.CenterVertically),
                        )
                        Text(
                            text =
                            if (headerData.title === null) {
                                if (headerData.titleRId === null)
                                    stringResource(Res.string.AppStatusLostConnect).removePrefix("\"").removeSuffix("\"")
                                else (stringResource((headerData.titleRId!!)).removePrefix("\"").removeSuffix("\""))
                            }
                            else headerData.title!!,
                            color = TextColorNormal,
                            style = FontSizeNormal(),
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .width(50.dp)
                                .background(Color(0x66FFFFFF))
                                .align(Alignment.CenterVertically),
                        )
                    }

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

@Preview()
@Composable
fun PageHeaderPreview() {
    PageHeader(rememberNavController())
}