package com.voc.honkai_stargazer.vm

import android.content.res.AssetManager
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    var characterList: List<CharacterEntity> by mutableStateOf(listOf())
        private set
    fun getCharacters(query: String) = viewModelScope.launch {
        repository.getCharacters(query).collectLatest {
            characterList = it
        }
    }

}