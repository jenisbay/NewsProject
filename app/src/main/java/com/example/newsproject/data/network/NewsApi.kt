package com.example.newsproject.data.network

import com.example.newsproject.data.models.NewsResponse
import com.example.newsproject.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getLatestNewsByCategory(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("category") category: String,
        @Query("language") language: String,
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun getNewsByQuery(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("q") query: String,
    ): Response<NewsResponse>
}