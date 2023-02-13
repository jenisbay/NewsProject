package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.BaseApplication
import com.example.newsproject.data.db.ArticleDataBase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideArticleDatabase(application: Application): ArticleDataBase {
        return ArticleDataBase.invoke(application)
    }


}