/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import types.AbyssInfoList
import types.Wallpaper.Companion.initWallpaperList
import ui.components.PomPomPopupUI
import ui.screens.AboutStargazerPageScreen
import ui.screens.ActionOrderListPageScreen
import ui.screens.ActionOrderSimulatorPageScreen
import ui.screens.AnomalyArbitrationMissionPageScreen
import ui.screens.ApocalypticShadowMissionPageScreen
import ui.screens.BackgroundSettingScreen
import ui.screens.BattleChroniclePageScreen
import ui.screens.CharacterInfoPage
import ui.screens.CharacterListPage
import ui.screens.EventContentPageScreen
import ui.screens.EventListPageScreen
import ui.screens.ExpeditionPageScreen
import ui.screens.HomePage
import ui.screens.HomePageBlockEditPageScreen
import ui.screens.HoyolabLoginPageScreen
import ui.screens.IIRCHomePageScreen
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
import ui.screens.initAAList
import ui.screens.initASList
import ui.screens.initActionOrderTeamList
import ui.screens.initCharList
import ui.screens.initLcList
import ui.screens.initMOCList
import ui.screens.initPFList
import ui.screens.initRelicList
import ui.screens.refreshAAList
import ui.screens.refreshASList
import ui.screens.refreshCharList
import ui.screens.refreshLcList
import ui.screens.refreshMOCList
import ui.screens.refreshPFList
import ui.screens.refreshRelicList
import utils.app.Constants.Companion.HOME_WIDTH
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Language
import utils.app.SG3NavTransitions
import utils.app.initPurchase
import utils.app.isAndroidPlatform
import utils.app.isIosPlatform
import utils.app.notchPaddingLeft
import utils.app.notchPaddingRight
import utils.app.pxToDp
import utils.app.showWarningToast
import utils.app.snackbarInstance

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
    initPurchase()
    initCharList()
    initLcList()
    initRelicList()
    initMOCList()
    initPFList()
    initASList()
    initAAList()
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

    val screenWidth = remember { mutableStateOf(INFO_MAX_WIDTH) }
    val homePageWithCutOutWidth = remember { mutableStateOf(0.dp) }
    val densityC = LocalDensity.current
    val density = LocalDensity.current.density
    Scaffold(
        modifier = Modifier.onSizeChanged { isRotate.value = !isRotate.value },
        snackbarHost = { SnackbarHost(snackbarInstance, modifier = Modifier.navigationBarsPadding()) }
    ) {
        BoxWithConstraints {
            screenWidth.value = maxWidth
            if(isPadMode.value){
                MakeBackground(screen = screenInstance, forceBlur = isPadMode.value, hazeState = hazeStateRoot)

                if(isPadMode.value && screenInstance != Screen.SplashPage && screenInstance != Screen.BlankPage){
                    Row {
                        Box(modifier = Modifier
                            .width(HOME_WIDTH + homePageWithCutOutWidth.value)
                            .let { if (screenWidth.value < HOME_WIDTH * 1.5f) it.weight(1f) else it }
                            .fillMaxHeight()
                        ) {
                            key(bgModified.value){
                                if(globalPadHomePageBg.value){
                                    MakeBackground(screen = Screen.HomePage, forceBlur = false, hazeState = hazeStateRoot)
                                }
                            }
                        }
                        Box(Modifier.weight(1f)) {
                        }
                    }
                }
            }

            if(screenInstance == Screen.SplashPage){
                NavHostInit(navigatorInstance, isPadMode)
            }else{
                Row {
                    if(isPadMode.value && screenInstance != Screen.SplashPage && screenInstance != Screen.BlankPage){
                        Box(Modifier
                            .notchPaddingLeft()
                            .width(HOME_WIDTH)
                            .let { if (screenWidth.value < HOME_WIDTH * 1.5f) it.weight(1f) else it }
                            .fillMaxHeight()
                            .onGloballyPositioned {
                                homePageWithCutOutWidth.value = pxToDp(it.positionInWindow().x.toInt(), density)
                            }
                        ) {
                            HomePage(
                                navigator = navigatorInstance,
                                hazeState = hazeStateRoot,
                            )
                        }
                    }
                    Box(Modifier.weight(1f)) {
                        NavHostInit(navigatorInstance, isPadMode)
                    }
                }
            }
        }
    }
}

