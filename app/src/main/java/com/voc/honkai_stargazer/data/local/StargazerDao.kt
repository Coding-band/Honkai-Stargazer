package com.voc.honkai_stargazer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voc.honkai_stargazer.models.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StargazerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterList(characters: List<CharacterEntity>)

    @Query("select * from character_db where name like '%' || :query  || '%' ")
    fun getCharacters(query: String): Flow<List<CharacterEntity>>
}