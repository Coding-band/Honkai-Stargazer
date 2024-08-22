package screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.HeaderData
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.Res
import files.app_icon_black_bg
import files.euclid_circular_a_medium
import files.star_peace_icon
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import types.UserAbyssRecord.Companion.refreshMOCData
import types.UserAbyssRecord.Companion.refreshPFData
import types.UserAccount.Companion.INSTANCE
import types.UserAccount.Companion.refreshCharacterList
import types.UserAccount.Companion.refreshNoteData
import utils.FontSizeNormalLarge24
import utils.FontSizeNormalSmall
import utils.Language
import utils.Preferences
import utils.initToastStr
import utils.navigation.Screen
import utils.navigation.navigateLimited


@Preview
@Composable
fun SplashPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
) {

    Language().setAppLanguage()

    val hazeStateRoot = remember { HazeState() }
    val showPopup = remember { mutableStateOf(!Preferences().AppSettings.isLangInitialized()) }

    val hasRefreshed = remember { mutableStateOf(false) }
    LaunchedEffect(showPopup.value) {
        if (!showPopup.value) {
            CoroutineScope(Dispatchers.Default).launch {
                if (INSTANCE.uid != "000000000" && !hasRefreshed.value) {
                    async { refreshCharacterList() }.await()
                    async { refreshNoteData() }.await()
                    async { refreshMOCData() }.await()
                    async { refreshPFData() }.await()
                }

                hasRefreshed.value = true

                withContext(Dispatchers.Main) {
                    if(INSTANCE.uid != "000000000" && !hasRefreshed.value ){
                        Preferences().Leaderboard.updatedLeaderboard()
                    }
                    if (!showPopup.value) {
                        navigator.navigateLimited(Screen.HomePage.route, options = NavOptions(popUpTo = PopUpTo(Screen.SplashPage.route, true)))
                    }
                }
            }
        }
    }

    initToastStr()

    //Root Container of this page
    Column(
        modifier = Modifier
            .fillMaxSize()
            .haze(hazeStateRoot)
            .background(Color.Black)
    ) {
        //Container of App Icon & Ads
        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
        ) {

            Image(
                painter = painterResource(Res.drawable.app_icon_black_bg),
                modifier = Modifier
                    .padding(bottom = getScreenSizeInfo().hDP.times(0.15f))
                    .size(134.dp, 134.dp)
                    .align(Alignment.Center),
                contentDescription = "App Icon In Splash Page"
            )

            //ads
            /*

            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                Image(
                    painter = painterResource(Res.drawable.donate_ad_bg),
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.Center)
                        .padding(bottom = 12.dp),
                    contentDescription = "App Icon In Splash Page",
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "3s",
                        fontSize = FontSizeNormal().fontSize,
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 24.dp,
                                    topStart = 24.dp,
                                    bottomEnd = 24.dp,
                                    bottomStart = 24.dp
                                )
                            )
                            .background(Color(0x4d000000))
                            .padding(top = 8.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)


                    )
                }


                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Skip",
                        fontSize = FontSizeNormal().fontSize,
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 24.dp,
                                    topStart = 24.dp,
                                    bottomEnd = 24.dp,
                                    bottomStart = 24.dp
                                )
                            )
                            .background(Color(0x4d000000))
                            .padding(top = 8.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)


                    )
                }
            }
             */
        }

        //Container of StarPeace Icon & Name
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


    Language().initAppLanguagePopup(showPopup,hazeState = hazeStateRoot)
}
