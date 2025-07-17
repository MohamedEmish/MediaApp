package com.amosh.pulse.core.ui.components.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun SimpleLabCircularImageView(
    imageUrl: String,
    placeholder: ImageVector,
    size: Dp = MaterialTheme.spacing.special100,
    strokeWidth: Dp = MaterialTheme.spacing.special1,
    strokeColor: Color = MaterialTheme.colorScheme.secondary,
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(
                width = strokeWidth,
                color = strokeColor,
                shape = CircleShape
            ),
        loading = {
            Image(
                imageVector = placeholder,
                contentDescription = "Placeholder",
                modifier = Modifier
                    .size(size)
                    .padding(MaterialTheme.spacing.small8)
            )
        },
        error = {
            Image(
                imageVector = placeholder,
                contentDescription = "Error",
                modifier = Modifier
                    .size(size)
                    .padding(MaterialTheme.spacing.small8)
            )
        }
    )
}