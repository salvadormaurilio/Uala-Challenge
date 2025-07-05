package com.example.ualachallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightPrimaryText,
    onSecondary = LightSecondaryText,
    onSurface = LightOnSurface,
    onBackground = DarkPrimaryText,
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkPrimaryText,
    onSecondary = DarkSecondaryText,
    onSurface = DarkOnSurface,
    onBackground = DarkPrimaryText,
)


@Composable
fun CountriesChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
