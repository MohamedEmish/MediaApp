package com.amosh.pulse.core.domain

import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getHomeSections(page: Int): Flow<HomeSectionsResponse>
    fun getHomeSearchSections(query : String): Flow<HomeSectionsResponse>
}