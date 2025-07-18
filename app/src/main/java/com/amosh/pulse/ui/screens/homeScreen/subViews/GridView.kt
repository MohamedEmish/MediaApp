package com.amosh.pulse.ui.screens.homeScreen.subViews

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amosh.pulse.core.ui.theme.platinum_150
import com.amosh.pulse.core.ui.theme.platinum_50
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.model.GridItem
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.model.enums.ContentType.AUDIO_ARTICLE
import com.amosh.pulse.model.enums.ContentType.AUDIO_BOOK
import com.amosh.pulse.model.enums.ContentType.EPISODE
import com.amosh.pulse.model.enums.ContentType.PODCAST
import com.amosh.pulse.ui.ext.mapToGridItem

@Composable
fun GridView(
    modifier: Modifier = Modifier,
    section: SectionsUiItem,
    selectedType: ContentType,
    lines: Int,
) {
    val items = when (selectedType) {
        PODCAST -> section.content?.filterIsInstance<ContentUiItem.PodcastContentUi>()
            ?.map { it.mapToGridItem() }

        EPISODE -> section.content?.filterIsInstance<ContentUiItem.EpisodeContentUi>()
            ?.map { it.mapToGridItem() }

        AUDIO_BOOK -> section.content?.filterIsInstance<ContentUiItem.AudioBookContentUi>()
            ?.map { it.mapToGridItem() }

        AUDIO_ARTICLE -> section.content?.filterIsInstance<ContentUiItem.AudioArticleContentUi>()
            ?.map { it.mapToGridItem() }
    }

    if (!items.isNullOrEmpty()) {
        GridViewData(
            sectionName = section.name.orEmpty(),
            items = items,
            lines = lines,
        )
    }
}

@Composable
fun GridViewData(
    sectionName: String,
    items: List<GridItem>,
    lines: Int,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f
    val cardHeight = MaterialTheme.spacing.special72
    val itemSpacing = MaterialTheme.spacing.small8

    val pairedItems = items.chunked(lines)

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
            contentPadding = PaddingValues(MaterialTheme.spacing.medium16),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            items(pairedItems) { pair ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(itemSpacing),
                    modifier = Modifier
                        .width(cardWidth)
                        .height((cardHeight * 2) + itemSpacing)
                ) {
                    GridItemView(
                        item = pair[0], modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                    )
                    if (pair.size > 1) {
                        GridItemView(
                            item = pair[1],
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cardHeight)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridItemView(
    modifier: Modifier,
    item: GridItem
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.spacing.medium16),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.special4),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(MaterialTheme.spacing.special72)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.medium16)),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium16)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}

@Composable
fun ShimmerGridView(
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
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(MaterialTheme.spacing.special72)
                        .background(
                            brush = brush,
                            shape = RoundedCornerShape(MaterialTheme.spacing.medium16)
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(MaterialTheme.spacing.special72)
                        .background(
                            brush = brush,
                            shape = RoundedCornerShape(MaterialTheme.spacing.medium16)
                        )
                )
            }
        }
    }
}
