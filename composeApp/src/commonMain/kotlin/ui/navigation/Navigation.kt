package ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import getOrientation
import getScreenSizeInfo
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.components.HeaderData
import ui.components.defaultHeaderData
import ui.function.characterListPage.CharacterListPage
import ui.function.homePage.HomePage
import ui.function.lightconeListPage.LightconeListPage
import ui.function.relicListPage.RelicListPage
import utils.app.BezierEasing2O48
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
//lateinit var pomPomPopupInstance: MutableState<PomPomPopup>
lateinit var swipeProperties: SwipeProperties
lateinit var navTransition : NavTransition
var screenInstance : Screen = Screen.BlankPage

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
        WindowWidthSizeClass.COMPACT -> {
            false
        }
        WindowWidthSizeClass.MEDIUM -> {
            getOrientation() == Orientation.Horizontal
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

/**
 * Root Frame of the app.
 */
@Composable
fun RootContent() {
    val snackbarHostState = remember { SnackbarHostState() }
    val hazeStateRoot = remember { HazeState() }
    val isPadMode = mutableStateOf(isPadMode())
    val isRotate = remember { mutableStateOf(false) }
    val navigator = rememberNavigator()
    navigatorInstance = navigator
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

    key(isRotate.value) {
        globalWindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        isPadMode.value = isPadMode()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState!!) },
    ) {

        AnimatedContent(
            targetState = isPadMode.value,
            //Transition should fade in and out, also must make sure NO BLACK SCREEN
            transitionSpec = {
                fadeIn(tween(500)) togetherWith fadeOut(tween(1000))
            },
            modifier = Modifier.onSizeChanged { isRotate.value = !isRotate.value }
        ) {
            if(it) {
                MakeBackground(screen = screenInstance, forceBlur = true)
            }else{
                MakeBackground(screen = screenInstance)
            }
        }

        if(isPadMode.value){
            Row(modifier = Modifier.haze(hazeStateRoot)) {
                Box(Modifier
                    .width(HOME_WIDTH)
                    .let { if (getScreenSizeInfo().wDP < HOME_WIDTH * 1.5f) it.weight(1f) else it }
                    .fillMaxHeight()
                ) {
                    HomePage(
                        navigator = navigatorInstance,
                        headerData = Screen.HomePage.headerData
                    )
                }
                Box(Modifier.weight(1f)) {
                    NavHostInit(navigatorInstance, isPadMode)
                }
            }
        }else{
            Box(modifier = Modifier.haze(hazeStateRoot)) {
                NavHostInit(navigatorInstance, isPadMode)
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
fun NavHostInit(navigator : Navigator, isPadMode: MutableState<Boolean>){
    val defaultNavTransition = remember {
        NavTransition(
            createTransition = fadeIn(),
            destroyTransition = fadeOut(),
            exitTargetContentZIndex = 0f
        )
    }
    NavHost(
        navigator = navigator,
        swipeProperties = if(isPadMode.value) null else swipeProperties,
        navTransition = if (isPadMode.value) defaultNavTransition else navTransition,
        initialRoute = if(isPadMode.value) Screen.BlankPage.route else Screen.HomePage.route
    ) {
        scene(route = Screen.BlankPage.route) {
            screenInstance = Screen.BlankPage
            withBGScreen(isPadMode){
                BlankPage(
                    navigator = navigator,
                    headerData = defaultHeaderData
                )
            }
        }
        scene(route = Screen.HomePage.route) {
            screenInstance = Screen.HomePage
            withBGScreen(isPadMode){
                HomePage(
                    navigator = navigator,
                    headerData = Screen.HomePage.headerData
                )
            }
        }

        scene(route = Screen.CharacterListPage.route) {
            screenInstance = Screen.CharacterListPage
            withBGScreen(isPadMode){
                CharacterListPage(
                    navigator = navigator,
                    headerData = Screen.CharacterListPage.headerData
                )
            }
        }
        scene(route = Screen.LightconeListPage.route) {
            screenInstance = Screen.LightconeListPage
            withBGScreen(isPadMode){
                LightconeListPage(
                    navigator = navigator,
                    headerData = Screen.LightconeListPage.headerData
                )
            }
        }
        scene(route = Screen.RelicListPage.route) {
            screenInstance = Screen.RelicListPage
            withBGScreen(isPadMode){
                RelicListPage(
                    navigator = navigator,
                    headerData = Screen.RelicListPage.headerData
                )
            }
        }

    }
}

@Composable
fun withBGScreen(isPadMode: MutableState<Boolean>, content: @Composable () -> Unit){
    Box{
        if(!isPadMode.value){
            MakeBackground(screen = screenInstance)
        }
        content()
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


@Composable
fun BlankPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxHeight()){

    }
}