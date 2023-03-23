package com.voc.honkai_stargazer.vm

import android.content.res.AssetManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.voc.honkai_stargazer.models.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor() : ViewModel() {

    var characterList: List<CharacterEntity> by mutableStateOf(listOf())
        private set



    fun fromJsonString(jsonString: String) {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<CharacterEntity?>?>() {}.type

        characterList = gson.fromJson(jsonString,listType)
    }

}