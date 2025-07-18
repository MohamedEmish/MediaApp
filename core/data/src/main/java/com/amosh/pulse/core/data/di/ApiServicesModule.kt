package com.amosh.pulse.core.data.di

import android.content.Context
import com.amosh.pulse.core.data.ConnectionCheckerImpl
import com.amosh.pulse.core.data.dataSource.api.AppApiService
import com.amosh.pulse.core.domain.ConnectionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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

    @Provides
    @Singleton
    fun provideConnectionChecker(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): ConnectionChecker = ConnectionCheckerImpl(context, ioDispatcher)
}