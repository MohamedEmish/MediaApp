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
import com.amosh.pulse.core.ui.theme.platinum_150
import com.amosh.pulse.core.ui.theme.platinum_200
import com.amosh.pulse.core.ui.theme.platinum_600
import com.amosh.pulse.core.ui.theme.platinum_800
import com.amosh.pulse.core.ui.theme.platinum_900


val lightColors = LightColors.apply {
    primary = colorPrimary
    onPrimary = platinum_150
    inversePrimary = primaryDark
    secondary = colorAccent
    onSecondary = white
    backgroundColor = platinum_150
    onBackgroundColor = black
    surface = white
    onSurface = black
}


val darkColors = DarkColors.apply {
    primary = primaryDark
    onPrimary = platinum_150
    inversePrimary = colorPrimary
    secondary = accentDark
    onSecondary = black
    backgroundColor = platinum_150
    onBackgroundColor = white
    surface = platinum_600
    onSurface = black
}

@Composable
fun PulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
    ) {
        SimpleLabTheme(
            darkTheme = darkTheme,
            typography = Typography,
            lightColors = lightColors,
            darkColors = darkColors,
            content = content
        )
    }
}