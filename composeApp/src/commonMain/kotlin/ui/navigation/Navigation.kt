/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeSource
import getDeviceInfo
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import types.Wallpaper.Companion.initWallpaperList
import ui.components.HeaderData
import ui.components.PomPomPopupUI
import ui.components.defaultHeaderData
import ui.screens.AboutStargazerPageScreen
import ui.screens.ActionOrderListPageScreen
import ui.screens.ActionOrderSimulatorPageScreen
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
import ui.screens.bgModified
import ui.screens.doInit
import ui.screens.globalPadHomePageBg
import ui.screens.initActionOrderTeamList
import ui.screens.initCharList
import ui.screens.initLcList
import ui.screens.initMOCList
import ui.screens.initPFList
import ui.screens.initRelicList
import ui.screens.refreshCharList
import ui.screens.refreshLcList
import ui.screens.refreshMOCList
import ui.screens.refreshPFList
import ui.screens.refreshRelicList
import utils.app.Constants.Companion.HOME_WIDTH
import utils.app.Language
import utils.app.isIosPlatform
import utils.app.snackbarInstance
import utils.app.valueOfWithDefaultCombatType
import utils.app.valueOfWithDefaultPath

/**
 * Navigate to a route with a limited interval.
 *  - The interval is 1 second.
 * @param route The route to navigate to.
 * @param options The navigation options.
 */
//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navigatorInstance : NavHostController

lateinit var hazeStateRoot : HazeState

lateinit var urlHandler: UriHandler

/**
 * Only usage : For GlobalBackground check whether should blur the background
 *
 * **Warm remind** : popBack will not be able to this.
 */
var screenInstance by mutableStateOf<Screen>(Screen.BlankPage)

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

@Composable
fun initVar(){
    initCharList()
    initLcList()
    initRelicList()
    initMOCList()
    initPFList()
    initActionOrderTeamList()
    initWallpaperList()
    println("INITED!")
}

/**
 * Root Frame of the app.
 */
