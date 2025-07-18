package com.amosh.pulse.ui.screens.homeScreen.subViews

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.amosh.pulse.R
import com.amosh.pulse.core.ui.theme.platinum_150
import com.amosh.pulse.core.ui.theme.platinum_50
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.model.SquareItem
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.model.enums.ContentType.AUDIO_ARTICLE
import com.amosh.pulse.model.enums.ContentType.AUDIO_BOOK
import com.amosh.pulse.model.enums.ContentType.EPISODE
import com.amosh.pulse.model.enums.ContentType.PODCAST
import com.amosh.pulse.ui.ext.mapToSquareItem
import com.amosh.pulse.utils.formatDate

@Composable
fun SquareView(
    modifier: Modifier = Modifier,
    section: SectionsUiItem,
    selectedType: ContentType,
    isBig: Boolean,
    onEndReached: () -> Unit,
) {
    val items = when (selectedType) {
        PODCAST -> section.content?.filterIsInstance<ContentUiItem.PodcastContentUi>()
            ?.map { it.mapToSquareItem() }

        EPISODE -> section.content?.filterIsInstance<ContentUiItem.EpisodeContentUi>()
            ?.map { it.mapToSquareItem() }

        AUDIO_BOOK -> section.content?.filterIsInstance<ContentUiItem.AudioBookContentUi>()
            ?.map { it.mapToSquareItem() }

        AUDIO_ARTICLE -> section.content?.filterIsInstance<ContentUiItem.AudioArticleContentUi>()
            ?.map { it.mapToSquareItem() }
    }

    if (!items.isNullOrEmpty()) {
        if (isBig) {
            BigSquareViewData(
                sectionName = section.name.orEmpty(),
                items = items,
                modifier = modifier,
                onEndReached = onEndReached
            )
        } else {
            SquareViewData(
                sectionName = section.name.orEmpty(),
                items = items,
                modifier = modifier,
                onEndReached = onEndReached
            )
        }
    }
}

@Composable
fun SquareViewData(
    sectionName: String,
    items: List<SquareItem>,
    modifier: Modifier = Modifier,
    onEndReached: () -> Unit,
) {
    // For infinite scrolling
    val infiniteItems = remember { items + items + items }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = items.size)

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val fullCardWidth = screenWidth * 0.60f
    val peekWidth = screenWidth * 0.15f
    val itemWidth = fullCardWidth + peekWidth  // Each item takes card width + peek
    val itemSpacing = MaterialTheme.spacing.small8 // Add spacing between items
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

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(MaterialTheme.spacing.special156)
                .padding(vertical = MaterialTheme.spacing.small8)
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
                itemsIndexed(infiniteItems) { index, item ->
                    val currentIndex = infiniteItems.indexOf(item) % items.size
                    if (index == items.lastIndex) {
                        LaunchedEffect(Unit) {
                            onEndReached()
                        }
                    }
                    Box(
                        modifier = Modifier
                            .width(itemWidth)
                            .fillMaxHeight(),
                    ) {
                        val thirdCard = items[(currentIndex + 2) % items.size]
                        SquareCard(
                            item = thirdCard,
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .offset(x = peekWidth)
                                .zIndex(0f),
                        )

                        val secondCard = items[(currentIndex + 1) % items.size]
                        SquareCard(
                            item = secondCard,
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .offset(x = peekWidth / 2)
                                .zIndex(1f),
                        )

                        val currentCard = items[currentIndex]
                        SquareCard(
                            item = currentCard,
                            modifier = Modifier
                                .width(fullCardWidth)
                                .align(Alignment.CenterStart)
                                .zIndex(2f)
                        )
                    }
                }
            }

            LaunchedEffect(listState.firstVisibleItemIndex) {
                if (listState.firstVisibleItemIndex >= items.size * 2) {
                    listState.scrollToItem(items.size)
                } else if (listState.firstVisibleItemIndex <= 0) {
                    listState.scrollToItem(items.size)
                }
            }
        }
    }
}

@Composable
fun BigSquareViewData(
    sectionName: String,
    items: List<SquareItem>,
    modifier: Modifier = Modifier,
    onEndReached: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val spacing = MaterialTheme.spacing.medium16
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
                Box(modifier = Modifier.width(screenWidth - spacing.times(2))) {
                    BigSquareItemView(item = item)
                }
            }
        }
    }
}

@Composable
fun BigSquareItemView(item: SquareItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.spacing.special140)
            .clip(RoundedCornerShape(MaterialTheme.spacing.medium16))
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(MaterialTheme.spacing.medium16)),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                        ),
                        startY = 0.4f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium16),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small8))

            Text(
                text = item.episodesCount?.let {
                    stringResource(R.string.episode_x, it)
                } ?: item.date.formatDate() ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                ),
                maxLines = 1,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun SquareCard(
    item: SquareItem,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium16),
        modifier = modifier
            .graphicsLayer { this.alpha = alpha }
            .height(MaterialTheme.spacing.special140),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium16)
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small8))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            AsyncImage(
                model = item.imageUrl,
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

@Composable
fun ShimmerSquareView(
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

