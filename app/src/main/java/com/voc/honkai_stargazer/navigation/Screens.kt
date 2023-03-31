package com.voc.honkai_stargazer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.voc.honkai_stargazer.R

sealed class Screens(val route: String, val title: Int) {

    sealed class BottomNavigationScreens(
        route: String,
        titleId: Int,
        val icon: ImageVector? = null,
        val drawableIcon: Int? = null,
    ) : Screens(route, titleId) {
        object Home: BottomNavigationScreens("home", R.string.bottom_bar_home_text, null, R.drawable.home_symbol_filled_rounded)
        object Characters: BottomNavigationScreens("character",R.string.bottom_bar_characters_text, Icons.Default.Group)
        object Settings: BottomNavigationScreens("settings",R.string.bottom_bar_settings_text, Icons.Default.Settings)
    }
}

val screensInBottomNavigation = listOf(
    Screens.BottomNavigationScreens.Home,
    Screens.BottomNavigationScreens.Characters,
    Screens.BottomNavigationScreens.Settings,
    )
