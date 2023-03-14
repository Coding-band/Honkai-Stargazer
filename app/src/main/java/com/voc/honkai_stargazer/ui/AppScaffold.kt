package com.voc.honkai_stargazer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.voc.honkai_stargazer.navigation.NavigationHost
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.navigation.screensInBottomNavigation
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun AppScaffold(
    viewModel: MainViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, screens = screensInBottomNavigation)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationHost(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun BottomBar(navController: NavController, screens: List<Screens.BottomNavigationScreens>,) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                    )
                },
                label = {Text(text = screen.title)},
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}