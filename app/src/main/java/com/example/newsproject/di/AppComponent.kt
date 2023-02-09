package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.features.home.LatestNewsFragment
import com.example.newsproject.features.search.SearchNewsFragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun injectLatestNewsFragment(latestNewsFragment: LatestNewsFragment)
    fun injectSearchNewsFragment(searchNewsFragment: SearchNewsFragment)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}