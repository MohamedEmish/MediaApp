package com.amosh.pulse.core.domain.model

import com.google.gson.annotations.SerializedName

data class ContentItem(
    // Common fields
    @SerializedName("podcast_id") val podcastId: String? = null,
    @SerializedName("episode_id") val episodeId: String? = null,
    @SerializedName("audiobook_id") val audiobookId: String? = null,
    @SerializedName("article_id") val articleId: String? = null,

    // Basic information
    val name: String? = null,
    val description: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    val duration: String? = null,  // Could be Int? if you prefer
    val language: String? = null,

    // Podcast specific
    @SerializedName("episode_count") val episodeCount: Int? = null,
    val priority: String? = null,
    @SerializedName("popularityScore") val popularityScore: String? = null,
    val score: String? = null,
    @SerializedName("podcastPopularityScore") val podcastPopularityScore: Int? = null,
    @SerializedName("podcastPriority") val podcastPriority: Int? = null,

    // Episode specific
    @SerializedName("season_number") val seasonNumber: Int? = null,
    @SerializedName("episode_type") val episodeType: String? = null,
    @SerializedName("podcast_name") val podcastName: String? = null,
    @SerializedName("author_name") val authorName: String? = null,
    val number: Int? = null,
    @SerializedName("separated_audio_url") val separatedAudioUrl: String? = null,
    @SerializedName("audio_url") val audioUrl: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    val chapters: List<Any>? = null,

    // Paid content flags
    @SerializedName("paid_is_early_access") val paidIsEarlyAccess: Boolean? = null,
    @SerializedName("paid_is_now_early_access") val paidIsNowEarlyAccess: Boolean? = null,
    @SerializedName("paid_is_exclusive") val paidIsExclusive: Boolean? = null,
    @SerializedName("paid_transcript_url") val paidTranscriptUrl: String? = null,
    @SerializedName("free_transcript_url") val freeTranscriptUrl: String? = null,
    @SerializedName("paid_is_exclusive_partially") val paidIsExclusivePartially: Boolean? = null,
    @SerializedName("paid_exclusive_start_time") val paidExclusiveStartTime: Int? = null,
    @SerializedName("paid_early_access_date") val paidEarlyAccessDate: String? = null,
    @SerializedName("paid_early_access_audio_url") val paidEarlyAccessAudioUrl: String? = null,
    @SerializedName("paid_exclusivity_type") val paidExclusivityType: String? = null,
)