package com.example.newsproject

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.newsproject.di.AppComponent
import com.example.newsproject.di.DaggerAppComponent
import java.util.*

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setApplicationSettings()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    private fun setApplicationSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        AppSettings.setNightMode(sharedPreferences.getBoolean("nightMode", false))
        AppSettings.setLocale(sharedPreferences.getString("language", "en").toString())
    }

}