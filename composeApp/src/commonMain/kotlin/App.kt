

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.Stargazer3Theme
import utils.navigation.Navigation

@Composable
@Preview
        /**
         * - The door of the app, only should put things that need to initize at the beginning of the app start
         * - Handling and deciding which display case it is (Horizonal / Landscape) , (Pad Mode ? Phone Mode?)
         * - Display specific screen as the login in Figma Design expect
         */
fun App() {
    Stargazer3Theme(
        darkTheme = true
    ){
        PreComposeApp {
            Navigation()
        }
    }
}
