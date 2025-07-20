package com.amosh.pulse.media.data

import com.amosh.pulse.core.domain.utils.mapDataOrThrow
import com.amosh.pulse.media.domain.MediaRepository
import com.amosh.pulse.media.domain.source.MediaRemoteSource
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: MediaRemoteSource,
) : MediaRepository {
    override fun getHomeSections(page: Int) =
        remoteDataSource.getHomeSections(page)
            .mapDataOrThrow()

    override fun getHomeSearchSections(query: String) =
        remoteDataSource.getHomeSearchSections(query)
            .mapDataOrThrow()
}