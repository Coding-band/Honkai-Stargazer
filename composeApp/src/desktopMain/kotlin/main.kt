import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import coil3.size.Dimension
import com.russhwolf.settings.Settings
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBuilder
import files.Res
import files.app_icon
import files.app_icon_black_bg
import files.app_name
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        if(!Settings().hasKey("isJCEFInited")) {Settings().putBoolean("isJCEFInited", false)}
        App(ContextFactory()) //Since Desktop does not have Context

        LaunchedEffect(Unit){
            if(!Settings().getBoolean("isJCEFInited", false)) return@LaunchedEffect
            withContext(Dispatchers.IO) {
                KCEF.init(builder = {
                    installDir(File("kcef-bundle"))
                })
            }
        }
    }
}