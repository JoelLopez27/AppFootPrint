package com.example.appfootprint.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appfootprint.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT count(*) FROM articles")
    fun getSizeRows(): LiveData<Int>
}