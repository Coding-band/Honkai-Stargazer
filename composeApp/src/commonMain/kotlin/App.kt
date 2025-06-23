

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import coil3.compose.setSingletonImageLoaderFactory
import com.voc.stargazer3.BuildKonfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.userAccountErrorMessage
import ui.components.PomPomInit
import ui.navigation.RootContent
import utils.app.Language
import utils.app.Language.Companion.AppLanguageInstance
import utils.app.LogExportInit
import utils.app.Stargazer3Theme
import utils.app.dateTimeStrInit
import utils.app.kcefPopUpInit
import utils.app.newImageLoader
import utils.app.updateAssetsInit

lateinit var platformContext: ContextFactory
lateinit var globalDensity: Density
lateinit var appLanguageState : MutableState<Language.AppLanguage>

@Composable
@Preview
        /**
         * - The door of the app, only should put things that need to initialize at the beginning of the app start
         * - Handling and deciding which display case it is (Horizontal / Landscape) , (Pad Mode ? Phone Mode?)
         * - Display specific screen as the login in Figma Design expect
         */
fun App(platformContextFactory: ContextFactory) {
    globalDensity = LocalDensity.current // Not yet applied to the app
    platformContext = platformContextFactory
    setSingletonImageLoaderFactory { context ->
        newImageLoader(
            context,
            arrayOf("DEV").contains(BuildKonfig.appProfile)
        )
    }

    // 用 State 管理語言
    if( !::appLanguageState.isInitialized) {
        appLanguageState = remember { mutableStateOf(AppLanguageInstance) }
    }

    val direction = remember(appLanguageState.value) {
        mutableStateOf(
            if (appLanguageState.value == Language.AppLanguage.ARABIC_HALAL) LayoutDirection.Rtl
            else LayoutDirection.Ltr
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides direction.value) {
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
            kcefPopUpInit()
            RootContent()
        }
    }

}
