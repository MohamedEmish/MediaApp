package com.amosh.pulse.core.domain.useCases

import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import javax.inject.Inject

class AppThemeUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getAppTheme() =
        repository.getAppTheme()

    suspend fun updateAppTheme(theme: SupportedTheme) =
        repository.updateAppTheme(theme)
}