package com.minwook.cafeblogsearch.repository

import com.minwook.cafeblogsearch.data.BlogResponse
import com.minwook.cafeblogsearch.data.CafeResponse
import com.minwook.cafeblogsearch.network.ServerAPI
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val serverAPI: ServerAPI
) {

    fun getBlogSearchResult(query: String, page: Int): Single<BlogResponse> {
        return serverAPI.getBlogSearchResult(query, page = page)
    }

    fun getCafeSearchResult(query: String, page: Int): Single<CafeResponse> {
        return serverAPI.getCafeSearchResult(query, page = page)
    }
}