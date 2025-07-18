package com.amosh.pulse.ui.mapper

import com.amosh.pulse.core.domain.mapper.Mapper
import com.amosh.pulse.core.domain.model.ContentItem
import com.amosh.pulse.model.ContentUiItem
import javax.inject.Inject

class SearchItemUiMapper @Inject constructor() : Mapper<ContentItem, ContentUiItem?> {
    override fun from(i: ContentItem, parentType: String?): ContentUiItem? {
        return when {
            i.podcastId.isNullOrEmpty().not() -> {
                ContentUiItem.PodcastContentUi(
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
            }

            i.episodeId.isNullOrEmpty().not() -> {
                ContentUiItem.EpisodeContentUi(
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
            }

            i.audiobookId.isNullOrEmpty().not() -> {
                ContentUiItem.AudioBookContentUi(
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
            }

            i.articleId.isNullOrEmpty().not() -> {
                ContentUiItem.AudioArticleContentUi(
                    articleId = i.articleId,
                    name = i.name,
                    authorName = i.authorName,
                    description = i.description,
                    avatarUrl = i.avatarUrl,
                    duration = i.duration?.toInt(),
                    releaseDate = i.releaseDate,
                    score = i.score?.toInt()
                )
            }

            else -> null
        }
    }

    override fun to(o: ContentUiItem?): ContentItem {
        return ContentItem()
    }

}
