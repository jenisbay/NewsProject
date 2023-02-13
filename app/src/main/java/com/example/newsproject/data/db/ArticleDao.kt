package com.example.newsproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsproject.data.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Update
    suspend fun update(article: Article)

}