

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.function.SplashPage.SplashPage
import ui.navigation.NavigationInit
import utils.app.Stargazer3Theme
import coil3.compose.setSingletonImageLoaderFactory
import com.voc.honkaistargazer.BuildKonfig
import utils.app.newImageLoader

@Composable
@Preview
        /**
         * - The door of the app, only should put things that need to initize at the beginning of the app start
         * - Handling and deciding which display case it is (Horizonal / Landscape) , (Pad Mode ? Phone Mode?)
         * - Display specific screen as the login in Figma Design expect
         */
fun App() {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(
            context,
            arrayOf("C.BETA","BETA","DEV").contains(BuildKonfig.appProfile)
        )
    }

    Stargazer3Theme(
        darkTheme = true
    ){
        PreComposeApp {
            NavigationInit()
        }
    }
}
