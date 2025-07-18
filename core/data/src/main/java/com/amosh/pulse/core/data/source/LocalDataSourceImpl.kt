package com.amosh.pulse.core.data.source

import com.amosh.pulse.core.data.dataSource.local.SecureDataStore
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.amosh.pulse.core.domain.source.LocalDataSource
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

    override fun getAppTheme() =
        secureDataStore.userData.map {
            it.theme
        }

    override suspend fun updateAppTheme(theme: SupportedTheme) =
        updateUserData {
            this.theme = theme
        }

    private suspend fun updateUserData(block: UserData.() -> Unit) {
        val userData = secureDataStore.userData.first().copy().apply(block)
        secureDataStore.setUserData(userData)
    }

    override fun getUserData() = secureDataStore.userData

    override suspend fun updateName(name: String) =
        updateUserData {
            userName = name
        }

    override suspend fun updateProfilePic(pic: String) =
        updateUserData {
            profilePic = pic
        }

}