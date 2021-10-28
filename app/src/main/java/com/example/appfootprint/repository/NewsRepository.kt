package com.example.appfootprint.repository

import com.example.appfootprint.api.RetrofitInstance
import com.example.appfootprint.db.ArticleDatabase
import com.example.appfootprint.models.Article
import retrofit2.http.Query

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(selectedTopic: String, selectedLanguage: String, comeFisrt: String,
                                pageNumber: Int, numNews: Int) = RetrofitInstance.api.getBreakingNews(selectedTopic, selectedLanguage, comeFisrt, pageNumber, numNews)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}