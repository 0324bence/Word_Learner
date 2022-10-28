package com.bence.wordlearner.ui.theme

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val DarkThemeColors = darkColorScheme(
//    primary = Purple200,
//    primaryContainer = Purple700,
//    secondary = Teal200
)

private val LightThemeColors = lightColorScheme(
//    primary = Purple500,
//    primaryContainer = Purple700,
//    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WordLearnerTheme(darkTheme: Boolean = isSystemInDarkTheme(), isDynamicColor: Boolean = true, content: @Composable () -> Unit) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        dynamicColor && darkTheme -> {
            dynamicDarkColorScheme(LocalContext.current)
        }
        dynamicColor && !darkTheme -> {
            dynamicLightColorScheme(LocalContext.current)
        }
        darkTheme -> DarkThemeColors
        else -> LightThemeColors
    }
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colors.surfaceColorAtElevation(2f.dp).toArgb()
        window.navigationBarColor = colors.surfaceColorAtElevation(2f.dp).toArgb()
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightNavigationBars = !darkTheme
    }


    MaterialTheme(
        colorScheme = colors,
//        typography = ,
//        shapes = Shapes,
        content = content
    )
}