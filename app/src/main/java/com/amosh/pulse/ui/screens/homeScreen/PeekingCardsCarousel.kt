package com.amosh.pulse.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.amosh.pulse.core.ui.theme.platinum_200
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.model.enums.ContentType.AUDIO_ARTICLE
import com.amosh.pulse.model.enums.ContentType.AUDIO_BOOK
import com.amosh.pulse.model.enums.ContentType.EPISODE
import com.amosh.pulse.model.enums.ContentType.PODCAST

@Composable
fun TopItemsCarousel(
    modifier: Modifier = Modifier,
    sections: List<SectionsUiItem>,
    selectedType: ContentType,
    onItemClick: (String) -> Unit = {}
) {
    when (selectedType) {
        PODCAST -> {
            val items = sections
                .mapNotNull { it.content }
                .flatten()
                .filterIsInstance<ContentUiItem.PodcastContentUi>()

            if (items.isNotEmpty()) {
                PeekingCardsCarousel(
                    items = items,
                    modifier = modifier,
                    onItemClick = { podcast -> onItemClick(podcast.podcastId ?: "") }
                )
            }
        }

        EPISODE -> sections.filterIsInstance<ContentUiItem.EpisodeContentUi>()
        AUDIO_BOOK -> sections.filterIsInstance<ContentUiItem.AudioBookContentUi>()
        AUDIO_ARTICLE -> sections.filterIsInstance<ContentUiItem.AudioArticleContentUi>()
    }
}

@Composable
fun PeekingCardsCarousel(
    items: List<ContentUiItem.PodcastContentUi>,
    modifier: Modifier = Modifier,
    onItemClick: (ContentUiItem.PodcastContentUi) -> Unit
) {
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val fullCardWidth = screenWidth * 0.60f
    val peekWidth = screenWidth * 0.15f
    val itemWidth = fullCardWidth + peekWidth  // Each item takes card width + peek

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.special156)
            .padding(MaterialTheme.spacing.medium16)
            .background(platinum_200, RoundedCornerShape(MaterialTheme.spacing.medium16)),
    ) {
        LazyRow(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(MaterialTheme.spacing.medium16)),
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.spacing.medium16,
                vertical = MaterialTheme.spacing.small8
            )
        ) {
            items(items) { item ->
                val currentIndex = items.indexOf(item)
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight(),
                ) {
                    // Card 3 (peeking from right, deepest)
                    if (currentIndex + 2 < items.size) {
                        PodcastCard(
                            item = items[currentIndex + 2],
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .offset(x = peekWidth)
                                .zIndex(0f),
                        )
                    }

                    // Card 2 (middle, peeking from right)
                    if (currentIndex + 1 < items.size) {
                        PodcastCard(
                            item = items[currentIndex + 1],
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .offset(x = peekWidth.div(2))
                                .zIndex(1f),
                        )
                    }

                    // Current Card (main card)
                    PodcastCard(
                        item = item,
                        modifier = Modifier
                            .width(fullCardWidth)
                            .align(Alignment.CenterStart)
                            .zIndex(2f)
                            .clickable { onItemClick(item) },
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastCard(
    item: ContentUiItem.PodcastContentUi,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .graphicsLayer { this.alpha = alpha }
            .height(MaterialTheme.spacing.special140),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            item.avatarUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .height(140.dp)
                        .width(140.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.name ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.description ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}