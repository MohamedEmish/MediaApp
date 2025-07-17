package com.amosh.mediaapp.core.domain.model

import com.google.gson.annotations.SerializedName

data class Section(
    val name: String? = null,
    val type: String? = null,
    @SerializedName("content_type") val contentType: String? = null,
    val order: String? = null,
    val content: List<ContentItem>? = null
)