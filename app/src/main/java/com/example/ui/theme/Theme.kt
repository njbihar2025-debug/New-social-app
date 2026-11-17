package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = KosiTeal,
    onPrimary = Color.White,
    primaryContainer = KosiTealContainer,
    onPrimaryContainer = OnKosiTealContainer,
    secondary = MarigoldSaffron,
    onSecondary = Color.White,
    secondaryContainer = MarigoldContainer,
    onSecondaryContainer = OnMarigoldContainer,
    tertiary = MithilaEmerald,
    onTertiary = Color.White,
    tertiaryContainer = MithilaEmeraldContainer,
    onTertiaryContainer = OnMithilaEmeraldContainer,
    error = CrimsonAlert,
    onError = Color.White,
    errorContainer = CrimsonContainer,
    onErrorContainer = Color(0xFF410E0B),
    background = WarmIvory,
    onBackground = WarmInk,
    surface = WarmIvory,
    onSurface = WarmInk,
    surfaceVariant = WarmSand,
    onSurfaceVariant = WarmSlate,
    surfaceContainerLowest = PureCreamCard,
    surfaceContainerLow = WarmSand,
    surfaceContainer = WarmSand,
    surfaceContainerHigh = WarmSandHigh,
    outline = WarmOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = TealDarkPrimary,
    onPrimary = Color(0xFF003542),
    primaryContainer = TealDarkContainer,
    onPrimaryContainer = Color(0xFFB5EBFA),
    secondary = SaffronDarkSecondary,
    onSecondary = Color(0xFF471D00),
    secondaryContainer = SaffronDarkContainer,
    onSecondaryContainer = Color(0xFFFFDBC2),
    tertiary = EmeraldDarkTertiary,
    onTertiary = Color(0xFF003824),
    tertiaryContainer = EmeraldDarkContainer,
    onTertiaryContainer = Color(0xFFB3F5D8),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = DeepRiverBg,
    onBackground = DarkInk,
    surface = DeepRiverBg,
    onSurface = DarkInk,
    surfaceVariant = DeepRiverSurface,
    onSurfaceVariant = DarkSlate,
    surfaceContainerLowest = DeepRiverSurface,
    surfaceContainerLow = DeepRiverSurface,
    surfaceContainer = DeepRiverSurfaceHigh,
    surfaceContainerHigh = Color(0xFF213C45),
    outline = DarkOutline
)

val ApnaSupaulShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun ApnaSupaulTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ApnaSupaulTypography,
        shapes = ApnaSupaulShapes,
        content = content
    )
}