fun refreshInit(){
    CoroutineScope(Dispatchers.Default).launch {
        async {
            refreshCharList()
            refreshLcList()
            refreshRelicList()
            AbyssInfoList.refreshListJson()
            refreshPFList()
            refreshMOCList()
            refreshASList()
            refreshAAList()
            println("REFRESHED!")
        }.await()
    }
}


@Composable
fun NavHostInit(navigator : NavHostController, isPadMode: MutableState<Boolean>){
    //ref: https://github.com/JetBrains/compose-multiplatform/issues/4528#issuecomment-2015222282
    if(isPadMode.value){
        NavHost(
            navController = navigator,
            startDestination = SplashRoute,
            enterTransition = { fadeIn() } ,
            exitTransition = { fadeOut() } ,
            popEnterTransition = { fadeIn() } ,
            popExitTransition = { fadeOut() } ,
            builder = navBuilder(isPadMode, navigator)
        )
    }else if(isIosPlatform()) {
        NavHost(
            navController = navigator,
            startDestination = SplashRoute,
            builder = navBuilder(isPadMode, navigator)
        )
    }else {
        NavHost(
            navController = navigator,
            startDestination = SplashRoute,
            enterTransition = SG3NavTransitions.enterTransition,
            exitTransition = SG3NavTransitions.exitTransition ,
            popEnterTransition = SG3NavTransitions.popEnterTransition ,
            popExitTransition = SG3NavTransitions.popExitTransition ,
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
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            if(!isPadMode.value) {
                HomePage(
                    navigator = navigator,
                    hazeState = hazeState
                )
            }else{
                BlankPage(
                    navigator = navigator,
                    hazeState = hazeState

                )
            }
        }
    }

    composable<CharacterListRoute> {
        screenInstance = Screen.CharacterListPage
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            CharacterListPage(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }
    }
    composable<LightconeListRoute> {
        screenInstance = Screen.LightconeListPage
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            LightconeListPage(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }
    }
    composable<RelicListRoute> {
        screenInstance = Screen.RelicListPage
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            RelicListPage(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }
    }

    composable<CharacterInfoRoute> { backStackEntry ->
        screenInstance = Screen.CharacterInfoPage
        withBGScreen(isPadMode) { hazeState, pageHeader ->
            CharacterInfoPage(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
            )
        }
    }

    composable<LightconeInfoRoute> { backStackEntry ->
        screenInstance = Screen.LightconeInfoPage
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            LightconeInfoPage(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
            )
        }
    }

    //?fileName={fileName}
    composable<RelicInfoRoute> { backStackEntry ->
        screenInstance = Screen.RelicInfoPage
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            RelicInfoPage(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
            )
        }
    }

    composable<SettingRoute> {
        screenInstance = Screen.SettingScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            SettingScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }
    }

    composable<BackgroundSettingRoute> {
        screenInstance = Screen.BackgroundSettingScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            BackgroundSettingScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }
    }

    //?serverId={serverId}
    composable<HoyolabLoginRoute> { backStackEntry ->
        screenInstance = Screen.HoyolabLoginPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            HoyolabLoginPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
            )
        }
    }

    composable<EventListRoute> {
        screenInstance = Screen.EventListPageScreen
        val alreadyDisplayWarning = rememberSaveable { mutableStateOf(false) }
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            EventListPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
            if((!isAndroidPlatform()) && (!alreadyDisplayWarning.value)){
                showWarningToast(message = "暫不支援內嵌式網頁，請見諒\nApologize for the inconvenience that currently Webview is not fully supported", dismissPrevious = true)
                alreadyDisplayWarning.value = true
            }
        }
    }

    //?eventId={eventId}
    composable<EventContentRoute> { backStackEntry ->
        screenInstance = Screen.EventContentPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            EventContentPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
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
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            UserInfoPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
                pageHeader = pageHeader
            )
        }

    }

    //?uid={uid}&charId={charId}
    composable<UserCharacterRoute> { backStackEntry ->
        screenInstance = Screen.UserCharacterPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            UserCharacterPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
            )
        }

    }
    composable<UIDSearchRoute> {
        screenInstance = Screen.UIDSearchPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            UIDSearchPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }

    }
    composable<MemoryOfChaosMissionRoute> {
        screenInstance = Screen.MemoryOfChaosMissionPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            MemoryOfChaosMissionPageScreen(
                navigator = navigator,
                hazeState = hazeState
            )
        }

    }
    //?uid={uid}
    composable<BattleChronicleRoute> { backStackEntry ->
        screenInstance = Screen.BattleChroniclePageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            BattleChroniclePageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
            )
        }

    }
    composable<PureFictionMissionRoute> {
        screenInstance = Screen.PureFictionMissionPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            PureFictionMissionPageScreen(
                navigator = navigator,
                hazeState = hazeState
            )
        }

    }
    composable<AboutStargazerRoute> {
        screenInstance = Screen.AboutStargazerPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            AboutStargazerPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }

    }
    composable<ExpeditionRoute> {
        screenInstance = Screen.ExpeditionPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            ExpeditionPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }

    }
    composable<ProficientLeaderboardRoute> {
        screenInstance = Screen.ProficientLeaderboardPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            ProficientLeaderboardPageScreen(
                navigator = navigator,
                hazeState = hazeState,
            )
        }

    }
    composable<ActionOrderListRoute> {
        screenInstance = Screen.ActionOrderListPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            ActionOrderListPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
            )
        }

    }

    //?index={index}
    composable<ActionOrderSimulatorRoute> { backStackEntry ->
        screenInstance = Screen.ActionOrderSimulatorPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            ActionOrderSimulatorPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                backStackEntry = backStackEntry,
            )
        }

    }

    composable<IIRCHomePageRoute> {
        screenInstance = Screen.IIRCHomePageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            IIRCHomePageScreen(
                navigator = navigator,
                hazeState = hazeState,
            )
        }

    }

    composable<ApocalypticShadowMissionRoute> {
        screenInstance = Screen.ApocalypticShadowMissionPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            ApocalypticShadowMissionPageScreen(
                navigator = navigator,
                hazeState = hazeState
            )
        }

    }
    composable<AnomalyArbitrationMissionRoute> {
        screenInstance = Screen.AnomalyArbitrationMissionPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            AnomalyArbitrationMissionPageScreen(
                navigator = navigator,
                hazeState = hazeState
            )
        }

    }
    composable<HomePageBlockEditRoute> {
        screenInstance = Screen.HomePageBlockEditPageScreen
        withBGScreen(isPadMode){ hazeState, pageHeader ->
            HomePageBlockEditPageScreen(
                navigator = navigator,
                hazeState = hazeState,
                pageHeader = pageHeader
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

fun NavHostController.navigateLimited(route: Any, builder: NavOptionsBuilder.() -> Unit) {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, builder)
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
fun withBGScreen(isPadMode: MutableState<Boolean>, content: @Composable (HazeState, MutableState<@Composable () -> Unit>) -> Unit){
    val rememberedScreenInstance = remember { mutableStateOf(screenInstance) }
    val hazeStateLocal = if(isPadMode.value) hazeStateRoot else remember { HazeState() }
    val pageHeader = remember { mutableStateOf<@Composable () -> Unit>({}) }

    // Make sure the language will not be changed when the screen is changed
    // https://discord.com/channels/880921456903618610/880921459546009602/1367305921209962659
    Language().setAppLanguage()

    Box(modifier = Modifier.fillMaxSize()) {
        if(!isPadMode.value){
            MakeBackground(screen = rememberedScreenInstance.value, hazeState = hazeStateLocal)
        }
        Box(modifier = Modifier.imePadding().notchPaddingRight()) {
            content(hazeStateLocal, pageHeader)
        }
        pageHeader.value()

        //Overlay - For Error Message or Loading Popup, only show in page when is Pad Mode

        PomPomPopupUI(hazeState = hazeStateLocal)

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
    navigator: NavHostController,
    hazeState: HazeState,
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxHeight()){

    }
}
