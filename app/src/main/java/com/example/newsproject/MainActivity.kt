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
import com.example.newsproject.databinding.ActivityMainBinding
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
        observeSharedPreferences()
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }


    private val sharedPreferenceChangeListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Toast.makeText(this, key, Toast.LENGTH_SHORT).show()

            setLocale(sharedPreferences)
            setNightMode(sharedPreferences)
            recreate()
        }

    private fun observeSharedPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)

        setLocale(sharedPreferences)
        setNightMode(sharedPreferences)
    }


    private fun setNightMode(sharedPreferences: SharedPreferences) {

        val isDarkTheme = sharedPreferences.getBoolean("darkTheme", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

    }

    private fun setLocale(sharedPreferences: SharedPreferences) {

        val language = sharedPreferences.getString("language", "en").toString()
        val locale = Locale(language)
        Locale.setDefault(locale)
        LanguageHelper.translate(this, locale)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}