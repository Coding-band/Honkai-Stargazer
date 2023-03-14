package com.voc.honkai_stargazer.vm

import androidx.lifecycle.ViewModel
import com.voc.honkai_stargazer.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _currentScreen = MutableStateFlow<Screens>(Screens.BottomNavigationScreens.Home)
    val currentScreen get() = _currentScreen.asStateFlow()

    fun setCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }

}