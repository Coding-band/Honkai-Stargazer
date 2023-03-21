package com.voc.honkai_stargazer.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.vm.MainViewModel
import com.voc.honkai_stargazer.vm.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    Text("Settings Screen")
}