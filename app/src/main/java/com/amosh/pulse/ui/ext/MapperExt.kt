package com.amosh.pulse.ui.ext

import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.model.GridItem
import com.amosh.pulse.model.QueueItem
import com.amosh.pulse.model.SquareItem

fun ContentUiItem.PodcastContentUi.mapToQueueItem(): QueueItem {
    return QueueItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        duration = this.duration.orEmpty(),
        date = this.episodeCount?.toString().orEmpty(),
    )
}

fun ContentUiItem.EpisodeContentUi.mapToQueueItem(): QueueItem {
    return QueueItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.AudioBookContentUi.mapToQueueItem(): QueueItem {
    return QueueItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.AudioArticleContentUi.mapToQueueItem(): QueueItem {
    return QueueItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.PodcastContentUi.mapToSquareItem(): SquareItem {
    return SquareItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration.orEmpty(),
        date = this.episodeCount?.toString().orEmpty(),
        episodesCount = this.episodeCount ?: 0
    )
}

fun ContentUiItem.EpisodeContentUi.mapToSquareItem(): SquareItem {
    return SquareItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.AudioBookContentUi.mapToSquareItem(): SquareItem {
    return SquareItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.AudioArticleContentUi.mapToSquareItem(): SquareItem {
    return SquareItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.PodcastContentUi.mapToGridItem(): GridItem {
    return GridItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration.orEmpty(),
        date = this.episodeCount?.toString().orEmpty(),
    )
}

fun ContentUiItem.EpisodeContentUi.mapToGridItem(): GridItem {
    return GridItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun ContentUiItem.AudioBookContentUi.mapToGridItem(): GridItem {
    return GridItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate?.toString().orEmpty(),
    )
}

fun ContentUiItem.AudioArticleContentUi.mapToGridItem(): GridItem {
    return GridItem(
        imageUrl = this.avatarUrl.orEmpty(),
        title = this.name.orEmpty(),
        description = this.description.orEmpty(),
        duration = this.duration?.toString().orEmpty(),
        date = this.releaseDate.orEmpty(),
    )
}

fun List<ContentUiItem?>?.filterNotNullItems(): List<ContentUiItem> {
    return this.orEmpty().filterNotNull()
}