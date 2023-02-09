package com.example.newsproject.data

import com.example.newsproject.data.models.Article
import com.example.newsproject.data.models.NewsResponse
import com.example.newsproject.data.network.NewsApi
import com.example.newsproject.data.network.Resource
import com.example.newsproject.utils.Constants.API_KEY
import com.example.newsproject.utils.Constants.LANGUAGE
import retrofit2.Response
import java.io.IOException


class Repository(private val newsApi: NewsApi) {

    suspend fun getLatestNews(
        category: String,
        language: String,
    ): Resource<List<Article>> {

        lateinit var resource: Resource<List<Article>>
        try {
            val response = newsApi.getLatestNewsByCategory(category = category, language = language)
            if (response.isSuccessful){
                response.body()?.let {
                    resource = Resource.Success(it.articles)
                }
            } else {
                resource = Resource.Error(message = response.message())
            }
        } catch (e: IOException){
            resource = Resource.Error(message = "Please check your internet connection")
        } catch (e: Exception){
            resource = Resource.Error(message = e.message.toString())
        }
        return resource
    }

    suspend fun getNewsByQuery(query: String) : Response<NewsResponse>{
        return newsApi.getNewsByQuery(query = query)
    }
}