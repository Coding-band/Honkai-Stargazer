package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    components: () -> Unit,
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    isVisible: Boolean
){
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.wrapContentSize().navigationBarsPadding().padding(bottom = 4.dp)
    ) {
        //Dialog
        Box(modifier = modifier
            .padding(20.dp)
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
                style = HazeStyle(Color.Unspecified, 20.dp, Float.MIN_VALUE),
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
                    Icon(
                        painter = painterResource(Res.drawable.ui_icon_close),
                        contentDescription = "Press to Close Dialog",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.wrapContentWidth().height(2.dp)
                        .padding(top = 2.dp, bottom = 2.dp).background(Color(0x0F000000))
                )

                components()
            }
        }
    }
}