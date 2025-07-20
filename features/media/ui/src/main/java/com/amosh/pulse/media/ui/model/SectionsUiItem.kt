package com.amosh.pulse.media.ui.model

import com.amosh.pulse.media.ui.model.enums.ContentType

data class SectionsUiItem(
    val name: String? = null,
    val type: String? = null,
    val contentType: ContentType? = null,
    val order: String? = null,
    val content: List<ContentUiItem>? = null
)