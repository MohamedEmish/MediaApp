package com.amosh.pulse.ui.screens.libraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amosh.pulse.R
import com.amosh.pulse.core.ui.components.EmptyViewScreen

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        EmptyViewScreen(
            emptyMessage = stringResource(R.string.coming_soon),
            animationRes = R.raw.no_library
        )
    }
}