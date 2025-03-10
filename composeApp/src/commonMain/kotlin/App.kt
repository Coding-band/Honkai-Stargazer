

import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import com.voc.stargazer3.BuildKonfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.userAccountErrorMessage
import ui.components.PomPomInit
import ui.navigation.RootContent
import utils.app.Language
import utils.app.LogExportInit
import utils.app.Stargazer3Theme
import utils.app.dateTimeStrInit
import utils.app.newImageLoader
import utils.app.updateAssetsInit

lateinit var platformContext: ContextFactory

@Composable
@Preview
        /**
         * - The door of the app, only should put things that need to initize at the beginning of the app start
         * - Handling and deciding which display case it is (Horizonal / Landscape) , (Pad Mode ? Phone Mode?)
         * - Display specific screen as the login in Figma Design expect
         */
fun App(platformContextFactory: ContextFactory) {
    platformContext = platformContextFactory
    setSingletonImageLoaderFactory { context ->
        newImageLoader(
            context,
            arrayOf("DEV").contains(BuildKonfig.appProfile)
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
        userAccountErrorMessage()
        updateAssetsInit()

        RootContent()
    }
}
