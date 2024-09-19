package com.market.crypto.navigation


sealed class NavigationBarScreen(
    val route: String,
) {
    data object Market : NavigationBarScreen(
        route = "market_screen",
    )

    data object Favourites : NavigationBarScreen(
        route = "favourites_screen",
    )

    data object Search : NavigationBarScreen(
        route = "search_screen",
    )
}

sealed class Screen(val route: String) {
    data object Details : Screen("details_screen")
    data object NavigationBar : Screen("navigation_bar_screen")
}