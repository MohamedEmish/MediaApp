package com.amosh.pulse.media.domain.source

import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MediaRemoteSource {
    fun getHomeSections(page: Int): Flow<Response<HomeSectionsResponse>>
    fun getHomeSearchSections(query : String): Flow<Response<HomeSectionsResponse>>
}