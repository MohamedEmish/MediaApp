package com.amosh.pulse.core.data

import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.source.InMemorySource
import com.amosh.pulse.core.domain.source.LocalDataSource
import com.amosh.pulse.core.domain.source.RemoteDataSource
import com.amosh.pulse.core.domain.utils.mapDataOrThrow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val inMemorySource: InMemorySource
) : Repository {
    override fun getHomeSections(page: Int) =
        remoteDataSource.getHomeSections(page)
            .mapDataOrThrow()

    override fun getHomeSearchSections(query : String) =
        remoteDataSource.getHomeSearchSections(query)
            .mapDataOrThrow()
}