package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import files.Res
import files.pom_pom_praying
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import utils.FontSizeNormal16
import utils.navigation.pomPomPopupInstance

lateinit var docCountDown : MutableState<Int>

data class PomPomPopup(
    var isDisplay: Boolean = false,
)

@Composable
fun PomPomPopupUI(
    hazeState: HazeState = remember { HazeState() }
) {

    println("instance.isDisplay : ${pomPomPopupInstance.value.isDisplay}")
    val isDisplay = pomPomPopupInstance.value.isDisplay
    // Display the popup
    if (pomPomPopupInstance.value.isDisplay) {
        CoroutineScope(Dispatchers.Default).launch {
            while (pomPomPopupInstance.value.isDisplay) {
                if(docCountDown.value == 3) {
                    docCountDown.value = 1
                } else {
                    docCountDown.value += 1
                }
                delay(1000)
            }
        }
        //Full-Screen
        AnimatedVisibility(
            visible = pomPomPopupInstance.value.isDisplay,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ){
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                //Popup
                Box(
                    Modifier.fillMaxWidth(0.5f).wrapContentHeight().align(Alignment.Center).hazeChild(
                        hazeState,
                        style = HazeStyle(Color(0x66AAAAAA), 10.dp, 0f),
                        shape = RoundedCornerShape(25.dp)
                    )
                ) {
                    // Display the content of the popup
                    Column(Modifier.padding(24.dp)) {
                        Image(
                            painter = painterResource(Res.drawable.pom_pom_praying),
                            contentDescription = "Pom Pom",
                            modifier = Modifier.align(Alignment.CenterHorizontally).defaultMinSize(128.dp).fillMaxWidth().aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Pom Pom is praying${".".repeat(docCountDown.value)}",
                            color = Color.White,
                            style = FontSizeNormal16(),
                            modifier = Modifier.align(Alignment.CenterHorizontally))

                    }
                }
            }
        }
    }

}