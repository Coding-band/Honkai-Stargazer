import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import coil3.size.Dimension
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBuilder
import files.Res
import files.app_icon
import files.app_icon_black_bg
import files.app_name
import io.ktor.websocket.Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import utils.annotation.DoItLater
import utils.app.removeStrQuote
import java.io.File
import kotlin.math.max


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = removeStrQuote(Res.string.app_name),
        icon = painterResource(Res.drawable.app_icon),
        state = WindowState(width = 1280.dp, height = 720.dp)
    ) {

        @DoItLater("JCEF, do it later")
        App(ContextFactory()) //Since Desktop does not have Context
        /* JCEF, do it later
        var restartRequired by remember { mutableStateOf(false) }
        var downloading by remember { mutableStateOf(0F) }
        var initialized by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                KCEF.init(builder = {
                    installDir(File("kcef-bundle"))
                    progress {
                        onDownloading {
                            downloading = max(it, 0F)
                        }
                        onInitialized {
                            initialized = true
                        }
                    }
                    settings {
                        cachePath = File("cache").absolutePath
                    }
                }, onError = {
                    it?.printStackTrace()
                }, onRestartRequired = {
                    restartRequired = true
                })
            }
        }

        if (restartRequired) {
            Frame.Text(text = "Restart required.")
        } else {
            if (initialized) {
                App()
            } else {
                Frame.Text(text = "Downloading $downloading%")
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                KCEF.disposeBlocking()
            }
        }

         */

    }

}