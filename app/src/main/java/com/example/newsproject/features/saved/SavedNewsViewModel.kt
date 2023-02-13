package com.example.newsproject.features.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.db.ArticleDataBase
import com.example.newsproject.data.models.Article
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(private val articleDataBase: ArticleDataBase) : ViewModel() {

    private val dao = articleDataBase.dao()

    private var _news: LiveData<List<Article>> = dao.getAllArticles()
    val news: LiveData<List<Article>> get() = _news

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            dao.delete(article)
        }
    }

}