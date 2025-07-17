package com.amosh.mediaapp.core.domain.model

data class HomeSectionsResponse(
    val sections: List<Section>? = null,
    val pagination: Pagination? = null
)