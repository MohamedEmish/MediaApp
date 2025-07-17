package com.amosh.pulse.core.data.source

import com.amosh.pulse.core.data.dataSource.local.SecureDataStore
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.source.LocalDataSource
import com.amosh.zakwa.core.domain.model.enums.SupportedLanguages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val secureDataStore: SecureDataStore,
) : LocalDataSource {
    override suspend fun updateAppLanguage(language: SupportedLanguages) =
        updateUserData {
            this.language = language
        }

    override fun getAppLanguage() =
        secureDataStore.userData.map {
            it.language
        }

    private suspend fun updateUserData(block: UserData.() -> Unit) {
        val userData = secureDataStore.userData.first().copy().apply(block)
        secureDataStore.setUserData(userData)
    }

    override fun getUserData() = secureDataStore.userData
}