package com.amosh.pulse.media.data.di

import com.amosh.pulse.media.data.MediaRepositoryImpl
import com.amosh.pulse.media.data.source.MediaRemoteSourceImpl
import com.amosh.pulse.media.domain.MediaRepository
import com.amosh.pulse.media.domain.source.MediaRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MediaDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindMediaRepository(repository: MediaRepositoryImpl): MediaRepository

    @Binds
    @Singleton
    abstract fun bindMediaRemoteDataSource(source: MediaRemoteSourceImpl): MediaRemoteSource
}