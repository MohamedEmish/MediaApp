package com.amosh.mediaapp.core.data.di

import com.amosh.mediaapp.core.data.dataSource.api.AppApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiServicesModule {
    @Provides
    @Singleton
    fun provideAppApiService(
        retrofit: Retrofit,
    ): AppApiService = retrofit.create(AppApiService::class.java)
}