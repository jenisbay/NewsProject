package com.example.newsproject.features.settings

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.newsproject.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.layout_settings, rootKey)

        val preference = findPreference<Preference>("navigateToSavedNewsFragment")
        preference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            view?.findNavController()?.navigate(R.id.action_settingsFragment_to_savedNewsFragment)
            true
        }

    }

}