package com.voc.honkai_stargazer.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.voc.honkai_stargazer.navigation.Screens
import com.voc.honkai_stargazer.vm.CharacterListViewModel
import com.voc.honkai_stargazer.vm.HomeViewModel
import com.voc.honkai_stargazer.vm.MainViewModel

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    mainViewModel.setCurrentScreen(Screens.BottomNavigationScreens.Characters)
    Text("Character List Screen")
}