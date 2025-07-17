package com.amosh.pulse.core.domain.model

data class HomeSectionsResponse(
    val sections: List<Section>? = null,
    val pagination: Pagination? = null
)