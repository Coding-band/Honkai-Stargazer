package com.voc.honkai_stargazer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.voc.honkai_stargazer.models.CharacterEntity

@Database(
    entities = [
        CharacterEntity::class,
    ],
    exportSchema = false,
    version = 1,
)
abstract class StargazerDB : RoomDatabase() {
    abstract fun getDao(): StargazerDao
}