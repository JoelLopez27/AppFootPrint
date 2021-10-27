package com.example.appfootprint.api

interface NewsAPI {

import com.example.appfootprint.models.NewsResponse
import com.example.appfootprint.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
    @GET("v2/everything")

    suspend fun getBreakingNews(
        @Query("q")
        selectedTopic: String = "medio-ambiente",
        @Query("language")
        selectedLanguage: String = "es",
        @Query("sortBy")
        comeFirst: String = "publishedAt",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

}