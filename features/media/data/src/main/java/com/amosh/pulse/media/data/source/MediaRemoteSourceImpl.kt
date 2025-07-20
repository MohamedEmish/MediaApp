package com.amosh.pulse.media.data.source

import com.amosh.pulse.core.data.dataSource.api.AppApiService
import com.amosh.pulse.media.domain.source.MediaRemoteSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaRemoteSourceImpl @Inject constructor(
    private val apiService: AppApiService,
) : MediaRemoteSource {
    override fun getHomeSections(page: Int) = flow {
        emit(apiService.getHomeSections(page))
    }

    override fun getHomeSearchSections(query: String) = flow {
        emit(apiService.getHomeSearchSections(query))
    }
}