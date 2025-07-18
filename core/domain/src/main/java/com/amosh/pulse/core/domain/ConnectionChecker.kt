package com.amosh.pulse.core.domain

import kotlinx.coroutines.flow.Flow

interface ConnectionChecker {
    suspend fun hasInternetConnection(): Boolean
    fun observeConnection(): Flow<Boolean>
}