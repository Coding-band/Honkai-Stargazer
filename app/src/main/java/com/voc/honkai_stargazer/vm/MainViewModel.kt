package com.voc.honkai_stargazer.vm

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.voc.honkai_stargazer.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _jsonString = MutableStateFlow<String>("")
    val jsonString get() = _jsonString.asStateFlow()

    fun setCurrentString(screen: String) {
        _jsonString.value = screen
    }

}