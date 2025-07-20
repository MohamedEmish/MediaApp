package com.amosh.pulse.core.domain

import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAppLanguage(): Flow<SupportedLanguages>
    suspend fun updateAppLanguage(language: SupportedLanguages)

    fun getAppTheme(): Flow<SupportedTheme>
    suspend fun updateAppTheme(theme: SupportedTheme)

    fun getUserData(): Flow<UserData>

    suspend fun updateProfilePic(pic: String)
    suspend fun updateName(name: String)
}