package com.example.newsproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*


class LanguageHelper{
    companion object {
        @SuppressLint("SuspiciousIndentation")
        fun translate(context: Context?, newLocale: Locale) {
            var context: Context? = context
            val res: Resources = context!!.resources
            val configuration: Configuration = res.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                context.createConfigurationContext(configuration)
            } else
                configuration.setLocale(newLocale)
                res.updateConfiguration(configuration, res.displayMetrics)
        }
    }
}