@Composable
fun RootContent() {
    hazeStateRoot = remember { HazeState() }
    val isPadMode = remember { mutableStateOf(false) }
    val isRotate = remember { mutableStateOf(false) }
    val navigator = rememberNavController()
    val focusManager = LocalFocusManager.current
    navigatorInstance = navigator
    urlHandler = LocalUriHandler.current

    key(isRotate.value) {
        globalWindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        isPadMode.value = isPadMode()
        Language().setAppLanguage()
    }

    key(doInit.value){
        if (!doInit.value){
            initVar()
            doInit.value = true
        }
    }

    Scaffold(
        modifier = Modifier.onSizeChanged { isRotate.value = !isRotate.value },
        snackbarHost = { SnackbarHost(snackbarInstance, modifier = Modifier.navigationBarsPadding()) }
    ) {


        if(isPadMode.value){
            MakeBackground(screen = screenInstance, forceBlur = isPadMode.value)
        }

        Row {
            if(isPadMode.value && screenInstance != Screen.SplashPage && screenInstance != Screen.BlankPage){
                Box(Modifier
                    .width(HOME_WIDTH)
                    .let { if (getScreenSizeInfo().wDP < HOME_WIDTH * 1.5f) it.weight(1f) else it }
                    .fillMaxHeight(),
                ) {
                    if(globalPadHomePageBg.value){
                        key(bgModified.value){
                            if(bgModified.value){
                                MakeBackground(screen = Screen.HomePage, forceBlur = false)
                            }
                            MakeBackground(screen = Screen.HomePage, forceBlur = false)
                        }
                    }
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

fun refreshInit(){
    CoroutineScope(Dispatchers.Default).launch {
        async {
            refreshCharList()
            refreshLcList()
            refreshRelicList()
            refreshPFList()
            refreshMOCList()
            println("REFRESHED!")
        }.await()
    }
}


@Composable
fun NavHostInit(navigator : NavHostController, isPadMode: MutableState<Boolean>){
    //ref: https://github.com/JetBrains/compose-multiplatform/issues/4528#issuecomment-2015222282
    val animationSpec = tween<IntOffset>(easing = LinearEasing)
    if(isIosPlatform() && !isPadMode.value){
        NavHost(
            navController = navigator,
            startDestination = SplashRoute,
            builder = navBuilder(isPadMode, navigator)
        )
    }else{
        NavHost(
            navController = navigator,
            startDestination = SplashRoute,
            enterTransition = { if(isPadMode.value) fadeIn() else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left,animationSpec)},
            exitTransition = { if(isPadMode.value) fadeOut() else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,animationSpec) },
            popEnterTransition = { if(isPadMode.value) fadeIn() else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right,animationSpec) },
            popExitTransition = { if(isPadMode.value) fadeOut() else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,animationSpec) },
            builder = navBuilder(isPadMode, navigator)
        )
    }
}

fun navBuilder(isPadMode: MutableState<Boolean>, navigator: NavHostController) : NavGraphBuilder.() -> Unit = {
    composable<SplashRoute> {
        screenInstance = Screen.SplashPage
        SplashPage(
            navigator = navigator,
            headerData = Screen.SplashPage.headerData
        )
    }
    composable<HomeRoute>{
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

    composable<CharacterListRoute> {
        screenInstance = Screen.CharacterListPage
        withBGScreen(isPadMode){
            CharacterListPage(
                navigator = navigator,
                headerData = Screen.CharacterListPage.headerData
            )
        }
    }
    composable<LightconeListRoute> {
        screenInstance = Screen.LightconeListPage
        withBGScreen(isPadMode){
            LightconeListPage(
                navigator = navigator,
                headerData = Screen.LightconeListPage.headerData
            )
        }
    }
    composable<RelicListRoute> {
        screenInstance = Screen.RelicListPage
        withBGScreen(isPadMode){
            RelicListPage(
                navigator = navigator,
                headerData = Screen.RelicListPage.headerData
            )
        }
    }

    composable<CharacterInfoRoute> { backStackEntry ->
        screenInstance = Screen.CharacterInfoPage
        withBGScreen(isPadMode) {
            CharacterInfoPage(
                navigator = navigator,
                headerData = Screen.CharacterInfoPage.headerData,
                backStackEntry = backStackEntry
            )
        }
    }

    composable<LightconeInfoRoute> { backStackEntry ->
        screenInstance = Screen.LightconeInfoPage
        withBGScreen(isPadMode){
            LightconeInfoPage(
                navigator = navigator,
                headerData = Screen.LightconeInfoPage.headerData,
                backStackEntry = backStackEntry
            )
        }
    }

    //?fileName={fileName}
    composable<RelicInfoRoute> { backStackEntry ->
        screenInstance = Screen.RelicInfoPage
        withBGScreen(isPadMode){
            RelicInfoPage(
                navigator = navigator,
                headerData = Screen.RelicInfoPage.headerData,
                backStackEntry = backStackEntry,
            )
        }
    }

    composable<SettingRoute> {
        screenInstance = Screen.SettingScreen
        withBGScreen(isPadMode){
            SettingScreen(
                navigator = navigator,
                headerData = Screen.SettingScreen.headerData
            )
        }
    }

    composable<BackgroundSettingRoute> {
        screenInstance = Screen.BackgroundSettingScreen
        withBGScreen(isPadMode){
            BackgroundSettingScreen(
                navigator = navigator,
                headerData = Screen.BackgroundSettingScreen.headerData
            )
        }
    }

    //?serverId={serverId}
    composable<HoyolabLoginRoute> { backStackEntry ->
        screenInstance = Screen.HoyolabLoginPageScreen
        withBGScreen(isPadMode){
            HoyolabLoginPageScreen(
                navigator = navigator,
                headerData = Screen.HoyolabLoginPageScreen.headerData,
                backStackEntry = backStackEntry,
            )
        }
    }

    composable<EventListRoute> {
        screenInstance = Screen.EventListPageScreen
        withBGScreen(isPadMode){
            EventListPageScreen(
                navigator = navigator,
                headerData = Screen.EventListPageScreen.headerData
            )
        }
    }

    //?eventId={eventId}
    composable<EventContentRoute> { backStackEntry ->
        screenInstance = Screen.EventContentPageScreen
        withBGScreen(isPadMode){
            EventContentPageScreen(
                navigator = navigator,
                headerData = Screen.EventContentPageScreen.headerData,
                backStackEntry = backStackEntry,
            )
        }

    }
    composable<MapRoute> {
        //val uriHandler = LocalUriHandler.current
        LaunchedEffect(Unit){
            navigator.popBackStack()
        }
    }

    //?uid={uid}
    composable<UserInfoRoute> { backStackEntry ->
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
    composable<UserCharacterRoute> { backStackEntry ->
        screenInstance = Screen.UserCharacterPageScreen
        withBGScreen(isPadMode){
            UserCharacterPageScreen(
                navigator = navigator,
                headerData = Screen.UserCharacterPageScreen.headerData,
                backStackEntry = backStackEntry,
            )
        }

    }
    composable<UIDSearchRoute> {
        screenInstance = Screen.UIDSearchPageScreen
        withBGScreen(isPadMode){
            UIDSearchPageScreen(
                navigator = navigator,
                headerData = Screen.UIDSearchPageScreen.headerData
            )
        }

    }
    composable<MemoryOfChaosMissionRoute> {
        screenInstance = Screen.MemoryOfChaosMissionPageScreen
        withBGScreen(isPadMode){
            MemoryOfChaosMissionPageScreen(
                navigator = navigator,
                headerData = Screen.MemoryOfChaosMissionPageScreen.headerData
            )
        }

    }
    //?uid={uid}
    composable<BattleChronicleRoute> { backStackEntry ->
        screenInstance = Screen.BattleChroniclePageScreen
        withBGScreen(isPadMode){
            BattleChroniclePageScreen(
                navigator = navigator,
                headerData = Screen.BattleChroniclePageScreen.headerData,
                backStackEntry = backStackEntry,
            )
        }

    }
    composable<PureFictionMissionRoute> {
        screenInstance = Screen.PureFictionMissionPageScreen
        withBGScreen(isPadMode){
            PureFictionMissionPageScreen(
                navigator = navigator,
                headerData = Screen.PureFictionMissionPageScreen.headerData
            )
        }

    }
    composable<AboutStargazerRoute> {
        screenInstance = Screen.AboutStargazerPageScreen
        withBGScreen(isPadMode){
            AboutStargazerPageScreen(
                navigator = navigator,
                headerData = Screen.AboutStargazerPageScreen.headerData
            )
        }

    }
    composable<ExpeditionRoute> {
        screenInstance = Screen.ExpeditionPageScreen
        withBGScreen(isPadMode){
            ExpeditionPageScreen(
                navigator = navigator,
                headerData = Screen.ExpeditionPageScreen.headerData
            )
        }

    }
    composable<ProficientLeaderboardRoute> {
        screenInstance = Screen.ProficientLeaderboardPageScreen
        withBGScreen(isPadMode){
            ProficientLeaderboardPageScreen(
                navigator = navigator,
                headerData = Screen.ProficientLeaderboardPageScreen.headerData
            )
        }

    }
    composable<ActionOrderListRoute> {
        screenInstance = Screen.ActionOrderListPageScreen
        withBGScreen(isPadMode){
            ActionOrderListPageScreen(
                navigator = navigator,
                headerData = Screen.ActionOrderListPageScreen.headerData
            )
        }

    }

    //?index={index}
    composable<ActionOrderSimulatorRoute> { backStackEntry ->
        screenInstance = Screen.ActionOrderSimulatorPageScreen
        withBGScreen(isPadMode){
            ActionOrderSimulatorPageScreen(
                navigator = navigator,
                headerData = Screen.ActionOrderSimulatorPageScreen.headerData,
                backStackEntry = backStackEntry,
            )
        }

    }

}

/**
 * Navigate to a route with a limited interval.
 */
fun NavHostController.navigateLimited(route: Any, options: NavOptions? = null) {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, options)
        Settings().putLong("lastNavigationTime", currentTime)
    }
}
fun NavHostController.popBackStackLimited() {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        popBackStack()
        Settings().putLong("lastNavigationTime", currentTime)
    }
}

@Composable
fun withBGScreen(isPadMode: MutableState<Boolean>, content: @Composable () -> Unit){
    val rememberedScreenInstance = remember { mutableStateOf(screenInstance) }

    Box(modifier = Modifier.fillMaxSize()) {
        if(!isPadMode.value){
            MakeBackground(screen = rememberedScreenInstance.value)
        }
        Box(modifier = Modifier.imePadding()) {
            content()
        }

        //Overlay - For Error Message or Loading Popup
        PomPomPopupUI(hazeState = hazeStateRoot)

        /*
        Toaster(
            state = toastInstance,
            richColors = true,
            maxVisibleToasts = 10,
            alignment = Alignment.BottomCenter,
            showCloseButton = true,
            darkTheme = true,
            modifier = Modifier.navigationBarsPadding()
        )
         */
    }
}

@Composable
fun BlankPage(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxHeight()){

    }
}
