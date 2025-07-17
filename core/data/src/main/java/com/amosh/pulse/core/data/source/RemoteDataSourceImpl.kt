package com.amosh.pulse.core.data.source

import com.amosh.pulse.core.data.dataSource.api.AppApiService
import com.amosh.pulse.core.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: AppApiService
) : RemoteDataSource {
    override fun getHomeSections(page: Int) = flow {
        emit(apiService.getHomeSections(page))
    }

    override fun getHomeSearchSections(query: String) = flow {
        emit(apiService.getHomeSearchSections(query))
    }
}