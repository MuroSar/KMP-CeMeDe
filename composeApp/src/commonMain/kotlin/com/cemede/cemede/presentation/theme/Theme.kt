package com.cemede.cemede.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CemedeGold,
    background = CemedeOliveGreen,
    surface = CemedeOliveGreen,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = CemedeWhite,
    onSurface = CemedeWhite,
)

@Composable
fun CemedeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = getTypography(),
        content = content
    )
}
