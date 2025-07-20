package com.amosh.pulse.media.ui.mapper

import com.amosh.pulse.core.domain.mapper.Mapper
import com.amosh.pulse.core.domain.model.ContentItem
import com.amosh.pulse.core.domain.utils.stringToEnum
import com.amosh.pulse.media.ui.model.ContentUiItem
import com.amosh.pulse.media.ui.model.enums.ContentType
import com.amosh.pulse.media.ui.model.enums.ContentType.*
import javax.inject.Inject

class ContentItemUiMapper @Inject constructor() : Mapper<ContentItem, ContentUiItem> {
    override fun from(i: ContentItem, parentType: String?): ContentUiItem {
        val type = parentType?.let { stringToEnum<ContentType>(parentType) }
        return when (type) {
            PODCAST -> ContentUiItem.PodcastContentUi(
                podcastId = i.podcastId,
                name = i.name,
                description = i.description,
                avatarUrl = i.avatarUrl,
                episodeCount = i.episodeCount,
                duration = i.duration,
                language = i.language,
                priority = i.priority,
                popularityScore = i.popularityScore,
                score = i.score
            )

            EPISODE -> ContentUiItem.EpisodeContentUi(
                podcastPopularityScore = i.podcastPopularityScore,
                podcastPriority = i.podcastPriority,
                episodeId = i.episodeId,
                name = i.name,
                seasonNumber = i.seasonNumber,
                episodeType = i.episodeType,
                podcastName = i.podcastName,
                authorName = i.authorName,
                description = i.description,
                number = i.number,
                duration = i.duration?.toInt(),
                avatarUrl = i.avatarUrl,
                separatedAudioUrl = i.separatedAudioUrl,
                audioUrl = i.audioUrl,
                releaseDate = i.releaseDate,
                podcastId = i.podcastId,
                chapters = i.chapters,
                paidIsEarlyAccess = i.paidIsEarlyAccess,
                paidIsNowEarlyAccess = i.paidIsNowEarlyAccess,
                paidIsExclusive = i.paidIsExclusive,
                paidTranscriptUrl = i.paidTranscriptUrl,
                freeTranscriptUrl = i.freeTranscriptUrl,
                paidIsExclusivePartially = i.paidIsExclusivePartially,
                paidExclusiveStartTime = i.paidExclusiveStartTime,
                paidEarlyAccessDate = i.paidEarlyAccessDate,
                paidEarlyAccessAudioUrl = i.paidEarlyAccessAudioUrl,
                paidExclusivityType = i.paidExclusivityType,
                score = i.score?.toDouble()
            )

            AUDIO_BOOK -> ContentUiItem.AudioBookContentUi(
                audiobookId = i.audiobookId,
                name = i.name,
                authorName = i.authorName,
                description = i.description,
                avatarUrl = i.avatarUrl,
                duration = i.duration?.toInt(),
                language = i.language,
                releaseDate = i.releaseDate,
                score = i.score?.toInt()
            )

            AUDIO_ARTICLE -> ContentUiItem.AudioArticleContentUi(
                articleId = i.articleId,
                name = i.name,
                authorName = i.authorName,
                description = i.description,
                avatarUrl = i.avatarUrl,
                duration = i.duration?.toInt(),
                releaseDate = i.releaseDate,
                score = i.score?.toInt()
            )

            null -> throw IllegalArgumentException("Unknown content type")
        }
    }

    override fun to(o: ContentUiItem): ContentItem {
        return when (o) {
            is ContentUiItem.PodcastContentUi -> ContentItem(
                podcastId = o.podcastId,
                name = o.name,
                description = o.description,
                avatarUrl = o.avatarUrl,
                episodeCount = o.episodeCount,
                duration = o.duration,
                language = o.language,
                priority = o.priority,
                popularityScore = o.popularityScore,
                score = o.score
            )

            is ContentUiItem.EpisodeContentUi -> ContentItem(
                podcastPopularityScore = o.podcastPopularityScore,
                podcastPriority = o.podcastPriority,
                episodeId = o.episodeId,
                name = o.name,
                seasonNumber = o.seasonNumber,
                episodeType = o.episodeType,
                podcastName = o.podcastName,
                authorName = o.authorName,
                description = o.description,
                number = o.number,
                duration = o.duration.toString(),
                avatarUrl = o.avatarUrl,
                separatedAudioUrl = o.separatedAudioUrl,
                audioUrl = o.audioUrl,
                releaseDate = o.releaseDate,
                podcastId = o.podcastId,
                chapters = o.chapters,
                paidIsEarlyAccess = o.paidIsEarlyAccess,
                paidIsNowEarlyAccess = o.paidIsNowEarlyAccess,
                paidIsExclusive = o.paidIsExclusive,
                paidTranscriptUrl = o.paidTranscriptUrl,
                freeTranscriptUrl = o.freeTranscriptUrl,
                paidIsExclusivePartially = o.paidIsExclusivePartially,
                paidExclusiveStartTime = o.paidExclusiveStartTime,
                paidEarlyAccessDate = o.paidEarlyAccessDate,
                paidEarlyAccessAudioUrl = o.paidEarlyAccessAudioUrl,
                paidExclusivityType = o.paidExclusivityType,
                score = o.score.toString()
            )

            is ContentUiItem.AudioBookContentUi -> ContentItem(
                audiobookId = o.audiobookId,
                name = o.name,
                authorName = o.authorName,
                description = o.description,
                avatarUrl = o.avatarUrl,
                duration = o.duration.toString(),
                language = o.language,
                releaseDate = o.releaseDate,
                score = o.score.toString()
            )

            is ContentUiItem.AudioArticleContentUi -> ContentItem(
                articleId = o.articleId,
                name = o.name,
                authorName = o.authorName,
                description = o.description,
                avatarUrl = o.avatarUrl,
                duration = o.duration.toString(),
                releaseDate = o.releaseDate,
                score = o.score.toString()
            )
        }
    }
}