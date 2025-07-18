package com.amosh.pulse.core.domain.source

import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAppLanguage(): Flow<SupportedLanguages>
    suspend fun updateAppLanguage(language: SupportedLanguages)

    fun getAppTheme(): Flow<SupportedTheme>
    suspend fun updateAppTheme(theme: SupportedTheme)

    fun getUserData(): Flow<UserData>

    suspend fun updateName(name: String)
    suspend fun updateProfilePic(pic: String)
}