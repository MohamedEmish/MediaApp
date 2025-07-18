package com.amosh.pulse.ui.ext

import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.model.QueueItem
import com.amosh.pulse.model.enums.SquareItem

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
        date = this.releaseDate?.toString().orEmpty(),
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