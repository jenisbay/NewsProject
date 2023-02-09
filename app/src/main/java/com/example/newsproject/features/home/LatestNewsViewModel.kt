package com.example.newsproject.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsproject.data.Repository
import com.example.newsproject.data.models.Article
import com.example.newsproject.data.models.NewsResponse
import com.example.newsproject.data.network.Resource
import retrofit2.Response
import javax.inject.Inject


class LatestNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _latestNews: MutableLiveData<List<Article>> = MutableLiveData()
    val latestNews: LiveData<List<Article>> get() = _latestNews

    private val _category: MutableLiveData<String> = MutableLiveData("general")
    val category: MutableLiveData<String> = _category

    suspend fun getLatestNewsByCategory(category: String, language: String) {
        when (val resource = repository.getLatestNews(category, language)) {
            is Resource.Success -> {
                _latestNews.postValue(resource.data?.toList())
            }
            is Resource.Error -> {

            }
        }

    }

    fun setCategory(category: String) {
        _category.postValue(category)
    }

}