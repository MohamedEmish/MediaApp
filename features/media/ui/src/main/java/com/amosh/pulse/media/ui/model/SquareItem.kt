package com.amosh.pulse.media.ui.model

data class SquareItem (
    val imageUrl: String,
    val title: String,
    val description: String,
    val duration: String,
    val date: String,
    val episodesCount: Int? = null,
)