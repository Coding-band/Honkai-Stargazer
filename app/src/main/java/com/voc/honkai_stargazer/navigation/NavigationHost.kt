package com.voc.honkai_stargazer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.voc.honkai_stargazer.ui.screens.CharacterDetailsScreen
import com.voc.honkai_stargazer.ui.screens.CharacterListScreen
import com.voc.honkai_stargazer.ui.screens.HomeScreen
import com.voc.honkai_stargazer.ui.screens.SettingsScreen
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.BottomNavigationScreens.Home.route
    ) {
        composable(Screens.BottomNavigationScreens.Home.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(Screens.BottomNavigationScreens.Characters.route) {
            CharacterListScreen(
                navController = navController,
                mainViewModel = viewModel
            )
        }
        composable(Screens.BottomNavigationScreens.Settings.route) {
            SettingsScreen(
                navController = navController
            )
        }

        composable("characterDetails/{name}") { navBackStackEntry ->
            val characterName = navBackStackEntry.arguments?.getString("name")
            if (characterName != null) {
                CharacterDetailsScreen(
                    characterName = characterName,
                    navController = navController,
                    mainViewModel = viewModel
                )
            }
        }
    }
}