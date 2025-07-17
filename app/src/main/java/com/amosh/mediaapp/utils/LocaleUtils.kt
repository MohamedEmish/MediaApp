package com.amosh.mediaapp.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleUtils {
    fun setLocale(
        c: Context,
        language: String?,
    ) = updateResources(c, language ?: "en")

    @Suppress("DEPRECATION")
    private fun updateResources(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        context.resources.apply {
            val config = Configuration(configuration)
            context.createConfigurationContext(configuration)
            config.setLocale(locale)
            context.createConfigurationContext(config)
            context.resources.updateConfiguration(config, displayMetrics)
        }
    }
}