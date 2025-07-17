package com.amosh.pulse.core.domain

import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getHomeSections(page: Int): Flow<HomeSectionsResponse>
    fun getHomeSearchSections(query : String): Flow<HomeSectionsResponse>

    fun getAppLanguage(): Flow<SupportedLanguages>
    suspend fun updateAppLanguage(language: SupportedLanguages)
}