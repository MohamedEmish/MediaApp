package com.amosh.pulse.core.data.source

import com.amosh.pulse.core.data.dataSource.local.SecureDataStore
import com.amosh.pulse.core.domain.source.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val secureDataStore: SecureDataStore,
) : LocalDataSource