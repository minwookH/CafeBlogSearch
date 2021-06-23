package com.minwook.cafeblogsearch.repository

import com.minwook.cafeblogsearch.data.BlogResponse
import com.minwook.cafeblogsearch.data.CafeResponse
import com.minwook.cafeblogsearch.db.SearchHistoryEntity
import com.minwook.cafeblogsearch.network.ServerAPI
import com.minwook.cafeblogsearch.db.SearchDAO
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val serverAPI: ServerAPI,
    private val searchDAO: SearchDAO
) {

    fun getBlogSearchResult(query: String, page: Int): Single<BlogResponse> {
        return serverAPI.getBlogSearchResult(query, page = page)
    }

    fun getCafeSearchResult(query: String, page: Int): Single<CafeResponse> {
        return serverAPI.getCafeSearchResult(query, page = page)
    }

    fun getSearchHistory(): Single<List<SearchHistoryEntity>> {
        return searchDAO.getSearchHistoryList()
    }

    fun insertSearchHistory(text: String) {
        searchDAO.insert(SearchHistoryEntity(text))
    }
}