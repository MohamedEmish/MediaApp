package com.amosh.pulse.core.domain

import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getHomeSections(page: Int): Flow<HomeSectionsResponse>
    fun getHomeSearchSections(query : String): Flow<HomeSectionsResponse>

    fun getAppLanguage(): Flow<SupportedLanguages>
    suspend fun updateAppLanguage(language: SupportedLanguages)

    fun getUserData(): Flow<UserData>

    suspend fun updateProfilePic(pic: String)
    suspend fun updateName(name: String)
}