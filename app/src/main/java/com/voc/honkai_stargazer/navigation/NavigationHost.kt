package com.voc.honkai_stargazer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                mainViewModel = viewModel
            )
        }
        composable(Screens.BottomNavigationScreens.Characters.route) {
            CharacterListScreen(
                mainViewModel = viewModel
            )
        }
        composable(Screens.BottomNavigationScreens.Settings.route) {
            SettingsScreen(
                mainViewModel = viewModel
            )
        }
    }
}