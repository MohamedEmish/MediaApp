package com.amosh.pulse.media.domain.useCase

import com.amosh.pulse.media.domain.MediaRepository
import javax.inject.Inject

class GetHomeSectionsUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    fun getHomeSections(page: Int) =
        repository.getHomeSections(page)

    fun getHomeSearchSections(query: String) =
        repository.getHomeSearchSections(query)
}