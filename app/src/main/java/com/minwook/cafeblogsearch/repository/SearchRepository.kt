package com.minwook.cafeblogsearch.repository

import com.minwook.cafeblogsearch.data.BlogResponse
import com.minwook.cafeblogsearch.data.CafeResponse
import com.minwook.cryptocoinsproject.network.ServerAPI
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val serverAPI: ServerAPI
) {

    fun getBlogSearchResult(query: String): Single<BlogResponse> {
        return serverAPI.getBlogSearchResult(query)
    }

    fun getCafeSearchResult(query: String): Single<CafeResponse> {
        return serverAPI.getCafeSearchResult(query)
    }
}