package com.amosh.mediaapp.core.data.source

import com.amosh.mediaapp.core.data.dataSource.local.SecureDataStore
import com.amosh.mediaapp.core.domain.source.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val secureDataStore: SecureDataStore,
) : LocalDataSource