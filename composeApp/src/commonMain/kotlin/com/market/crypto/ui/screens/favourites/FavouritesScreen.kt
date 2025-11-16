package com.market.crypto.ui.screens.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.market.crypto.data.source.local.database.model.FavouriteCoin
import com.market.crypto.ui.components.LoadingIndicator
import com.market.crypto.ui.theme.LocalAppColors
import kotlinx.collections.immutable.ImmutableList
import okio.FileSystem
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun FavouritesScreen(
    favouritesViewModel: FavouritesViewModel = koinViewModel<FavouritesViewModel>(),
    onCoinClick: (String) -> Unit
) {
    val uiState by favouritesViewModel.uiState.collectAsStateWithLifecycle()

    FavouritesScreenUI(
        uiState = uiState,
        onCoinClick = onCoinClick
    )
}

@Composable
fun FavouritesScreenUI(
    uiState: FavouritesUiState,
    modifier: Modifier = Modifier,
    onCoinClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    
    Scaffold(
        topBar = {
            FavouritesToolBar()
        },
        modifier = modifier.fillMaxSize(),
        containerColor = LocalAppColors.current.background
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }
                uiState.isEmpty -> {
                    EmptyFavouritesState()
                }
                else -> {
                    FavouritesContent(
                        favouriteCoins = uiState.favouriteCoins,
                        onCoinClick = onCoinClick,
                        lazyListState = listState
                    )
                }
            }
        }
    }
}

@Composable
fun FavouritesToolBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            text = "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = LocalAppColors.current.onBackground
        )
    }
}

@Composable
fun EmptyFavouritesState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Favourite Coins",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = LocalAppColors.current.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Add coins to your favourites to see them here",
            fontSize = 14.sp,
            color = LocalAppColors.current.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun FavouritesContent(
    favouriteCoins: ImmutableList<FavouriteCoin>,
    onCoinClick: (String) -> Unit,
    lazyListState: androidx.compose.foundation.lazy.LazyListState,
    modifier: Modifier = Modifier
) {
    if (favouriteCoins.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) { }
    } else {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            modifier = modifier
        ) {
            items(
                count = favouriteCoins.size,
                key = { favouriteCoins[it].id }
            ) { index ->
                val coin = favouriteCoins[index]
                val cardShape = when (index) {
                    0 -> MaterialTheme.shapes.medium.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                    favouriteCoins.lastIndex -> MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp)
                    )
                    else -> androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
                }

                FavouriteCoinItem(
                    coin = coin,
                    cardShape = cardShape,
                    onCoinClick = { onCoinClick(coin.id) }
                )
            }
        }
    }
}

@Composable
fun FavouriteCoinItem(
    coin: FavouriteCoin,
    modifier: Modifier = Modifier,
    cardShape: Shape,
    onCoinClick: () -> Unit
) {
    Surface(
        shape = cardShape,
        color = LocalAppColors.current.surface,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCoinClick() }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable { onCoinClick() }
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                coil3.compose.AsyncImage(
                    modifier = modifier
                        .size(50.dp)
                        .clip(shape = RoundedCornerShape(25.dp)),
                    model = coin.imageUrl,
                    contentDescription = coin.name,
                    contentScale = ContentScale.Fit,
                    onError = { it ->
                        println("url error>> ${coin.imageUrl} error is ${it.result.throwable}")
                    }
                )

                Column(
                    modifier = modifier.padding(start = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = coin.name,
                        maxLines = 1,
                        color = LocalAppColors.current.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = coin.symbol,
                        modifier = modifier.padding(top = 5.dp),
                        maxLines = 1,
                        fontSize = 14.sp,
                        color = LocalAppColors.current.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = modifier.align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.End
            ) {
                val price = coin.price.toDoubleOrNull()?.let {
                    round(it * 100) / 100  // Round to 2 decimal places
                }?.toString() ?: "0.00"  // Default value if conversion fails

                val isDown = coin.change.startsWith("-")
                val change = if (isDown) "${coin.change}%" else "+${coin.change}%"

                Text(
                    text = "$${price}",
                    modifier = modifier.padding(top = 5.dp),
                    maxLines = 1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = LocalAppColors.current.onPrimaryContainer,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = change,
                    modifier = modifier.padding(top = 5.dp),
                    maxLines = 1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDown) LocalAppColors.current.negativeRed else LocalAppColors.current.positiveGreen,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.3)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            newDiskCache()
        }
        .crossfade(true)
        .logger(DebugLogger())
        .build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder()
        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 1GB
        .build()
}

