package com.amosh.pulse.core.domain.useCases

import com.amosh.pulse.core.domain.Repository
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import javax.inject.Inject

class AppLanguageUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getAppLanguage() =
        repository.getAppLanguage()

    suspend fun updateAppLanguage(language: SupportedLanguages) =
        repository.updateAppLanguage(language)
}