/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package utils.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState
import com.dokar.sonner.rememberToasterState
import com.russhwolf.settings.Settings
import components.HeaderData
import components.PomPomPopup
import components.PomPomPopupUI
import components.defaultHeaderData
import components.docCountDown
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.ChangeWallPaper
import files.CharacterList
import files.Event
import files.HaveNotUsed
import files.LightconeList
import files.Login
import files.MOCMyBattleReport
import files.Map
import files.MemoryOfChaos
import files.PureFiction
import files.RelicList
import files.Res
import files.Setting
import files.UIDSearch
import files.UnLockAll
import files.UserInfoGameData
import files.phorphos_alien_fill
import files.phorphos_atom_fill
import files.phorphos_baseball_cap_fill
import files.phorphos_film_slate_regular
import files.phorphos_game_controller_fill
import files.phorphos_game_controller_regular
import files.phorphos_house_fill
import files.phorphos_map_trifold_fill
import files.phorphos_medal_military_fill
import files.phorphos_person_fill
import files.phorphos_sliders_horizontal_fill
import files.phorphos_sword_fill
import kotlinx.datetime.Clock
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import screens.BackgroundSettingScreen
import screens.BattleChroniclePageScreen
import screens.CharacterInfoPage
import screens.CharacterListPage
import screens.EventContentPageScreen
import screens.EventListPageScreen
import screens.HomePage
import screens.HoyolabLoginPageScreen
import screens.LightconeInfoPage
import screens.LightconeListPage
import screens.MakeBackground
import screens.MapPageScreen
import screens.MemoryOfChaosMissionPageScreen
import screens.PureFictionMissionPageScreen
import screens.RelicInfoPage
import screens.RelicListPage
import screens.SettingScreen
import screens.SplashPage
import screens.UIDSearchPageScreen
import screens.UserCharacterPageScreen
import screens.UserInfoPageScreen
import types.Constants
import utils.BezierEasing2O48

//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navigatorInstance : Navigator
lateinit var toastInstance : ToasterState
lateinit var pomPomPopupInstance: MutableState<PomPomPopup>

lateinit var swipeProperties: SwipeProperties
lateinit var navTransition : NavTransition

var isHomePaged = false
sealed class Screen(val route: String, val headerData: HeaderData = defaultHeaderData) {
    data object SplashPage :
        Screen("SplashPage", HeaderData(titleIconId = Res.drawable.phorphos_house_fill))

    data object HomePage : Screen("HomePage", HeaderData(titleIconId = Res.drawable.phorphos_house_fill))
    data object CharacterListPage : Screen(
        "CharacterListPage",
        HeaderData(
            titleRId = Res.string.CharacterList,
            titleIconId = Res.drawable.phorphos_person_fill
        )
    )

    data object LightconeListPage : Screen(
        "LightconeListPage",
        HeaderData(
            titleRId = Res.string.LightconeList,
            titleIconId = Res.drawable.phorphos_sword_fill
        )
    )

    data object RelicListPage : Screen(
        "RelicListPage",
        HeaderData(
            titleRId = Res.string.RelicList,
            titleIconId = Res.drawable.phorphos_baseball_cap_fill
        )
    )

