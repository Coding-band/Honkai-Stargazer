package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import files.Res
import files.ui_icon_close
import org.jetbrains.compose.resources.painterResource
import utils.FontSizeNormal20


@Composable
fun InfoDisplayDialog(
    titleString: String,
    components: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isNavBarVisible: Boolean = true,
    isDialogVisible: MutableState<Boolean>
){
    AnimatedVisibility(
        visible = isNavBarVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.wrapContentSize().navigationBarsPadding()
    ) {
        AppDialog(
            titleString = titleString,
            components = components,
            modifier = Modifier.fillMaxWidth(),
            hazeState = hazeState,
            isPopupShow = isDialogVisible
        )

    }
}

@Composable
fun AppDialog(
    titleString: String,
    components: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isPopupShow: MutableState<Boolean>
) {
    if(isPopupShow.value) {
        //Dialog
        Box(modifier = modifier
            .padding(20.dp)
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomStart = 4.dp,
                    bottomEnd = 4.dp
                )
            )
            .background(Color(0xCCF3F9FF))
            .hazeChild(
                hazeState,
                style = HazeStyle(Color.Unspecified, if(isPopupShow.value) 20.dp else 0.dp, Float.MIN_VALUE),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomStart = 4.dp,
                    bottomEnd = 4.dp
                )
            )
            .clickable(
                enabled = true,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {}
            )
        ) {
            //Inner padding
            Column(modifier = Modifier.padding(16.dp)) {
                //Title & Exit Button
                Row {
                    Text(
                        titleString,
                        style = FontSizeNormal20(),
                        color = Color.Black,
                        maxLines = 1,
                        modifier = Modifier.align(Alignment.CenterVertically).weight(1f)
                    )
                    OutlinedButton(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        onClick = { isPopupShow.value = false },
                        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(0.dp, Color(0x00FFFFFF)),
                        shape = CircleShape,
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ui_icon_close),
                            contentDescription = "Press to Close Dialog",
                            modifier = Modifier.size(40.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF222222))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(2.dp).background(Color(0x0F000000))
                )
                Spacer(modifier = Modifier.height(6.dp))

                components()
            }
        }
    }
}