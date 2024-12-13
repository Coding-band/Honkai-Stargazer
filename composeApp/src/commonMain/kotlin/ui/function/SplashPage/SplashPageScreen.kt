package ui.function.SplashPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import files.Res
import files.app_icon_black_bg
import files.euclid_circular_a_medium
import files.star_peace_icon
import getScreenSizeInfo
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import ui.components.HeaderData
import ui.components.defaultHeaderData
import utils.app.FontSizeNormalLarge24
import utils.app.FontSizeNormalSmall
import utils.app.Language

/**
 * The door of the app, only should put things that need to initize at the beginning of the app start
 * No matter isPadMode = t/f
 */
@Composable
fun SplashPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    viewModel: SplashPageViewModel = SplashPageViewModel(navigator = navigator)
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleIntent(SplashPageIntent.Initialize)
        if(!state.showPopup.value) {
            viewModel.handleIntent(SplashPageIntent.RefreshData)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(Res.drawable.app_icon_black_bg),
                modifier = Modifier
                    .size(134.dp, 134.dp)
                    .align(Alignment.Center),
                contentDescription = "App Icon In Splash Page"
            )
        }

        Row(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .navigationBarsPadding()
                .align(Alignment.CenterHorizontally)
        ) {
            Column {
                Text(
                    text = "Powered by",
                    style = FontSizeNormalSmall(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "starpeace",
                    style = FontSizeNormalLarge24(),
                    fontFamily = FontFamily(
                        Font(
                            Res.font.euclid_circular_a_medium,
                            weight = FontWeight.Medium
                        )
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
            Image(
                painter = painterResource(Res.drawable.star_peace_icon),
                modifier = Modifier
                    .size(40.dp, 40.dp),
                contentDescription = "StarPeace Icon In Splash Page"
            )
        }
    }

    Language().initAppLanguagePopup(state.showPopup,hazeState = state.hazeState)
}