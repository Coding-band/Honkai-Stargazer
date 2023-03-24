package com.voc.honkai_stargazer.data.repo

import com.voc.honkai_stargazer.models.CharacterEntity

interface Repository {
    suspend fun insertCharacters(characters: List<CharacterEntity>)
}