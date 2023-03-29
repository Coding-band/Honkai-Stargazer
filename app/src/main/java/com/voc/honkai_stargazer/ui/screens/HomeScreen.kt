package com.voc.honkai_stargazer.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.vm.HomeViewModel
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    Text("Home Screen")
}