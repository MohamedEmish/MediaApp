package com.amosh.mediaapp.core.domain.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("next_page") val nextPage: String? = null,
    @SerializedName("total_pages") val totalPages: Int? = null
)