package com.voc.honkai_stargazer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.voc.honkai_stargazer.R

sealed class Screens(val route: String, val title: String) {

    sealed class BottomNavigationScreens(
        route: String,
        title: String,
        val icon: ImageVector? = null,
        val drawableIcon: Int? = null,
    ) : Screens(route, title) {
        object Home: BottomNavigationScreens("home","Home", null, R.drawable.home_symbol_filled_rounded)
        object Characters: BottomNavigationScreens("character","Characters", Icons.Default.Group)
        object Settings: BottomNavigationScreens("settings","Settings", Icons.Default.Settings)
    }
}

val screensInBottomNavigation = listOf(
    Screens.BottomNavigationScreens.Home,
    Screens.BottomNavigationScreens.Characters,
    Screens.BottomNavigationScreens.Settings,
    )
