package com.amosh.pulse.core.domain.useCases

import com.amosh.pulse.core.domain.Repository
import javax.inject.Inject

class GetHomeSectionsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getHomeSections(page: Int) =
        repository.getHomeSections(page)
}