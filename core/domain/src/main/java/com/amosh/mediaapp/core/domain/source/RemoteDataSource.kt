package com.amosh.mediaapp.core.domain.source

import com.amosh.mediaapp.core.domain.model.HomeSectionsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    fun getHomeSections(page: Int): Flow<Response<HomeSectionsResponse>>
    fun getHomeSearchSections(query : String): Flow<Response<HomeSectionsResponse>>
}