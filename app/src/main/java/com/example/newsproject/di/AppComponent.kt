package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.features.home.LatestNewsFragment
import com.example.newsproject.features.saved.SavedNewsFragment
import com.example.newsproject.features.search.SearchNewsFragment
import com.example.newsproject.features.webview.WebViewActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun injectLatestNewsFragment(latestNewsFragment: LatestNewsFragment)
    fun injectSearchNewsFragment(searchNewsFragment: SearchNewsFragment)
    fun injectWebViewActivity(webViewActivity: WebViewActivity)
    fun injectSavedNewsFragment(savedNewsFragment: SavedNewsFragment)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}