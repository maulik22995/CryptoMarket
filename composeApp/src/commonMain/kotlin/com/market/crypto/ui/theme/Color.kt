package com.market.crypto.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val primary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val positiveGreen :Color,
    val negativeRed : Color,
    val zeroNeutral : Color
)

val dark_primary = Color(0xFFFFFFFF)
val dark_primaryContainer = Color(0xFF384670)
val dark_onPrimaryContainer = Color(0xFFFFFFFF)
val dark_background = Color(0xFF152241)
val dark_onBackground = Color(0xFFFFFFFF)
val dark_surface = Color(0xFF223260)
val dark_onSurface = Color(0xFFFFFFFF)
val dark_onSurfaceVariant = Color(0xFFBDBDBD)

val PositiveGreen = Color(0xFF17C683)
val NegativeRed = Color(0xFFEA3943)
val ZeroWhite = Color(0xFFFFFFFF)
val ZeroBlack = Color(0x00000000)

val light_primary = Color(0xFF223260)
val light_primaryContainer = Color(0xFFCBD8FF)
val light_onPrimaryContainer = Color(0xFF1A233C)
val light_background = Color(0xFFFFFFFF)
val light_onBackground = Color(0xFF152241)
val light_surface = Color(0xFFF2F6FF)
val light_onSurface = Color(0xFF152241)
val light_onSurfaceVariant = Color(0xFF4A4A4A)

val lightExtendedColors = ExtendedColors(
    primary = light_primary,
    primaryContainer = light_primaryContainer,
    onPrimaryContainer = light_onPrimaryContainer,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
    onSurfaceVariant = light_onSurfaceVariant,
    positiveGreen = PositiveGreen,
    negativeRed = NegativeRed,
    zeroNeutral = ZeroBlack
)

val darkExtendedColors = ExtendedColors(
    primary = dark_primary,
    primaryContainer = dark_primaryContainer,
    onPrimaryContainer = dark_onPrimaryContainer,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    onSurfaceVariant = dark_onSurfaceVariant,
    positiveGreen = PositiveGreen,
    negativeRed = NegativeRed,
    zeroNeutral = ZeroWhite
)


/**
 * A [CompositionLocal] that provides the light theme color palette.
 */

val LocalAppColors = staticCompositionLocalOf { lightExtendedColors }