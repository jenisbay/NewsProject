package com.example.newsproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsproject.data.models.Article


@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun dao(): ArticleDao

    companion object {
        @Volatile
        var INSTANCE: ArticleDataBase? = null
        private val LOCK = Any()


        fun invoke(context: Context) = synchronized(LOCK) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) = Room
            .databaseBuilder(
                context, ArticleDataBase::class.java,
                "article.db"
            ).build()
    }
}