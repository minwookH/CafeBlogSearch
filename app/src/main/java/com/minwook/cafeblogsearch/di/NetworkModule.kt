package com.minwook.cafeblogsearch.di

import com.minwook.cafeblogsearch.network.ServerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideServerAPI(retrofit: Retrofit): ServerAPI {
        return retrofit.create(ServerAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val build = it.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK 35224178203dcff2e87b7a937c8c33ae")
                    // Feel free to add any other headers
                    .url(it.request().url())
                    .build()

                it.proceed(build)
            }
            .build()
    }

}