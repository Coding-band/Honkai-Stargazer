package com.voc.honkai_stargazer.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.voc.honkai_stargazer.data.repo.RepositoryImpl
import com.voc.honkai_stargazer.models.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    private val _jsonString = MutableStateFlow<String>("")

    var bottomBarVisibility: Boolean by mutableStateOf(true)
        private set

    fun setCurrentString(screen: String) = viewModelScope.launch {
        _jsonString.value = screen
        repository.insertCharacters(fromJsonString(screen))
    }

    private fun fromJsonString(jsonString: String): List<CharacterEntity> {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<CharacterEntity?>?>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun setBottomBarVisibility(b: Boolean) = viewModelScope.launch {
        bottomBarVisibility = b
    }

}