    data object CharacterInfoPage : Screen(
        "CharacterInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_person_fill)
    )
    data object LightconeInfoPage : Screen(
        "LightconeInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_sword_fill)
    )
    data object RelicInfoPage : Screen(
        "RelicInfoPage",
        HeaderData(titleRId = Res.string.HaveNotUsed, titleIconId = Res.drawable.phorphos_baseball_cap_fill)
    )
    data object SettingScreen : Screen(
        "SettingScreen",
        HeaderData(titleRId = Res.string.Setting, titleIconId = Res.drawable.phorphos_sliders_horizontal_fill)
    )
    data object BackgroundSettingScreen : Screen(
        "BackgroundSettingScreen",
        HeaderData(titleRId = Res.string.ChangeWallPaper, titleIconId = Res.drawable.phorphos_sliders_horizontal_fill)
    )
    data object HoyolabLoginPageScreen : Screen(
        "HoyolabLoginPageScreen",
        HeaderData(titleRId = Res.string.Login, titleIconId = Res.drawable.phorphos_person_fill)
    )
    data object EventListPageScreen : Screen(
        "EventListPageScreen",
        HeaderData(titleRId = Res.string.Event, titleIconId = Res.drawable.phorphos_film_slate_regular)
    )
    data object EventContentPageScreen : Screen(
        "EventContentPageScreen",
        HeaderData(titleRId = Res.string.Event, titleIconId = Res.drawable.phorphos_film_slate_regular)
    )
    data object MapPageScreen : Screen(
        "MapPageScreen",
        HeaderData(titleRId = Res.string.Map, titleIconId = Res.drawable.phorphos_map_trifold_fill)
    )
    data object UserInfoPageScreen : Screen(
        "UserInfoPageScreen",
        HeaderData(titleRId = Res.string.UserInfoGameData, titleIconId = Res.drawable.phorphos_game_controller_regular)
    )
    data object UserCharacterPageScreen : Screen(
        "UserCharacterPageScreen",
        HeaderData(titleRId = Res.string.UserInfoGameData, titleIconId = Res.drawable.phorphos_game_controller_regular)
    )
    data object UIDSearchPageScreen : Screen(
        "UIDSearchPageScreen",
        HeaderData(titleRId = Res.string.UIDSearch, titleIconId = Res.drawable.phorphos_alien_fill)
    )
    data object MemoryOfChaosMissionPageScreen : Screen(
        "MemoryOfChaosMissionPageScreen",
        HeaderData(titleRId = Res.string.MemoryOfChaos, titleIconId = Res.drawable.phorphos_medal_military_fill)
    )
    data object BattleChroniclePageScreen : Screen(
        "BattleChroniclePageScreen",
        HeaderData(titleRId = Res.string.MOCMyBattleReport, titleIconId = Res.drawable.phorphos_game_controller_fill)
    )
    data object PureFictionMissionPageScreen : Screen(
        "PureFictionMissionPageScreen",
        HeaderData(titleRId = Res.string.PureFiction, titleIconId = Res.drawable.phorphos_atom_fill)
    )

    data object BlankScreen : Screen(
        "BlankScreen",
        HeaderData(titleRId = Res.string.PureFiction, titleIconId = Res.drawable.phorphos_atom_fill)
    )
    data object WithBGScreen : Screen(
        "WithBGScreen",
        HeaderData(titleRId = Res.string.PureFiction, titleIconId = Res.drawable.phorphos_atom_fill)
    )
    data object WithBGHeaderScreen : Screen(
        "WithBGHeaderScreen",
        HeaderData(titleRId = Res.string.UnLockAll, titleIconId = Res.drawable.phorphos_atom_fill)
    )
}

