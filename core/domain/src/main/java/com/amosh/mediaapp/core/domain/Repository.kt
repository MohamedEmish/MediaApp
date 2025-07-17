package com.amosh.mediaapp.core.domain

import com.amosh.mediaapp.core.domain.model.HomeSectionsResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getHomeSections(page: Int): Flow<HomeSectionsResponse>
    fun getHomeSearchSections(query : String): Flow<HomeSectionsResponse>
}