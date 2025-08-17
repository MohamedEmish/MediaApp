package com.amosh.pulse.core.data.di

import android.content.Context
import com.amosh.pulse.core.data.interceptors.AppHeadersInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideLoggingInterceptor() = LoggingInterceptor.Builder()
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request(" ")
        .response(" ")
        .build()

    @Provides
    fun provideChuckInterceptor(
        @ApplicationContext context: Context,
    ): ChuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(
            ChuckerCollector(
                context = context,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )
        )
        .build()

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
        appHeadersInterceptor: AppHeadersInterceptor,
        chuckInterceptor: ChuckerInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(appHeadersInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckInterceptor)
        .retryOnConnectionFailure(true)
        .build()


    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://mock.apidog.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}