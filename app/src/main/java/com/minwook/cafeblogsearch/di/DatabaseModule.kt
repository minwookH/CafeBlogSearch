package com.minwook.cafeblogsearch.di

import android.content.Context
import androidx.room.Room
import com.minwook.cafeblogsearch.db.SearchDAO
import com.minwook.cafeblogsearch.db.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideChannelDao(searchDatabase: SearchDatabase): SearchDAO {
        return searchDatabase.searchDAO()
    }
    
    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext appContext: Context): SearchDatabase {
        return Room.databaseBuilder(
            appContext,
            SearchDatabase::class.java,
            "search.db"
        ).build()
    }
}