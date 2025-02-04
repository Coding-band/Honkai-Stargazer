/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState
import com.dokar.sonner.rememberToasterState
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import getScreenSizeInfo
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.components.HeaderData
import ui.components.PomPomPopup
import ui.components.PomPomPopupUI
import ui.components.defaultHeaderData
import ui.components.docCountDown
import ui.screens.AboutStargazerPageScreen
import ui.screens.BackgroundSettingScreen
import ui.screens.BattleChroniclePageScreen
import ui.screens.CharacterInfoPage
import ui.screens.CharacterListPage
import ui.screens.EventContentPageScreen
import ui.screens.EventListPageScreen
import ui.screens.ExpeditionPageScreen
import ui.screens.HomePage
import ui.screens.HoyolabLoginPageScreen
import ui.screens.LightconeInfoPage
import ui.screens.LightconeListPage
import ui.screens.MakeBackground
import ui.screens.MapPageScreen
import ui.screens.MemoryOfChaosMissionPageScreen
import ui.screens.ProficientLeaderboardPageScreen
import ui.screens.PureFictionMissionPageScreen
import ui.screens.RelicInfoPage
import ui.screens.RelicListPage
import ui.screens.SettingScreen
import ui.screens.SplashPage
import ui.screens.UIDSearchPageScreen
import ui.screens.UserCharacterPageScreen
import ui.screens.UserInfoPageScreen
import utils.app.BezierEasing2O48
import utils.app.Constants.Companion.HOME_WIDTH

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

