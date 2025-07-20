package com.amosh.pulse.media.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.media.ui.model.ContentUiItem
import com.amosh.pulse.media.ui.model.QueueItem
import com.amosh.pulse.media.ui.model.SectionsUiItem
import com.amosh.pulse.media.ui.model.enums.ContentType
import com.amosh.pulse.media.ui.model.enums.ContentType.AUDIO_ARTICLE
import com.amosh.pulse.media.ui.model.enums.ContentType.AUDIO_BOOK
import com.amosh.pulse.media.ui.model.enums.ContentType.EPISODE
import com.amosh.pulse.media.ui.model.enums.ContentType.PODCAST
import com.amosh.pulse.media.ui.mapper.mapToQueueItem

@Composable
fun QueueView(
    modifier: Modifier = Modifier,
    section: SectionsUiItem,
    selectedType: ContentType,
    onEndReached: () -> Unit, // NEW
) {
    val items = when (selectedType) {
        PODCAST -> section.content?.filterIsInstance<ContentUiItem.PodcastContentUi>()
            ?.map { it.mapToQueueItem() }

        EPISODE -> section.content?.filterIsInstance<ContentUiItem.EpisodeContentUi>()
            ?.map { it.mapToQueueItem() }

        AUDIO_BOOK -> section.content?.filterIsInstance<ContentUiItem.AudioBookContentUi>()
            ?.map { it.mapToQueueItem() }

        AUDIO_ARTICLE -> section.content?.filterIsInstance<ContentUiItem.AudioArticleContentUi>()
            ?.map { it.mapToQueueItem() }
    }

    if (!items.isNullOrEmpty()) {
        QueueViewData(
            sectionName = section.name.orEmpty(),
            items = items,
            modifier = modifier,
            onEndReached = onEndReached
        )
    }
}


@Composable
fun QueueViewData(
    sectionName: String,
    items: List<QueueItem>,
    modifier: Modifier = Modifier,
    onEndReached: () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.medium16,
                vertical = MaterialTheme.spacing.special4
            ),
            text = sectionName,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(MaterialTheme.spacing.medium16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small8)
        ) {
            itemsIndexed(items) { index, item ->
                if (index == items.lastIndex) {
                    LaunchedEffect(Unit) {
                        onEndReached()
                    }
                }
                QueueItemView(item = item)
            }
        }
    }
}

@Composable
fun QueueItemView(item: QueueItem) {
    Column(
        modifier = Modifier
            .width(MaterialTheme.spacing.special124)
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(MaterialTheme.spacing.medium16)
            )
    ) {
        Box(
            modifier = Modifier
                .width(MaterialTheme.spacing.special124)
                .height(MaterialTheme.spacing.special124)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(MaterialTheme.spacing.medium16)),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.special4)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.duration,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun ShimmerQueueView(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onSurface,
        MaterialTheme.colorScheme.inverseSurface,
        MaterialTheme.colorScheme.onSurface,
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 500f, translateAnim.value + 500f)
    )
    LazyRow(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium16,
                vertical = MaterialTheme.spacing.small8
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small8),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small8)
    ) {
        items(itemCount) {
            Box(
                modifier = Modifier
                    .width(MaterialTheme.spacing.special124)
                    .height(MaterialTheme.spacing.special124)
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(MaterialTheme.spacing.medium16)
                    )
            )
        }
    }
}
