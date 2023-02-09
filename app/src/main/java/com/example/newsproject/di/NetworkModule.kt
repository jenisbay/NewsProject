package com.example.newsproject.di

import com.example.newsproject.data.Repository
import com.example.newsproject.data.network.NewsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import com.example.newsproject.utils.Constants.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideRepository(newsApi: NewsApi): Repository{
        return Repository(newsApi)
    }

}