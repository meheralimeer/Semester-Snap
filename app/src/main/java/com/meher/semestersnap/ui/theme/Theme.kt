package com.meher.semestersnap.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color

@Composable
fun SemesterSnapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Automatically detects system dark mode
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Set to true to enable dynamic color if available
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        !darkTheme -> LightColorScheme
        else -> DarkColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}




// Define a Light Color Scheme for Material 3
// We map our custom colors to Material 3's color system
val LightColorScheme = lightColorScheme(
    primary = LightAccentBlue, // Main accent color (e.g., for buttons, active states)
    onPrimary = Color.White, // Text/icons on primary color

    primaryContainer = LightAccentBlue.copy(alpha = 0.1f), // Lighter version of primary for containers
    onPrimaryContainer = LightTextPrimary,

    secondary = LightSuccessGreen, // Secondary accent color (e.g., for success indicators)
    onSecondary = Color.White,
    secondaryContainer = LightSuccessGreen.copy(alpha = 0.1f),
    onSecondaryContainer = LightTextPrimary,

    tertiary = LightProgressBarPurple, // Another accent color (can be used for other highlights)
    onTertiary = Color.White,
    tertiaryContainer = LightProgressBarPurple.copy(alpha = 0.1f),
    onTertiaryContainer = LightTextPrimary,

    background = LightPrimaryBackground, // Main screen background
    onBackground = LightTextPrimary, // Text color on the background

    surface = LightCardBackground, // Card backgrounds, elevated surfaces
    onSurface = LightTextPrimary, // Text color on surfaces
    surfaceVariant = LightDividerColor, // Used for subtle dividers or backgrounds
    onSurfaceVariant = LightTextSecondary, // Text color on surface variants

    error = Color(0xFFB00020), // Standard error color
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF410002),

    outline = LightDividerColor, // Outlines for components
    outlineVariant = LightDividerColor, // Variant for outlines
)

// Define a Dark Color Scheme for Material 3
// We map our custom dark colors to Material 3's color system
val DarkColorScheme = darkColorScheme(
    primary = DarkAccentTeal, // Main accent color (e.g., for happy face icon)
    onPrimary = Color.Black, // Text/icons on primary color (black for contrast on teal)

    primaryContainer = DarkOptimizeButtonBackground, // Using optimize button background as primary container
    onPrimaryContainer = DarkOptimizeButtonText, // Text on primary container

    secondary = DarkSuccessGreen, // Secondary accent color (e.g., for success indicators)
    onSecondary = Color.White,
    secondaryContainer = DarkSuccessGreen.copy(alpha = 0.2f),
    onSecondaryContainer = DarkTextPrimary,

    tertiary = DarkProgressBarPurple, // Another accent color
    onTertiary = Color.White,
    tertiaryContainer = DarkProgressBarPurple.copy(alpha = 0.2f),
    onTertiaryContainer = DarkTextPrimary,

    background = DarkPrimaryBackground, // Main screen background (pure black)
    onBackground = DarkTextPrimary, // Text color on the background (white)

    surface = DarkCardBackground, // Card backgrounds, elevated surfaces
    onSurface = DarkTextPrimary, // Text color on surfaces
    surfaceVariant = DarkDividerColor, // Used for subtle dividers or backgrounds
    onSurfaceVariant = DarkTextSecondary, // Text color on surface variants

    error = Color(0xFFCF6679), // Standard error color for dark theme
    onError = Color.Black,
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFFFFDAD4),

    outline = DarkDividerColor, // Outlines for components
    outlineVariant = DarkDividerColor, // Variant for outlines
)
