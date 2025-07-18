package com.amosh.pulse.ui.ext

import com.amosh.pulse.R
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages.AR
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages.EN
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import java.util.Locale

val SupportedLanguages.textRes
    get() = when (this) {
        EN -> R.string.english
        AR -> R.string.arabic
    }

val SupportedTheme.textRes
    get() = when (this) {
        SupportedTheme.LIGHT -> R.string.light
        SupportedTheme.DARK -> R.string.dark
        SupportedTheme.SYSTEM -> R.string.follow_system
    }

val SupportedLanguages.locale
    get() = when (this) {
        EN -> Locale.forLanguageTag("en")
        AR -> Locale.forLanguageTag("ar")
    }