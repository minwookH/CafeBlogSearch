package com.minwook.cafeblogsearch.network

import com.minwook.cafeblogsearch.data.BlogResponse
import com.minwook.cafeblogsearch.data.CafeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    @GET("/v2/search/blog")
    fun getBlogSearchResult(@Query("query") query: String, @Query("sort") sort: String = "accuracy", @Query("page") page: Int = 1, @Query("size") size: Int = 25): Single<BlogResponse>

    @GET("/v2/search/cafe")
    fun getCafeSearchResult(@Query("query") query: String, @Query("sort") sort: String = "accuracy", @Query("page") page: Int = 1, @Query("size") size: Int = 25): Single<CafeResponse>
}
