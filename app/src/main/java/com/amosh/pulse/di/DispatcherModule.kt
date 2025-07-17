package com.amosh.pulse.di

import com.amosh.pulse.core.data.di.ApplicationScope
import com.amosh.pulse.core.data.di.DefaultDispatcher
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.data.di.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    @Singleton
    internal fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    @Singleton
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    @Singleton
    internal fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @ApplicationScope
    @Singleton
    @Provides
    fun providesCoroutineScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
}