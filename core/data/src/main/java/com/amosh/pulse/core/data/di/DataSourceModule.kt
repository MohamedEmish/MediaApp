package com.amosh.pulse.core.data.di

import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.source.LocalDataSource
import com.amosh.pulse.core.domain.source.RemoteDataSource
import com.amosh.pulse.core.data.RepositoryImpl
import com.amosh.pulse.core.data.source.InMemorySourceImpl
import com.amosh.pulse.core.data.source.LocalDataSourceImpl
import com.amosh.pulse.core.data.source.RemoteDataSourceImpl
import com.amosh.pulse.core.domain.source.InMemorySource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repository: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(source: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(source: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindAppInMemorySource(source: InMemorySourceImpl): InMemorySource
}