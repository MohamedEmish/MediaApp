package com.amosh.pulse.core.data

import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.amosh.pulse.core.domain.source.LocalDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : Repository {
    override fun getAppLanguage() =
        localDataSource.getAppLanguage()

    override suspend fun updateAppLanguage(language: SupportedLanguages) =
        localDataSource.updateAppLanguage(language)

    override fun getAppTheme() =
        localDataSource.getAppTheme()

    override suspend fun updateAppTheme(theme: SupportedTheme) =
        localDataSource.updateAppTheme(theme)


    override fun getUserData() =
        localDataSource.getUserData()

    override suspend fun updateProfilePic(pic: String) =
        localDataSource.updateProfilePic(pic)

    override suspend fun updateName(name: String) =
        localDataSource.updateName(name)

}