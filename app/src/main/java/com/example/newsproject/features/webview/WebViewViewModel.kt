package com.example.newsproject.features.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.db.ArticleDao
import com.example.newsproject.data.db.ArticleDataBase
import com.example.newsproject.data.models.Article
import kotlinx.coroutines.launch
import javax.inject.Inject


class WebViewViewModel @Inject constructor(
    private val articleDataBase: ArticleDataBase
) : ViewModel() {

    private val dao: ArticleDao = articleDataBase.dao()

    fun saveArticle(article: Article){
        viewModelScope.launch {
            dao.insert(article)
        }
    }

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            dao.delete(article)
        }
    }
}