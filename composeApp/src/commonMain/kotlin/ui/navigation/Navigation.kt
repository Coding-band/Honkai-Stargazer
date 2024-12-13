package ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState
import com.dokar.sonner.rememberToasterState
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import getScreenSizeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.function.HomePage.HomePage
import ui.function.SplashPage.SplashPage
import utils.app.BezierEasing2O48
import utils.app.Constants
import utils.app.Constants.Companion.HOME_WIDTH
import utils.app.MakeBackground

/**
 * Navigate to a route with a limited interval.
 *  - The interval is 1 second.
 * @param route The route to navigate to.
 * @param options The navigation options.
 */
//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navigatorInstance : Navigator
lateinit var toastInstance : ToasterState
//lateinit var pomPomPopupInstance: MutableState<PomPomPopup>
lateinit var swipeProperties: SwipeProperties
lateinit var navTransition : NavTransition

var globalWindowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.COMPACT

/**
 * Check if the current window is in Pad mode.
 * - Pad mode is when the window is in landscape mode, also not phone.
 * @return True if the window is in Pad mode, false otherwise.
 */
@Composable
fun isPadMode(): Boolean {
    return when (globalWindowWidthSizeClass) {
        //Phone, Pad that is not in landscape mode
        WindowWidthSizeClass.COMPACT, WindowWidthSizeClass.MEDIUM -> {
            false
        }
        //Pad in landscape mode
        WindowWidthSizeClass.EXPANDED -> {
            true
        }
        else -> {
            false
        }
    }
}

@Composable
fun NavigationInit(){
    val navigator = rememberNavigator()
    val snackbarHostState = remember { SnackbarHostState() }
    navigatorInstance = navigator
    toastInstance = rememberToasterState()
    //pomPomPopupInstance = remember { mutableStateOf(PomPomPopup()) }
    //docCountDown = remember { mutableStateOf(0) }
    swipeProperties = remember { SwipeProperties(
        positionalThreshold = { distance -> distance * 0.5f },
        velocityThreshold = { 10.dp.toPx() }
    ) }

    navTransition = remember {
        NavTransition(
            createTransition = slideInHorizontally(animationSpec = tween(easing = BezierEasing2O48)) { it },
            destroyTransition = slideOutHorizontally(animationSpec = tween(easing = BezierEasing2O48)) { it },
            pauseTransition = slideOutHorizontally { -it / 4 },
            resumeTransition = slideInHorizontally { -it / 4 },
            exitTargetContentZIndex = 1f
        )
    }

    NavHost(
        navigator = navigator,
        swipeProperties = swipeProperties,
        navTransition = navTransition,
        initialRoute = Screen.SplashPage.route
    ) {
        scene(route = Screen.SplashPage.route, navTransition = navTransition) {
            SplashPage(navigator = navigator, headerData = Screen.HomePage.headerData)
        }

        scene(route = Screen.HomePage.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.HomePage,
                snackbarHostState = snackbarHostState,
                page = {
                    HomePage(
                        navigator = navigator,
                        headerData = Screen.HomePage.headerData
                    )
                }
            )
        }

    }
}

/**
 * Navigate to a route with a limited interval.
 */
fun Navigator.navigateLimited(route: String, options: NavOptions? = null) {
    val navigationInterval: Long = 1000 // 1 second
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, options)
        Settings().putLong("lastNavigationTime", currentTime)
    }
}

/**
 * Root Frame of the app.
 */
@Composable
fun RootContent(
    screen: Screen,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
    page: @Composable () -> Unit
) {
    val hazeStateRoot = remember { HazeState() }
    val isPadMode = mutableStateOf(isPadMode())


    key(getScreenSizeInfo().wDP){
        globalWindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        isPadMode.value = isPadMode()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState!!) },
    ) {

        AnimatedVisibility(!isPadMode.value,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ){
            MakeBackground(screen = screen)
            Box(modifier = Modifier.haze(hazeStateRoot)) {
                page()
            }
        }

        // Preload both blur and non-blur background
        AnimatedVisibility(isPadMode.value,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ){
            MakeBackground(screen = screen, forceBlur = true)
            Row (modifier = Modifier.haze(hazeStateRoot)) {
                Box (Modifier
                    .width(HOME_WIDTH)
                    .let { if(getScreenSizeInfo().wDP < HOME_WIDTH*2) it.weight(1f) else it }
                    .fillMaxHeight()
                ) {
                    HomePage(
                        navigator = navigatorInstance,
                        headerData = Screen.HomePage.headerData
                    )
                }
                Box(Modifier.weight(1f)) {
                    if(screen == Screen.HomePage){ BlankPage() } else page()
                }
            }
        }


        /*
        PomPomPopupUI(hazeState = hazeStateRoot)

        Toaster(
            state = toastInstance,
            richColors = true,
            maxVisibleToasts = 10,
            alignment = Alignment.BottomCenter,
            showCloseButton = true,
            darkTheme = true,
        )
         */
    }
}

@Composable
fun BlankPage(){
    Box(modifier = Modifier.fillMaxHeight().fillMaxHeight())
}