package com.voc.honkai_stargazer.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.voc.honkai_stargazer.data.local.StargazerDB
import com.voc.honkai_stargazer.data.local.StargazerDao
import com.voc.honkai_stargazer.data.repo.Repository
import com.voc.honkai_stargazer.data.repo.RepositoryImpl
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object AppModule {

    @Provides
    @Singleton
    fun provideAmberDB(@ApplicationContext context: Context): StargazerDao = Room.databaseBuilder(
        context,
        StargazerDB::class.java,
        "stargazer_db"
    )
        .fallbackToDestructiveMigration()
        .build()
        .getDao()

    @Provides
    @Singleton
    fun provideRepository( amberDao: StargazerDao) : Repository {
        return RepositoryImpl( amberDao)
    }
}