package com.market.crypto.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val primary: Color,
    val secondaryColor: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val marketUp: Color,
    val marketDown: Color,
    val themeLightDark: Color,
    val themeDark: Color,
    val textThemeColor : Color
)

val lightExtendedColors = ExtendedColors(
    primary = Color(0XFF0224ee),
    secondaryColor = Color(0XFFaf9ef6),
    background = Color(0XFFFFFFFF),
    surface = Color(0XFFFFFFFF),
    error = Color(0XFFB00020),
    onPrimary = Color(0XFFFFFFFF),
    onSecondary = Color(0XFF000000),
    onBackground = Color(0XFF000000),
    onSurface = Color(0XFF000000),
    onError = Color(0XFFFFFFFF),
    marketDown = Color(0XFFFF0000),
    marketUp = Color(0XFF008000),
    themeDark = Color(0XFF000000),
    themeLightDark = Color(0XFF000000).copy(alpha = 0.6f),
    textThemeColor = Color(0XFFFFFFFF)
)

val darkExtendedColors = ExtendedColors(
    primary = Color(0XFF6a6e81),  // A dark purple that works well on dark surfaces
    secondaryColor = Color(0XFF373c4D),  // A teal color often used in Material dark themes
    background = Color(0XFF121212),
    surface = Color(0XFF1E1E1E),
    error = Color(0XFFCF6679),
    onPrimary = Color(0XFF000000),  // Contrast color on lighter primary
    onSecondary = Color(0XFF000000),
    onBackground = Color(0XFFFFFFFF),
    onSurface = Color(0XFFFFFFFF),
    onError = Color(0XFF000000),
    marketDown = Color(0XFFFF0000),
    marketUp = Color(0XFF008000),
    themeDark = Color(0XFFFFFFFF),
    themeLightDark = Color(0XFFFFFFFF).copy(alpha = 0.6f),
    textThemeColor =  Color(0XFF121212),
)


/**
 * A [CompositionLocal] that provides the light theme color palette.
 */

val LocalAppColors = staticCompositionLocalOf { lightExtendedColors }