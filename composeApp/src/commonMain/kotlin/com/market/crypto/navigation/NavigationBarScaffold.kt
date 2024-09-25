package com.market.crypto.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.market.crypto.ui.screens.market.MarketScreen
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.favourites_screen
import cryptomarket.composeapp.generated.resources.ic_favourite
import cryptomarket.composeapp.generated.resources.ic_market
import cryptomarket.composeapp.generated.resources.ic_search
import cryptomarket.composeapp.generated.resources.market_screen
import cryptomarket.composeapp.generated.resources.search_screen
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationBarScaffold(
    startScreen: NavigationBarScreen,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navigationBarScreen = remember {
        persistentListOf(
            NavigationBarScreen.Market,
            NavigationBarScreen.Favourites,
            NavigationBarScreen.Search
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigationBar = navigationBarScreen.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showNavigationBar) {
                BottomNavigationBar(navController, navigationBarScreen)
            }
        },
        modifier = modifier,
        content = { paddingValues ->
            NavigationBarNavHost(
                navController, startScreen, Modifier.padding(paddingValues)
            )
        }
    )

}

@Composable
fun NavigationBarNavHost(
    navController: NavHostController,
    startScreen: NavigationBarScreen,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        composable(route = NavigationBarScreen.Market.route) {
            MarketScreen()
        }

        composable(route = NavigationBarScreen.Favourites.route) {
            Text("Favourites")
        }

        composable(route = NavigationBarScreen.Search.route) {
            Text("Search")
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    screens: PersistentList<NavigationBarScreen>,
) {

    AppBottomNavigationBar(show = true) {
        screens.forEach { item ->
            AppBottomNavigationBarItem(
                label = stringResource(getNavigationTitle(item)),
                icon = getNavigationIcon(item),
                onClick = {
                    navigateBottomBar(navController, item.route)
                },
                selected = navController.currentBackStackEntry?.destination?.route == item.route
            )
        }
    }
}


private fun navigateBottomBar(navController: NavController, destination: String) {
    navController.navigate(destination) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(NavigationBarScreen.Market.route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun getNavigationIcon(item: NavigationBarScreen): DrawableResource {
    return when (item) {
        NavigationBarScreen.Market -> Res.drawable.ic_market
        NavigationBarScreen.Search -> Res.drawable.ic_search
        NavigationBarScreen.Favourites -> Res.drawable.ic_favourite
    }
}

fun getNavigationTitle(item: NavigationBarScreen): StringResource {
    return when (item) {
        NavigationBarScreen.Market -> Res.string.market_screen
        NavigationBarScreen.Search -> Res.string.search_screen
        NavigationBarScreen.Favourites -> Res.string.favourites_screen
    }
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable (RowScope.() -> Unit),
) {
    val colors = LocalAppColors.current
    Surface(
        color = colors.background,
        contentColor = colors.onBackground,
        modifier = modifier.windowInsetsPadding(BottomNavigationDefaults.windowInsets)
    ) {
        if (show) {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    colors.primary,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Composable
fun RowScope.AppBottomNavigationBarItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
    selected: Boolean,
) {
    val colors = LocalAppColors.current
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .weight(1f)
            .background(
                if (selected) Color.White else colors.primary,
                RoundedCornerShape(10.dp)
            )
            .clickable(
                onClick = onClick,
            )
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = icon.toString(),
            contentScale = ContentScale.Crop,
            colorFilter = if (selected) {
                ColorFilter.tint(colors.primary)
            } else {
                ColorFilter.tint(colors.background)
            },
            modifier = modifier.then(
                Modifier.size(25.dp)
            )
        )

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                colors.primary
            } else {
                colors.background
            }
        )
    }
}