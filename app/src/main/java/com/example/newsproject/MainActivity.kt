package com.example.newsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.newsproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeNightMode()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun checkNightMode() {
        val isDarkTheme = PreferenceManager
            .getDefaultSharedPreferences(this)
            .getBoolean("darkTheme", false)
        setNightMode(isDarkTheme)
    }

    private fun observeNightMode() {
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener { sharedPreferences, _ ->
                val isDarkTheme = sharedPreferences?.getBoolean("darkTheme", false)
                setNightMode(isDarkTheme)
            }
        checkNightMode()
    }

    private fun setNightMode(isDarkTheme: Boolean?) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme == true)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}