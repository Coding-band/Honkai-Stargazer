package com.voc.honkai_stargazer.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.voc.honkai_stargazer.navigation.NavigationHost
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.navigation.screensInBottomNavigation
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun AppScaffold(
    jsonString: String,
    viewModel: MainViewModel = hiltViewModel()
){
    viewModel.setCurrentString(jsonString)
    val navController = rememberNavController()
    Scaffold(
        topBar = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                AndroidView(
//                    modifier = Modifier.fillMaxWidth(),
//                    factory = { context ->
//                        AdView(context).apply {
//                            setAdSize(AdSize.BANNER)
//                            adUnitId = "ca-app-pub-1889384269259267/4278227521"
//                            loadAd(AdRequest.Builder().build())
//                        }
//                    }
//                )
//            }
        },
        bottomBar = {
            if (viewModel.bottomBarVisibility) {
                BottomBar(navController = navController, screens = screensInBottomNavigation)
            }
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
                    if (screen.icon != null) {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = stringResource(id = screen.title),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        val vector = ImageVector.vectorResource(id = screen.drawableIcon!!)
                        Icon(
                            imageVector = vector,
                            contentDescription = stringResource(id = screen.title),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                },
                label = {Text(text = stringResource(id = screen.title))},
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}