package com.amosh.pulse.core.domain.useCases

import com.amosh.pulse.core.domain.Repository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: Repository,
) {
    fun getUserData() =
        repository.getUserData()

}