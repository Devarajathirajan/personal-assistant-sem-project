package com.example.personalassistant.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFAC58F5),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF41CBBF),
    secondary = Color(0xFF164E63),
    secondaryVariant = Color(0xFF64748B),
    onSecondary = Color.White,
    background = Color(0xFF0F172A),
    onBackground = Color.White,
    surface = Color(0xFFF62F63),
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFAC58F5),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF41CBBF),
    secondary = Color(0xFF164E63),
    secondaryVariant = Color(0xFF64748B),
    onSecondary = Color.White,
    background = Color(0xFF0F172A),
    onBackground = Color.White,
    surface = Color(0xFFF62F63),
    onSurface = Color.White
)

@Composable
fun PersonalAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}