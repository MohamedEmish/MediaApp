package com.amosh.pulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.amosh.pulse.core.ui.SimpleLabTheme
import com.amosh.pulse.core.ui.theme.DarkColors
import com.amosh.pulse.core.ui.theme.LightColors
import com.amosh.pulse.core.ui.theme.LocalSpacing
import com.amosh.pulse.core.ui.theme.Spacing
import com.amosh.pulse.core.ui.theme.Typography
import com.amosh.pulse.core.ui.theme.platinum_100
import com.amosh.pulse.core.ui.theme.platinum_600
import com.amosh.pulse.core.ui.theme.platinum_800


val lightColors = LightColors.apply {
    primary = colorPrimary
    onPrimary = platinum_100
    inversePrimary = primaryDark
    secondary = colorAccent
    onSecondary = white
    backgroundColor = platinum_100
    onBackgroundColor = black
    surface = white
    onSurface = black
}


val darkColors = DarkColors.apply {
    primary = primaryDark
    onPrimary = platinum_800
    inversePrimary = colorPrimary
    secondary = accentDark
    onSecondary = black
    backgroundColor = platinum_800
    onBackgroundColor = white
    surface = platinum_600
    onSurface = black
}

@Composable
fun PulseTheme(
    darkTheme: Boolean?,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
    ) {
        SimpleLabTheme(
            darkTheme = darkTheme ?: isSystemInDarkTheme(),
            typography = Typography,
            lightColors = lightColors,
            darkColors = darkColors,
            content = content
        )
    }
}