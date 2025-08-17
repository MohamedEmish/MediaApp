package com.amosh.pulse.core.data.interceptors

import com.amosh.pulse.core.data.dataSource.local.SecureDataStore
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AppHeadersInterceptor @Inject constructor(
    private val secureDataStore: SecureDataStore,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userLanguage = runBlocking { secureDataStore.userData.firstOrNull()?.language }
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .addHeader(
                ACCEPT_LANGUAGE,
                (userLanguage ?: SupportedLanguages.EN).name.lowercase()
            )
            .build()
        return chain.proceed(authenticatedRequest)
    }

    companion object {
        private const val ACCEPT_LANGUAGE = "Accept-Language"
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_VALUE = "application/json"
    }
}
