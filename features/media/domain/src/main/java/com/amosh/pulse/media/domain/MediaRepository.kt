package com.amosh.pulse.media.domain

import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getHomeSections(page: Int): Flow<HomeSectionsResponse>
    fun getHomeSearchSections(query : String): Flow<HomeSectionsResponse>
}