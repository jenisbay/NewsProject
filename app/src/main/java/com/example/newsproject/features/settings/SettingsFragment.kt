package com.example.newsproject.features.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.newsproject.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.layout_settings, rootKey)
    }
}