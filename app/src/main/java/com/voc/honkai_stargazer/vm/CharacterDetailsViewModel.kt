package com.voc.honkai_stargazer.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voc.honkai_stargazer.data.repo.RepositoryImpl
import com.voc.honkai_stargazer.models.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {
    var character: CharacterEntity? by mutableStateOf(null)
        private set

    var characterTabIndex: Int by mutableStateOf(0)

    fun getCharacter(name: String) = viewModelScope.launch {
        repository.getCharacter(name).collectLatest {
            character = it
        }
    }

    fun setCharacterTabIndex(index : Int) = viewModelScope.launch {
        characterTabIndex = index
    }
}