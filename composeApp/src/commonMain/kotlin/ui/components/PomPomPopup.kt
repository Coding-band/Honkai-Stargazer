package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import files.AppStatusLoading
import files.Res
import files.pom_pom_praying
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ui.navigation.hazeStateRoot
import utils.app.DialogPopUpZIndex
import utils.app.FontSizeNormal16
import utils.app.HazeBlurDp10
import utils.app.hazeEffectSG3
import utils.app.pxToDp
import utils.app.removeStrQuote

lateinit var docCountDown : MutableState<Int>
lateinit var pomPomPopupInstance: MutableState<PomPomPopup>

data class PomPomPopup(
    var isDisplay: Boolean = false,
)

@Composable
fun PomPomInit() {
    pomPomPopupInstance = remember { mutableStateOf(PomPomPopup()) }
}

@Composable
fun PomPomPopupUI(
    hazeState: HazeState = hazeStateRoot
) {

    docCountDown = remember { mutableStateOf(1) }
    val uiHeight: MutableState<Dp> = remember { mutableStateOf(1.dp) }
    val density = LocalDensity.current.density

    LaunchedEffect(pomPomPopupInstance.value.isDisplay) {
        while (pomPomPopupInstance.value.isDisplay) {
            if (docCountDown.value == 3) {
                docCountDown.value = 1
            } else {
                docCountDown.value += 1
            }
            delay(1000)
        }
    }
    // Display the popup
    if (pomPomPopupInstance.value.isDisplay) {

        //Full-Screen
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Background
            Box(
                modifier = Modifier

                    .fillMaxWidth(0.5f)
                    .height(uiHeight.value)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(25.dp))
                    .hazeSource(state = hazeState, zIndex = DialogPopUpZIndex)
                    .hazeEffectSG3(
                        state = hazeState,
                        style = HazeBlurDp10,
                    )
                    .background(Color(0x66AAAAAA))
                    /*
                    .hazeChild(
                        hazeState,
                        style = HazeStyle(Color(0x66AAAAAA), null, 10.dp, 0f),
                        //shape = RoundedCornerShape(25.dp)
                    )
                 */
            )

            // Popup
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .wrapContentHeight()
                    .align(Alignment.Center)
                    .onSizeChanged {
                        uiHeight.value = pxToDp(it.height, density)
                    }
            ) {
                // Display the content of the popup
                Column(Modifier.padding(24.dp)) {
                    Image(
                        painter = painterResource(Res.drawable.pom_pom_praying),
                        contentDescription = "Pom Pom",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .defaultMinSize(128.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${removeStrQuote(Res.string.AppStatusLoading)}${".".repeat(docCountDown.value)}",
                        color = Color.White,
                        style = FontSizeNormal16(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

}