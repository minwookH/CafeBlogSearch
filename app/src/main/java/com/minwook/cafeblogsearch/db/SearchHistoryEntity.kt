package com.minwook.cafeblogsearch.db

import androidx.room.Entity

@Entity(tableName = "SEARCH_HISTORY_TABLE", primaryKeys = ["searchText"])
data class SearchHistoryEntity(
    val searchText: String
)