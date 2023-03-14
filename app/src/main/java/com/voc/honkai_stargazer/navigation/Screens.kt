package com.voc.honkai_stargazer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String) {

    sealed class BottomNavigationScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(route, title) {
        object Home: BottomNavigationScreens("home","Home", Icons.Filled.Home)
        object Characters: BottomNavigationScreens("character","Characters", Icons.Default.Group)
        object Settings: BottomNavigationScreens("settings","Settings", Icons.Default.Settings)
    }
}

val screensInBottomNavigation = listOf(
    Screens.BottomNavigationScreens.Home,
    Screens.BottomNavigationScreens.Characters,
    Screens.BottomNavigationScreens.Settings,
    )
