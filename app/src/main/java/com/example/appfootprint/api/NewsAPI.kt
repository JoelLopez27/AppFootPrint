package com.example.appfootprint.api


import com.example.appfootprint.models.NewsResponse
import com.example.appfootprint.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    
    @GET("v2/everything")

    suspend fun getBreakingNews(
        @Query("q")
        selectedTopic: String = "calentamiento+global",
        @Query("language")
        selectedLanguage: String = "es",
        @Query("sortBy")
        comeFirst: String = "publishedAt",
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        numNews: Int = 60,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}
