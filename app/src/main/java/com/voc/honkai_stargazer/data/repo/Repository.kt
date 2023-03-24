package com.voc.honkai_stargazer.data.repo

import com.voc.honkai_stargazer.models.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun getCharacters(query: String): Flow<List<CharacterEntity>>
}