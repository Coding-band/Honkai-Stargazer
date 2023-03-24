package com.voc.honkai_stargazer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.voc.honkai_stargazer.models.CharacterEntity

@Dao
interface StargazerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterList(characters: List<CharacterEntity>)
}