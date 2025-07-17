package com.amosh.pulse.core.domain.source

import com.amosh.pulse.core.domain.model.UserData
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAppLanguage(): Flow<SupportedLanguages>
    suspend fun updateAppLanguage(language: SupportedLanguages)

    fun getUserData(): Flow<UserData>
}