/**
 * Only usage : For GlobalBackground check whether should blur the background
 *
 * **Warm remind** : popBack will not be able to this.
 */
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
            //Due to the request from our designer 2O48
            //There have to allow Pad in portrait mode of iPad, etc.
            //getOrientation() == Orientation.Horizontal
            true
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
    val isPadMode = remember { mutableStateOf(false) }
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

        Row(modifier = Modifier.haze(hazeStateRoot)) {
            if(isPadMode.value){
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
            }
            Box(Modifier.weight(1f)) {
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
            destroyTransition = fadeOut()
        )
    }

    NavHost(
        navigator = navigator,
        swipeProperties = if(isPadMode.value) null else swipeProperties,
        navTransition = if (isPadMode.value) defaultNavTransition else navTransition,
        initialRoute = Screen.HomePage.route
    ) {
        scene(route = Screen.HomePage.route) {
            screenInstance = Screen.HomePage
            withBGScreen(isPadMode){
                if(!isPadMode.value) {
                    HomePage(
                        navigator = navigator,
                        headerData = Screen.HomePage.headerData
                    )
                }else{
                    BlankPage(
                        navigator = navigator,
                        headerData = Screen.BlankPage.headerData
                    )
                }
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

        scene(
            //?fileName={fileName}&combatType={combatType}&path={path}&charId={charId}
            route = "${Screen.CharacterInfoPage.route}/{charName}",
        ) { backStackEntry ->
            screenInstance = Screen.CharacterInfoPage
            withBGScreen(isPadMode){
                CharacterInfoPage(
                    navigator = navigator,
                    headerData = Screen.CharacterInfoPage.headerData,
                    backStackEntry = backStackEntry
                )
            }
        }

        scene(
            //?fileName={fileName}&path={path}
            route = "${Screen.LightconeInfoPage.route}/{lcName}",
        ) { backStackEntry ->
            screenInstance = Screen.LightconeListPage
            withBGScreen(isPadMode){
                    LightconeInfoPage(
                        navigator = navigator,
                        headerData = Screen.LightconeInfoPage.headerData,
                        backStackEntry = backStackEntry
                    )
                }
        }

        //?fileName={fileName}
        scene(
            route = "${Screen.RelicInfoPage.route}/{relicName}",
        ) { backStackEntry ->
            screenInstance = Screen.RelicInfoPage
            withBGScreen(isPadMode){
                    RelicInfoPage(
                        navigator = navigator,
                        headerData = Screen.RelicInfoPage.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
        }

        scene(
            route = Screen.SettingScreen.route) {
            screenInstance = Screen.SettingScreen
            withBGScreen(isPadMode){
                SettingScreen(
                    navigator = navigator,
                    headerData = Screen.SettingScreen.headerData
                    )
            }
        }

        scene(
            route = Screen.BackgroundSettingScreen.route) {
            screenInstance = Screen.BackgroundSettingScreen
            withBGScreen(isPadMode){
                    BackgroundSettingScreen(
                        navigator = navigator,
                        headerData = Screen.BackgroundSettingScreen.headerData
                    )
                }
        }

        //?serverId={serverId}
        scene(
            route = Screen.HoyolabLoginPageScreen.route,
        ) { backStackEntry ->
            screenInstance = Screen.HoyolabLoginPageScreen
            withBGScreen(isPadMode){
                    HoyolabLoginPageScreen(
                        navigator = navigator,
                        headerData = Screen.HoyolabLoginPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
        }

        scene(
            route = Screen.EventListPageScreen.route) {
            screenInstance = Screen.EventListPageScreen
            withBGScreen(isPadMode){
                    EventListPageScreen(
                        navigator = navigator,
                        headerData = Screen.EventListPageScreen.headerData
                    )
                }
        }

        //?eventId={eventId}
        scene(
            route = Screen.EventContentPageScreen.route,
        ) { backStackEntry ->
            screenInstance = Screen.EventContentPageScreen
            withBGScreen(isPadMode){
                    EventContentPageScreen(
                        navigator = navigator,
                        headerData = Screen.EventContentPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }

        }
        scene(
            route = Screen.MapPageScreen.route) {
            screenInstance = Screen.MapPageScreen
            withBGScreen(isPadMode){
                    MapPageScreen(
                        navigator = navigator,
                        headerData = Screen.MapPageScreen.headerData
                    )
                }

        }

        //?uid={uid}
        scene(
            route = Screen.UserInfoPageScreen.route,
        ) { backStackEntry ->
            screenInstance = Screen.UserInfoPageScreen
            withBGScreen(isPadMode){
                    UserInfoPageScreen(
                        navigator = navigator,
                        headerData = Screen.UserInfoPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }

        }

        //?uid={uid}&charId={charId}
        scene(
            route = Screen.UserCharacterPageScreen.route,
        ) { backStackEntry ->
            screenInstance = Screen.UserCharacterPageScreen
            withBGScreen(isPadMode){
                    UserCharacterPageScreen(
                        navigator = navigator,
                        headerData = Screen.UserCharacterPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }

        }
        scene(
            route = Screen.UIDSearchPageScreen.route) {
            screenInstance = Screen.UIDSearchPageScreen
            withBGScreen(isPadMode){
                    UIDSearchPageScreen(
                        navigator = navigator,
                        headerData = Screen.UIDSearchPageScreen.headerData
                    )
                }

        }
        scene(
            route = Screen.MemoryOfChaosMissionPageScreen.route) {
            screenInstance = Screen.MemoryOfChaosMissionPageScreen
            withBGScreen(isPadMode){
                    MemoryOfChaosMissionPageScreen(
                        navigator = navigator,
                        headerData = Screen.MemoryOfChaosMissionPageScreen.headerData
                    )
                }

        }
        //?uid={uid}
        scene(
            route = Screen.BattleChroniclePageScreen.route,
            
        ) { backStackEntry ->
            screenInstance = Screen.BattleChroniclePageScreen
            withBGScreen(isPadMode){
                    BattleChroniclePageScreen(
                        navigator = navigator,
                        headerData = Screen.BattleChroniclePageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }

        }
        scene(
            route = Screen.PureFictionMissionPageScreen.route) {
            screenInstance = Screen.PureFictionMissionPageScreen
            withBGScreen(isPadMode){
                    PureFictionMissionPageScreen(
                        navigator = navigator,
                        headerData = Screen.PureFictionMissionPageScreen.headerData
                    )
                }

        }
        scene(
            route = Screen.AboutStargazerPageScreen.route) {
            screenInstance = Screen.AboutStargazerPageScreen
            withBGScreen(isPadMode){
                    AboutStargazerPageScreen(
                        navigator = navigator,
                        headerData = Screen.AboutStargazerPageScreen.headerData
                    )
                }

        }
        scene(
            route = Screen.ExpeditionPageScreen.route) {
            screenInstance = Screen.ExpeditionPageScreen
            withBGScreen(isPadMode){
                ExpeditionPageScreen(
                        navigator = navigator,
                        headerData = Screen.ExpeditionPageScreen.headerData
                    )
                }

        }
        scene(
            route = Screen.ProficientLeaderboardPageScreen.route) {
            screenInstance = Screen.ProficientLeaderboardPageScreen
            withBGScreen(isPadMode){
                ProficientLeaderboardPageScreen(
                        navigator = navigator,
                        headerData = Screen.ProficientLeaderboardPageScreen.headerData
                    )
                }

        }
    }
}

/**
 * Navigate to a route with a limited interval.
 */
fun Navigator.navigateLimited(route: String, options: NavOptions? = null) {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, options)
        Settings().putLong("lastNavigationTime", currentTime)
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

@Composable
fun BlankPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxHeight()){

    }
}
