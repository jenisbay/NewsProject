package com.example.newsproject

import android.app.Application
import com.example.newsproject.di.AppComponent
import com.example.newsproject.di.DaggerAppComponent

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

}