

import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import com.voc.stargazer3.BuildKonfig
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.PomPomInit
import ui.navigation.RootContent
import ui.navigation.Screen
import ui.screens.SplashPage
import utils.app.Language
import utils.app.LogExportInit
import utils.app.Stargazer3Theme
import utils.app.dateTimeStrInit
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
        /**
         * Here's the logic of the app
         * Start -> SplashNavInit() -> SplashPage --`Wait for Popup`--> RootContent() -> HomePage || BlankPage
         */

        Language().setAppLanguage()
        LogExportInit()
        PomPomInit()
        dateTimeStrInit()

        PreComposeApp {
            SplashNavInit()
        }
    }
}

/**
 * Gate to SplashPage, and the navigator used in there is temporately.
 */
@Composable
fun SplashNavInit(){
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        initialRoute = Screen.SplashPage.route
    ) {
        scene(route = Screen.SplashPage.route) {
            SplashPage(navigator = navigator)
        }
        scene(route = Screen.RootPage.route) {
            RootContent()
        }
    }
}