package com.amosh.mediaapp.core.domain.model

import com.google.gson.annotations.SerializedName

sealed class ContentItem {
    data class PodcastContent(
        @SerializedName("podcast_id") val podcastId: String? = null,
        val name: String? = null,
        val description: String? = null,
        @SerializedName("avatar_url") val avatarUrl: String? = null,
        @SerializedName("episode_count") val episodeCount: Int? = null,
        val duration: String? = null,
        val language: String? = null,
        val priority: String? = null,
        @SerializedName("popularityScore") val popularityScore: String? = null,
        val score: String? = null
    ) : ContentItem()

    data class EpisodeContent(
        @SerializedName("podcastPopularityScore") val podcastPopularityScore: Int? = null,
        @SerializedName("podcastPriority") val podcastPriority: Int? = null,
        @SerializedName("episode_id") val episodeId: String? = null,
        val name: String? = null,
        @SerializedName("season_number") val seasonNumber: Int?? = null,
        @SerializedName("episode_type") val episodeType: String? = null,
        @SerializedName("podcast_name") val podcastName: String? = null,
        @SerializedName("author_name") val authorName: String? = null,
        val description: String? = null,
        val number: Int? = null,
        val duration: Int? = null,
        @SerializedName("avatar_url") val avatarUrl: String? = null,
        @SerializedName("separated_audio_url") val separatedAudioUrl: String? = null,
        @SerializedName("audio_url") val audioUrl: String? = null,
        @SerializedName("release_date") val releaseDate: String? = null,
        @SerializedName("podcast_id") val podcastId: String? = null,
        val chapters: List<Any>? = null,
        @SerializedName("paid_is_early_access") val paidIsEarlyAccess: Boolean? = null,
        @SerializedName("paid_is_now_early_access") val paidIsNowEarlyAccess: Boolean? = null,
        @SerializedName("paid_is_exclusive") val paidIsExclusive: Boolean? = null,
        @SerializedName("paid_transcript_url") val paidTranscriptUrl: String?? = null,
        @SerializedName("free_transcript_url") val freeTranscriptUrl: String?? = null,
        @SerializedName("paid_is_exclusive_partially") val paidIsExclusivePartially: Boolean? = null,
        @SerializedName("paid_exclusive_start_time") val paidExclusiveStartTime: Int? = null,
        @SerializedName("paid_early_access_date") val paidEarlyAccessDate: String?? = null,
        @SerializedName("paid_early_access_audio_url") val paidEarlyAccessAudioUrl: String?? = null,
        @SerializedName("paid_exclusivity_type") val paidExclusivityType: String?? = null,
        val score: Double? = null
    ) : ContentItem()

    data class AudioBookContent(
        @SerializedName("audiobook_id") val audiobookId: String? = null,
        val name: String? = null,
        @SerializedName("author_name") val authorName: String? = null,
        val description: String? = null,
        @SerializedName("avatar_url") val avatarUrl: String? = null,
        val duration: Int? = null,
        val language: String? = null,
        @SerializedName("release_date") val releaseDate: String? = null,
        val score: Int? = null
    ) : ContentItem()

    data class AudioArticleContent(
        @SerializedName("article_id") val articleId: String? = null,
        val name: String? = null,
        @SerializedName("author_name") val authorName: String? = null,
        val description: String? = null,
        @SerializedName("avatar_url") val avatarUrl: String? = null,
        val duration: Int? = null,
        @SerializedName("release_date") val releaseDate: String? = null,
        val score: Int? = null
    ) : ContentItem()
}