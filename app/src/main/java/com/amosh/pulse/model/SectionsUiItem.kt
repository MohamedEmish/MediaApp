package com.amosh.pulse.model

import com.amosh.pulse.model.enums.ContentType

data class SectionsUiItem(
    val name: String? = null,
    val type: String? = null,
    val contentType: ContentType? = null,
    val order: String? = null,
    val content: List<ContentUiItem>? = null
)