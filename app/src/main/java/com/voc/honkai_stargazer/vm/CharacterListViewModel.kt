package com.voc.honkai_stargazer.vm

import android.content.res.AssetManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.voc.honkai_stargazer.models.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject

var searchResults: List<CharacterEntity> by mutableStateOf(listOf())
    private set
@HiltViewModel
class CharacterListViewModel @Inject constructor() : ViewModel() {
    val gson: Gson = Gson()

}