/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package utils.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import components.HeaderData
import components.defaultHeaderData
import files.ChangeWallPaper
import files.CharacterList
import files.Event
import files.HaveNotUsed
import files.LightconeList
import files.Login
import files.Map
import files.RelicList
import files.Res
import files.Setting
import files.UserInfoGameData
import files.phorphos_baseball_cap_fill
import files.phorphos_film_slate_regular
import files.phorphos_game_controller_regular
import files.phorphos_house_fill
import files.phorphos_map_trifold_fill
import files.phorphos_person_fill
import files.phorphos_sliders_horizontal_fill
import files.phorphos_sword_fill
import screens.BackgroundSettingScreen
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
import screens.RelicInfoPage
import screens.RelicListPage
import screens.SettingScreen
import screens.SplashPage
import screens.UserCharacterPageScreen
import screens.UserInfoPageScreen

//This should not be there, but CharacterCard need it in clickable, without using @Composable ...
lateinit var navControllerInstance : NavController
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
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val errorMessage by remember { mutableStateOf<String?>(null) }
    //val defaultEnterTransition = EnterTransition.None
    //val defaultExitTransition = ExitTransition.None
    val defaultEnterTransition = EnterTransition.None
    val defaultExitTransition = ExitTransition.None
    navControllerInstance = navController
    NavHost(navController = navController, startDestination = Screen.SplashPage.route) {
        composable(
            route = Screen.SplashPage.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            SplashPage(navController = navController, headerData = Screen.HomePage.headerData)
        }
        composable(
            route = Screen.HomePage.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.HomePage,
                snackbarHostState = snackbarHostState,
                page = {
                    HomePage(
                        navController = navController,
                        headerData = Screen.HomePage.headerData
                    )
                })
        }
        composable(
            route = Screen.CharacterListPage.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.CharacterListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    CharacterListPage(
                        navController = navController,
                        headerData = Screen.CharacterListPage.headerData
                    )
                })
        }
        composable(
            route = Screen.LightconeListPage.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.LightconeListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    LightconeListPage(
                        navController = navController,
                        headerData = Screen.LightconeListPage.headerData
                    )
                })
        }
        composable(
            route = Screen.RelicListPage.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.RelicListPage,
                snackbarHostState = snackbarHostState,
                page = {
                    RelicListPage(
                        navController = navController,
                        headerData = Screen.RelicListPage.headerData
                    )
                })
        }
        composable(
            route = "${Screen.CharacterInfoPage.route}/{charName}/?fileName={fileName}&combatType={combatType}&path={path}&charId={charId}",
            arguments = listOf(
                (navArgument("fileName") { type = NavType.StringType }),
                (navArgument("charName") { type = NavType.StringType }),
                (navArgument("combatType") { type = NavType.StringType }),
                (navArgument("path") { type = NavType.StringType }),
                (navArgument("charId") { type = NavType.StringType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.CharacterInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    CharacterInfoPage(
                        navController = navController,
                        headerData = Screen.CharacterInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                })
        }
        composable(
            route = "${Screen.LightconeInfoPage.route}/{lcName}/?fileName={fileName}&path={path}",
            arguments = listOf(
                (navArgument("fileName") { type = NavType.StringType }),
                (navArgument("lcName") { type = NavType.StringType }),
                (navArgument("path") { type = NavType.StringType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.LightconeInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    LightconeInfoPage(
                        navController = navController,
                        headerData = Screen.LightconeInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                })
        }
        composable(
            route = "${Screen.RelicInfoPage.route}/{relicName}/?fileName={fileName}",
            arguments = listOf(
                (navArgument("fileName") { type = NavType.StringType }),
                (navArgument("relicName") { type = NavType.StringType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.RelicInfoPage,
                snackbarHostState = snackbarHostState,
                page = {
                    RelicInfoPage(
                        navController = navController,
                        headerData = Screen.RelicInfoPage.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                })
        }

        composable(
            route = Screen.SettingScreen.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.SettingScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    SettingScreen(
                        navController = navController,
                        headerData = Screen.SettingScreen.headerData
                    )
                })
        }

        composable(
            route = Screen.BackgroundSettingScreen.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.BackgroundSettingScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    BackgroundSettingScreen(
                        navController = navController,
                        headerData = Screen.BackgroundSettingScreen.headerData
                    )
                })
        }
        composable(
            route = "${Screen.HoyolabLoginPageScreen.route}?serverId={serverId}",
            arguments = listOf(
                (navArgument("serverId") { type = NavType.StringType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.HoyolabLoginPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    HoyolabLoginPageScreen(
                        navController = navController,
                        headerData = Screen.HoyolabLoginPageScreen.headerData,
                        backStackEntry = backStackEntry,
                        snackbarHostState = snackbarHostState
                    )
                })
        }

        composable(
            route = Screen.EventListPageScreen.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.EventListPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    EventListPageScreen(
                        navController = navController,
                        headerData = Screen.EventListPageScreen.headerData
                    )
                })
        }

        composable(
            route = "${Screen.EventContentPageScreen.route}?eventId={eventId}",
            arguments = listOf(
                (navArgument("eventId") { type = NavType.IntType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.EventContentPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    EventContentPageScreen(
                        navController = navController,
                        headerData = Screen.EventContentPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                })
        }
        composable(
            route = Screen.MapPageScreen.route,
            enterTransition = { defaultEnterTransition },
            exitTransition = { defaultExitTransition }) {
            RootContent(
                screen = Screen.MapPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    MapPageScreen(
                        navController = navController,
                        headerData = Screen.MapPageScreen.headerData
                    )
                })
        }

        composable(
            route = "${Screen.UserInfoPageScreen.route}?uid={uid}",
            arguments = listOf(
                (navArgument("uid") { type = NavType.StringType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.UserInfoPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    UserInfoPageScreen(
                        navController = navController,
                        headerData = Screen.UserInfoPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                })
        }

        composable(
            route = "${Screen.UserCharacterPageScreen.route}?uid={uid}&charId={charId}",
            arguments = listOf(
                (navArgument("uid") { type = NavType.StringType }),
                (navArgument("charId") { type = NavType.IntType }),
            ),
            enterTransition = { defaultEnterTransition }, exitTransition = { defaultExitTransition }
        ) { backStackEntry ->
            RootContent(
                screen = Screen.UserCharacterPageScreen,
                snackbarHostState = snackbarHostState,
                page = {
                    UserCharacterPageScreen(
                        navController = navController,
                        headerData = Screen.UserCharacterPageScreen.headerData,
                        backStackEntry = backStackEntry,
                    )
                })
        }
    }
}

@Composable
fun RootContent(
    screen: Screen,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
    page: @Composable () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState!!)
        },
    ) {
        MakeBackground(screen = screen)
        //Landscape can do ... ?
        Box(
            modifier = Modifier
        ) {
            page()
        }
    }
}