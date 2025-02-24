/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.core.layout.WindowWidthSizeClass
import com.dokar.sonner.Toaster
import com.russhwolf.settings.Settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
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
import utils.app.toastInstance

/**
 * Navigate to a route with a limited interval.
 *  - The interval is 1 second.
 * @param route The route to navigate to.
 * @param options The navigation options.
 */
//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navigatorInstance : NavHostController

private lateinit var hazeStateRoot : HazeState

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

/**
 * Root Frame of the app.
 */
@Composable
fun RootContent() {
    val snackbarHostState = remember { SnackbarHostState() }
    hazeStateRoot = remember { HazeState() }
    val isPadMode = remember { mutableStateOf(false) }
    val isRotate = remember { mutableStateOf(false) }
    val navigator = rememberNavController()
    navigatorInstance = navigator

    key(isRotate.value) {
        globalWindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        isPadMode.value = isPadMode()
    }

    key(doInit.value){
        if (!doInit.value){
            initCharList()
            initLcList()
            initRelicList()
            initMOCList()
            initPFList()
            initActionOrderTeamList()
            println("INITED!")
            doInit.value = true
        }
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
                    if(globalPadHomePageBg.value){
                        MakeBackground(screen = Screen.HomePage, forceBlur = false)
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
    val animationSpec = tween<IntOffset>(650)
    NavHost(
        navController = navigator,
        startDestination = Screen.SplashPage.route,
        enterTransition = { if(isPadMode.value) fadeIn() else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec) },
        exitTransition = { if(isPadMode.value) fadeOut() else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec) },
        popEnterTransition = { if(isPadMode.value) fadeIn() else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec) },
        popExitTransition = { if(isPadMode.value) fadeOut() else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec) }
    ) {
        composable(route = Screen.SplashPage.route) {
            screenInstance = Screen.SplashPage
            SplashPage(
                navigator = navigator,
                headerData = Screen.SplashPage.headerData
            )
        }
        composable(route = Screen.HomePage.route) {
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

        composable(route = Screen.CharacterListPage.route) {
            screenInstance = Screen.CharacterListPage
            withBGScreen(isPadMode){
                CharacterListPage(
                    navigator = navigator,
                    headerData = Screen.CharacterListPage.headerData
                )
            }
        }
        composable(route = Screen.LightconeListPage.route) {
            screenInstance = Screen.LightconeListPage
            withBGScreen(isPadMode){
                LightconeListPage(
                    navigator = navigator,
                    headerData = Screen.LightconeListPage.headerData
                )
            }
        }
        composable(route = Screen.RelicListPage.route) {
            screenInstance = Screen.RelicListPage
            withBGScreen(isPadMode){
                RelicListPage(
                    navigator = navigator,
                    headerData = Screen.RelicListPage.headerData
                )
            }
        }

        composable(
            //?fileName={fileName}&combatType={combatType}&path={path}&charId={charId}
            route = "${Screen.CharacterInfoPage.route}/{charName}?fileName={fileName}&combatType={combatType}&path={path}&charId={charId}",
            arguments = listOf(
                navArgument("charName") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("fileName") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("combatType") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("path") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("charName") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
            )
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

        composable(
            //?fileName={fileName}&path={path}
            route = "${Screen.LightconeInfoPage.route}/{lcName}?fileName={fileName}&path={path}\",",
            arguments = listOf(
                navArgument("fileName") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("path") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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
        composable(
            route = "${Screen.RelicInfoPage.route}/{relicName}?fileName={fileName}",
            arguments = listOf(
                navArgument("fileName") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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

        composable(
            route = Screen.SettingScreen.route) {
            screenInstance = Screen.SettingScreen
            withBGScreen(isPadMode){
                SettingScreen(
                    navigator = navigator,
                    headerData = Screen.SettingScreen.headerData
                    )
            }
        }

        composable(
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
        composable(
            route = Screen.HoyolabLoginPageScreen.route +"?serverId={serverId}",
            arguments = listOf(
                navArgument("serverId") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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

        composable(
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
        composable(
            route = Screen.EventContentPageScreen.route +"?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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
        composable(
            route = Screen.MapPageScreen.route) {
            screenInstance = Screen.MapPageScreen
            LocalUriHandler.current.openUri("https://act.hoyolab.com/sr/app/interactive-map/index.html?lang=${Language.TextLanguageInstance.hoyolabName}")
            navigator.popBackStack()
            /*
            withBGScreen(isPadMode){
                    MapPageScreen(
                        navigator = navigator,
                        headerData = Screen.MapPageScreen.headerData
                    )
                }
             */

        }

        //?uid={uid}
        composable(
            route = Screen.UserInfoPageScreen.route +"?uid={uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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
        composable(
            route = Screen.UserCharacterPageScreen.route +"?uid={uid}&charId={charId}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType ;defaultValue = ""; nullable = false; },
                navArgument("charId") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
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
        composable(
            route = Screen.UIDSearchPageScreen.route) {
            screenInstance = Screen.UIDSearchPageScreen
            withBGScreen(isPadMode){
                    UIDSearchPageScreen(
                        navigator = navigator,
                        headerData = Screen.UIDSearchPageScreen.headerData
                    )
                }

        }
        composable(
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
        composable(
            route = Screen.BattleChroniclePageScreen.route + "?uid={uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
            
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
        composable(
            route = Screen.PureFictionMissionPageScreen.route) {
            screenInstance = Screen.PureFictionMissionPageScreen
            withBGScreen(isPadMode){
                    PureFictionMissionPageScreen(
                        navigator = navigator,
                        headerData = Screen.PureFictionMissionPageScreen.headerData
                    )
                }

        }
        composable(
            route = Screen.AboutStargazerPageScreen.route) {
            screenInstance = Screen.AboutStargazerPageScreen
            withBGScreen(isPadMode){
                    AboutStargazerPageScreen(
                        navigator = navigator,
                        headerData = Screen.AboutStargazerPageScreen.headerData
                    )
                }

        }
        composable(
            route = Screen.ExpeditionPageScreen.route) {
            screenInstance = Screen.ExpeditionPageScreen
            withBGScreen(isPadMode){
                ExpeditionPageScreen(
                        navigator = navigator,
                        headerData = Screen.ExpeditionPageScreen.headerData
                    )
                }

        }
        composable(
            route = Screen.ProficientLeaderboardPageScreen.route) {
            screenInstance = Screen.ProficientLeaderboardPageScreen
            withBGScreen(isPadMode){
                ProficientLeaderboardPageScreen(
                        navigator = navigator,
                        headerData = Screen.ProficientLeaderboardPageScreen.headerData
                    )
                }

        }
        composable(
            route = Screen.ActionOrderListPageScreen.route) {
            screenInstance = Screen.ActionOrderListPageScreen
            withBGScreen(isPadMode){
                ActionOrderListPageScreen(
                    navigator = navigator,
                    headerData = Screen.ActionOrderListPageScreen.headerData
                )
            }

        }

        //?index={index}
        composable(
            route = Screen.ActionOrderSimulatorPageScreen.route + "?index={index}",
            arguments = listOf(
                navArgument("index") { type = NavType.StringType ;defaultValue = ""; nullable = false; }
            )
        ) { backStackEntry ->
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
}

/**
 * Navigate to a route with a limited interval.
 */
fun NavHostController.navigateLimited(route: String, options: NavOptions? = null) {
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
    val hazeState = remember { HazeState() }
    Box(modifier = Modifier.fillMaxSize()) {
        if(!isPadMode.value){
            MakeBackground(screen = screenInstance)
        }
        Box(modifier = Modifier.haze(hazeState)){
            content()
        }

        //Overlay - For Error Message or Loading Popup
        PomPomPopupUI(hazeState = hazeState)

        Toaster(
            state = toastInstance,
            richColors = true,
            maxVisibleToasts = 10,
            alignment = Alignment.BottomCenter,
            showCloseButton = true,
            darkTheme = true,
            modifier = Modifier.navigationBarsPadding()
        )
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