@Composable
fun Navigation() {
    println("HI IS FROM APP.KT")
    val navigator = rememberNavigator()
    val snackbarHostState = remember { SnackbarHostState() }

    navigatorInstance = navigator
    toastInstance = rememberToasterState()
    pomPomPopupInstance = remember { mutableStateOf(PomPomPopup()) }
    docCountDown = remember { mutableStateOf(0) }
    swipeProperties = remember { SwipeProperties(
        spaceToSwipe = Constants.SCREEN_SAVE_PADDING,
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

        scene(
            route = Screen.CharacterListPage.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.CharacterListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    CharacterListPage(
                        navigator = navigator,
                        headerData = Screen.CharacterListPage.headerData
                    )
                }
            )
        }

        scene(
            route = Screen.LightconeListPage.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.LightconeListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    LightconeListPage(
                        navigator = navigator,
                        headerData = Screen.LightconeListPage.headerData
                    )
                }
            )
        }

        scene(
            route = Screen.RelicListPage.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.RelicListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    RelicListPage(
                        navigator = navigator,
                        headerData = Screen.RelicListPage.headerData
                    )
                }
            )
        }

        scene(
            //?fileName={fileName}&combatType={combatType}&path={path}&charId={charId}
            route = "${Screen.CharacterInfoPage.route}/{charName}",
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.CharacterInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    CharacterInfoPage(
                        navigator = navigator,
                        headerData = Screen.CharacterInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        scene(
            //?fileName={fileName}&path={path}
            route = "${Screen.LightconeInfoPage.route}/{lcName}",
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.LightconeInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    LightconeInfoPage(
                        navigator = navigator,
                        headerData = Screen.LightconeInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        //?fileName={fileName}
        scene(
            route = "${Screen.RelicInfoPage.route}/{relicName}",
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.RelicInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    RelicInfoPage(
                        navigator = navigator,
                        headerData = Screen.RelicInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        scene(
            route = Screen.SettingScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.SettingScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    SettingScreen(
                        navigator = navigator,
                        headerData = Screen.SettingScreen.headerData
                    )
                }
            )
        }

        scene(
            route = Screen.BackgroundSettingScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.BackgroundSettingScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    BackgroundSettingScreen(
                        navigator = navigator,
                        headerData = Screen.BackgroundSettingScreen.headerData
                    )
                }
            )
        }

        //?serverId={serverId}
        scene(
            route = Screen.HoyolabLoginPageScreen.route,
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.HoyolabLoginPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    HoyolabLoginPageScreen(
                        navigator = navigator,
                        headerData = Screen.HoyolabLoginPageScreen.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                }
            )
        }

        scene(
            route = Screen.EventListPageScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.EventListPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    EventListPageScreen(
                        navigator = navigator,
                        headerData = Screen.EventListPageScreen.headerData
                    )
                })
        }

        //?eventId={eventId}
        scene(
            route = Screen.EventContentPageScreen.route,
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.EventContentPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    EventContentPageScreen(
                        navigator = navigator,
                        headerData = Screen.EventContentPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
            )
        }
        scene(
            route = Screen.MapPageScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.MapPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    MapPageScreen(
                        navigator = navigator,
                        headerData = Screen.MapPageScreen.headerData
                    )
                }
            )
        }

        //?uid={uid}
        scene(
            route = Screen.UserInfoPageScreen.route,
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.UserInfoPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    UserInfoPageScreen(
                        navigator = navigator,
                        headerData = Screen.UserInfoPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
            )
        }

        //?uid={uid}&charId={charId}
        scene(
            route = Screen.UserCharacterPageScreen.route,
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.UserCharacterPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    UserCharacterPageScreen(
                        navigator = navigator,
                        headerData = Screen.UserCharacterPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
            )
        }
        scene(
            route = Screen.UIDSearchPageScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.UIDSearchPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    UIDSearchPageScreen(
                        navigator = navigator,
                        headerData = Screen.UIDSearchPageScreen.headerData
                    )
                }
            )
        }
        scene(
            route = Screen.MemoryOfChaosMissionPageScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.MemoryOfChaosMissionPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    MemoryOfChaosMissionPageScreen(
                        navigator = navigator,
                        headerData = Screen.MemoryOfChaosMissionPageScreen.headerData
                    )
                }
            )
        }
        //?uid={uid}
        scene(
            route = Screen.BattleChroniclePageScreen.route,
            navTransition = navTransition
        ) { backStackEntry ->
            RootContent(
                screen = Screen.BattleChroniclePageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    BattleChroniclePageScreen(
                        navigator = navigator,
                        headerData = Screen.BattleChroniclePageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                }
            )
        }
        scene(
            route = Screen.PureFictionMissionPageScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.PureFictionMissionPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    PureFictionMissionPageScreen(
                        navigator = navigator,
                        headerData = Screen.PureFictionMissionPageScreen.headerData
                    )
                }
            )
        }

        //For Testing

        scene(
            route = Screen.BlankScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.BlankScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    BlankScreen(
                        navigator = navigator,
                        headerData = Screen.BlankScreen.headerData
                    )
                }
            )
        }
        scene(
            route = Screen.WithBGScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.WithBGScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    WithBGScreen(
                        navigator = navigator,
                        headerData = Screen.WithBGScreen.headerData,
                    )
                }
            )
        }
        scene(
            route = Screen.WithBGHeaderScreen.route, navTransition = navTransition) {
            RootContent(
                screen = Screen.WithBGHeaderScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    WithBGScreen(
                        navigator = navigator,
                        headerData = Screen.WithBGHeaderScreen.headerData,
                        haveHeader = true
                    )
                }
            )
        }

    }
}

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
fun RootContent(
    screen: Screen,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
    page: @Composable () -> Unit
) {
    println("I'm going to show ${screen.route}")
    val hazeStateRoot = remember { HazeState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState!!)
        },
    ) {
        MakeBackground(screen = screen)
        //Landscape can do ... ?
        Box(
            modifier = Modifier.haze(hazeStateRoot)
        ) {
            page()
        }

        PomPomPopupUI(hazeState = hazeStateRoot)

        Toaster(
            state = toastInstance,
            richColors = true,
            maxVisibleToasts = 10,
            alignment = Alignment.BottomCenter,
            showCloseButton = true,
            darkTheme = true,
        )
    }
}
