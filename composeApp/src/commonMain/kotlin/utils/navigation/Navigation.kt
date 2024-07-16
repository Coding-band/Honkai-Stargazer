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
import files.CharacterList
import files.LightconeList
import files.RelicList
import files.Res
import files.Setting
import files.europe
import files.phorphos_baseball_cap_fill
import files.phorphos_house_fill
import files.phorphos_person_fill
import files.phorphos_sliders_horizontal_fill
import files.phorphos_sword_fill
import screens.CharacterInfoPage
import screens.CharacterListPage
import screens.HomePage
import screens.LightconeInfoPage
import screens.LightconeListPage
import screens.MakeBackground
import screens.RelicInfoPage
import screens.RelicListPage
import screens.SettingScreen
import screens.SplashPage

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
        HeaderData(titleRId = Res.string.europe, titleIconId = Res.drawable.phorphos_person_fill)
    )
    data object LightconeInfoPage : Screen(
        "LightconeInfoPage",
        HeaderData(titleRId = Res.string.europe, titleIconId = Res.drawable.phorphos_sword_fill)
    )
    data object RelicInfoPage : Screen(
        "RelicInfoPage",
        HeaderData(titleRId = Res.string.europe, titleIconId = Res.drawable.phorphos_baseball_cap_fill)
    )
    data object SettingScreen : Screen(
        "SettingScreen",
        HeaderData(titleRId = Res.string.Setting, titleIconId = Res.drawable.phorphos_sliders_horizontal_fill)
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