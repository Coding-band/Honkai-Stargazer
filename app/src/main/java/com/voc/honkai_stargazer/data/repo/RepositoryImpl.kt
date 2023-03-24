package com.voc.honkai_stargazer.data.repo

import com.voc.honkai_stargazer.data.local.StargazerDao
import com.voc.honkai_stargazer.models.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val stargazerDao: StargazerDao
) : Repository {
    override suspend fun insertCharacters(characterEntityList: List<CharacterEntity>) = withContext(Dispatchers.IO) {
        stargazerDao.insertCharacterList(characterEntityList)
    }

    override suspend fun getCharacters(query: String): Flow<List<CharacterEntity>> = withContext(Dispatchers.IO) {
        return@withContext stargazerDao.getCharacters(query)
    }
}