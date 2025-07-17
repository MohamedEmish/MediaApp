package com.amosh.pulse.core.ui.components.bars

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.theme.SimpleLabTitleLarge
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLabTopAppBar(
    title: String,
    hasNavigationIcon: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onNavigationIconClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            SimpleLabTextViewCompose(
                text = title,
                textColor = colors.titleContentColor,
                textStyle = if (hasNavigationIcon) SimpleLabTitleMedium else SimpleLabTitleLarge
            )
        },
        navigationIcon = {
            if (hasNavigationIcon) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "",
                    )
                }
            }
        },
        actions = {
            actions()
        },
        colors = colors,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}