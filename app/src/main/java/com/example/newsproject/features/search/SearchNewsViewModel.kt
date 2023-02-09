package com.example.newsproject.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsproject.data.Repository
import com.example.newsproject.data.models.Article
import javax.inject.Inject


class SearchNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _news: MutableLiveData<List<Article>> = MutableLiveData()
    val news: LiveData<List<Article>> get() = _news

    suspend fun getNewsByQuery(query: String){
        if (query.isEmpty()){
            return
        }
        val response = repository.getNewsByQuery(query)
        if (response.isSuccessful){
            response.body()?.let {
                _news.postValue(it.articles)
            }
        }
    }

}