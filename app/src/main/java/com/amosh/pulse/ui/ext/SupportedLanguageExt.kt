package com.amosh.pulse.ui.ext

import com.amosh.pulse.R
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages.AR
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages.EN
import java.util.Locale

val SupportedLanguages.textRes
    get() = when (this) {
        EN -> R.string.english
        AR -> R.string.arabic
    }

val SupportedLanguages.locale
    get() = when (this) {
        EN -> Locale.forLanguageTag("en")
        AR -> Locale.forLanguageTag("ar")
    }