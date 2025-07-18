package com.amosh.pulse.ui.screens.homeScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.amosh.pulse.core.ui.theme.platinum_150
import com.amosh.pulse.core.ui.theme.platinum_50
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
    // For infinite scrolling
    val infiniteItems = remember { items + items + items }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = items.size)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val fullCardWidth = screenWidth * 0.60f
    val peekWidth = screenWidth * 0.15f
    val itemWidth = fullCardWidth + peekWidth  // Each item takes card width + peek
    val itemSpacing = MaterialTheme.spacing.small8 // Add spacing between items

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.special156)
            .padding(
                horizontal = MaterialTheme.spacing.medium16,
                vertical = MaterialTheme.spacing.small8
            )
            .background(platinum_150, RoundedCornerShape(MaterialTheme.spacing.medium16)),
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
            ),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            items(infiniteItems) { item ->
                val currentIndex = infiniteItems.indexOf(item) % items.size
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight(),
                ) {
                    // Card 3 (peeking from right, deepest)
                    if (currentIndex + 2 < items.size) {
                        PodcastCard(
                            item = items[(currentIndex + 2) % items.size],
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
                            item = items[(currentIndex + 1) % items.size],
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .offset(x = peekWidth.div(2))
                                .zIndex(1f),
                        )
                    }

                    // Current Card (main card)
                    PodcastCard(
                        item = items[currentIndex % items.size],
                        modifier = Modifier
                            .width(fullCardWidth)
                            .align(Alignment.CenterStart)
                            .zIndex(2f)
                            .clickable { onItemClick(items[currentIndex % items.size]) },
                    )
                }
            }
        }

        // Handle infinite scroll reset
        LaunchedEffect(listState.firstVisibleItemIndex) {
            if (listState.firstVisibleItemIndex >= items.size * 2) {
                listState.scrollToItem(items.size)
            } else if (listState.firstVisibleItemIndex <= 0) {
                listState.scrollToItem(items.size)
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
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

            item.avatarUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .height(MaterialTheme.spacing.special124)
                        .width(MaterialTheme.spacing.special124)
                        .clip(RoundedCornerShape(MaterialTheme.spacing.medium16)),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Composable
fun ShimmerPeekingCardsCarousel(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    val shimmerColors = listOf(
        platinum_150,
        platinum_50,
        platinum_150,
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
                    .width(MaterialTheme.spacing.special304)
                    .height(MaterialTheme.spacing.special140)
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(MaterialTheme.spacing.medium16)
                    )
            )
        }
    }
}

