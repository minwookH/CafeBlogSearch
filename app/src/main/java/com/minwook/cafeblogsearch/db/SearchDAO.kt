package com.minwook.cafeblogsearch.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SearchDAO {

    @Query("SELECT * FROM SEARCH_HISTORY_TABLE")
    fun getSearchHistoryList(): Single<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchText: SearchHistoryEntity)

}