package com.amosh.pulse.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.amosh.pulse.core.ui.theme.CoreTheme
import com.amosh.pulse.core.ui.theme.DarkColors
import com.amosh.pulse.core.ui.theme.LightColors
import com.amosh.pulse.core.ui.theme.Typography

@Composable
fun SimpleLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = Typography,
    lightColors: LightColors = LightColors,
    darkColors: DarkColors = DarkColors,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) = CoreTheme(
    darkTheme,
    typography,
    lightColors.colorScheme,
    darkColors.colorScheme,
    dynamicColor,
    content
)