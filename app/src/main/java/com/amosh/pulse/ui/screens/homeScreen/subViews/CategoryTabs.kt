package com.amosh.pulse.ui.screens.homeScreen.subViews

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.amosh.pulse.core.ui.theme.platinum_150
import com.amosh.pulse.core.ui.theme.platinum_400
import com.amosh.pulse.core.ui.theme.platinum_50
import com.amosh.pulse.core.ui.theme.platinum_600
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun CategoryTabs(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onItemClick: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = modifier
            .clip(CircleShape),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.medium16,
            vertical = MaterialTheme.spacing.small8
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small8)
    ) {
        itemsIndexed(categories) { index, category ->
            Text(
                text = category,
                modifier = Modifier
                    .clickable {
                        selectedIndex = index
                        onItemClick(index)
                    }
                    .background(
                        color = if (selectedIndex == index) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            platinum_600
                        },
                        shape = RoundedCornerShape(MaterialTheme.spacing.medium16)
                    )
                    .padding(
                        horizontal = MaterialTheme.spacing.medium16,
                        vertical = MaterialTheme.spacing.small8
                    ),
                color = if (selectedIndex == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    platinum_400
                },
                style = if (selectedIndex == index) {
                    MaterialTheme.typography.labelLarge
                } else {
                    MaterialTheme.typography.labelMedium
                }
            )
        }
    }
}

@Composable
fun ShimmerCategoryTabs(
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
        modifier = modifier.padding(horizontal = MaterialTheme.spacing.medium16),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small8),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small8)
    ) {
        items(itemCount) {
            Box(
                modifier = Modifier
                    .width(MaterialTheme.spacing.special100)
                    .height(MaterialTheme.spacing.special36)
                    .background(brush = brush, shape = RoundedCornerShape(MaterialTheme.spacing.medium16))
            )
        }
    }
}