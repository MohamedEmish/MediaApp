package com.amosh.pulse.core.ui.components.inputFields

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageWithBackground(
    modifier: Modifier = Modifier,
    background: ImageVector? = null,
    backgroundTint: Color? = null,
    icon: ImageVector? = null,
    iconTint: Color? = null,
) {
    Box(modifier = modifier) {
        background?.let { background ->
            Image(
                background,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier,
                colorFilter = backgroundTint?.let { tint -> ColorFilter.tint(color = tint) }
            )
        }

        icon?.let {
            Image(
                icon,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier,
                colorFilter = iconTint?.let { ColorFilter.tint(color = it) }
            )
        }
    }
}

@Composable
fun PasswordToggleIndicator(
    passwordVisible: Boolean,
    onVisibilityChange: (b: Boolean) -> Unit,
) {
    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    IconButton(onClick = {
        onVisibilityChange.invoke(!passwordVisible)
    }) {
        Icon(imageVector = image, null)
    }
}