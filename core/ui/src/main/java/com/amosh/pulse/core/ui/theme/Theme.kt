package com.amosh.pulse.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object LightColors {
    val colorScheme
        get() = run {
            lightColorScheme(
                primary = primary,
                onPrimary = onPrimary,
                inversePrimary = inversePrimary,
                secondary = secondary,
                onSecondary = onSecondary,
                background = backgroundColor,
                onBackground = onBackgroundColor,
                surface = surface,
                onSurface = onSurface,
                inverseSurface = inverseSurface,
                error = error,
                onError = onError,
            )
        }

    var primary = colorPrimary
    var onPrimary = white
    var inversePrimary = platinum_400

    var secondary = colorAccent
    var onSecondary = black

    var backgroundColor = platinum_100
    var onBackgroundColor = platinum_900

    var surface = white
    var onSurface = black
    var inverseSurface = platinum_400

    var error = error_600
    var onError = white
}

object DarkColors {
    val colorScheme
        get() = run {
            darkColorScheme(
                primary = primary,
                onPrimary = onPrimary,
                inversePrimary = inversePrimary,
                secondary = secondary,
                onSecondary = onSecondary,
                background = backgroundColor,
                onBackground = onBackgroundColor,
                surface = surface,
                onSurface = onSurface,
                inverseSurface = inverseSurface,
                error = error,
                onError = onError,
            )
        }

    var primary = colorAccent
    var onPrimary = platinum_900
    var inversePrimary = platinum_400

    var secondary = colorPrimary
    var onSecondary = platinum_400

    var backgroundColor = platinum_900
    var onBackgroundColor = platinum_100

    var surface = platinum_100
    var onSurface = platinum_900
    var inverseSurface = platinum_400

    var error = error_50
    var onError = platinum_900
}

@Suppress("DEPRECATION")
@Composable
fun CoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = Typography,
    lightColors: ColorScheme = LightColors.colorScheme,
    darkColors: ColorScheme = DarkColors.colorScheme,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColors
        else -> lightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? Activity
            if (activity != null) {
                val window = activity.window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
        shapes = Shapes
    )
}