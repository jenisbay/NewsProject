package com.example.newsproject

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.newsproject.AppSettings.Companion.setNightMode
import com.example.newsproject.databinding.ActivityMainBinding
import com.example.newsproject.utils.Constants.LANGUAGE
import com.example.newsproject.utils.Constants.NIGHT_MODE
import java.util.*


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    private val sharedPreferenceChangeListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                NIGHT_MODE -> {
                    setNightMode(sharedPreferences)
                }
                LANGUAGE -> {
                    setLocale(sharedPreferences)
                }
            }
        }

    private fun setNightMode(sharedPreferences: SharedPreferences) {
        val isNightMode = sharedPreferences.getBoolean(NIGHT_MODE, false)
        AppSettings.setNightMode(isNightMode)
    }

    private fun setLocale(sharedPreferences: SharedPreferences) {
        val language = sharedPreferences.getString(LANGUAGE, "en").toString()
        AppSettings.setLocale(language)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}