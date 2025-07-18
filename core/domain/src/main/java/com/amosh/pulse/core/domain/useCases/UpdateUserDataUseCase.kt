package com.amosh.pulse.core.domain.useCases

import com.amosh.pulse.core.domain.Repository
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend fun updateName(name: String) =
        repository.updateName(name)

    suspend fun updateProfilePic(name: String) =
        repository.updateProfilePic(name)

}