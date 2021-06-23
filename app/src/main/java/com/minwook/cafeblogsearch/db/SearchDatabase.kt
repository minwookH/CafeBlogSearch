package com.minwook.cafeblogsearch.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistoryEntity::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDAO(): SearchDAO

}