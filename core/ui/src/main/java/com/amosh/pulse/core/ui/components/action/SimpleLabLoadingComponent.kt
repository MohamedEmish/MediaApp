package com.amosh.pulse.core.ui.components.action

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun SimpleLabLoadingComponent(
    modifier: Modifier = Modifier,
    showProgressIndicator: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    var size by remember { mutableIntStateOf(0) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size = it.height }
    ) {
        if (showProgressIndicator) {
            CircularProgressIndicator(
                color = color,
                strokeWidth = (size * 0.05).dp
            )
        }
    }
}
