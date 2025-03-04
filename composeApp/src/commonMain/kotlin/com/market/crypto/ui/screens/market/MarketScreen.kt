package com.market.crypto.ui.screens.market

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
import com.market.crypto.data.source.local.database.model.Coin
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.ui.components.CoinSortChip
import com.market.crypto.ui.components.LoadingIndicator
import com.market.crypto.ui.model.MarketUiState
import com.market.crypto.ui.model.Percentage
import com.market.crypto.ui.model.TimeOfDay
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.market_is_down
import cryptomarket.composeapp.generated.resources.market_is_flat
import cryptomarket.composeapp.generated.resources.market_is_up
import kotlinx.collections.immutable.ImmutableList
import okio.FileSystem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun MarketScreen(
    marketViewModel: MarketViewModel = koinViewModel<MarketViewModel>(),
    onCoinClick: (Coin) -> Unit
) {
    val uiState by marketViewModel.uiState.collectAsStateWithLifecycle()

    MarketScreenUI(
        uiState,
        onCoinClick = onCoinClick,
        onUpdateCoinSort = { coinSort ->
            marketViewModel.updateCoinSort(coinSort)
        }
    )
}

@Composable
fun MarketScreenUI(
    uiState: MarketUiState,
    modifier: Modifier = Modifier,
    onUpdateCoinSort: (CoinSort) -> Unit,
    onCoinClick: (Coin) -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    Scaffold(
        topBar = {
            MarketToolBar(uiState.timeOfDay, uiState.marketCapChangePercentage24h)
        },
        modifier = modifier.fillMaxSize()
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.padding(padding)

        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }

                else -> {
                    MarketContent(
                        coins = uiState.coins,
                        onCoinClick = onCoinClick,
                        coinSort = uiState.coinSort,
                        onUpdateCoinSort = onUpdateCoinSort,
                        lazyListState = listState
                    )
                }
            }
        }
    }
}

@Composable
fun MarketToolBar(timeOfDay: TimeOfDay, marketCapChangePercentage24h: Percentage?) {
    Column(modifier = Modifier.animateContentSize().padding(horizontal = 20.dp, vertical = 15.dp)) {
        Text(
            text = "Good ${timeOfDay.name.toString()}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = LocalAppColors.current.themeDark
        )
        marketCapChangePercentage24h?.let {
            Text(
                text = when {
                    it.isPositive -> stringResource(Res.string.market_is_up)
                    it.isNegative -> stringResource(Res.string.market_is_down)
                    else -> stringResource(Res.string.market_is_flat)
                },
                color = LocalAppColors.current.themeDark
            )
        }
    }
}

@Composable
fun MarketContent(
    coins: ImmutableList<Coin>, onCoinClick: (Coin) -> Unit,
    coinSort: CoinSort,
    onUpdateCoinSort: (CoinSort) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    if (coins.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) { }
    } else {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            modifier = modifier
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 8.dp)
                ) {
                    CoinSort.entries.forEach { coinSortEntry ->
                        CoinSortChip(
                            coinSort = coinSortEntry,
                            selected = coinSortEntry == coinSort,
                            onClick = { onUpdateCoinSort(coinSortEntry) }
                        )
                    }
                }
            }
            items(
                count = coins.size,
                key = { coins[it].id },
                itemContent = { index ->
                    val coinListItem = coins[index]

                    MarketCoinItem(
                        coinListItem,
                        Modifier,
                        onCoinClick
                    )
                }
            )
        }
    }
}

@Composable
fun MarketCoinItem(
    coin: Coin,
    modifier: Modifier = Modifier,
    onCoinClick: (Coin) -> Unit
) {
    Card(modifier = modifier.padding(10.dp).clickable {
        onCoinClick(coin)
    }, shape = RoundedCornerShape(8.dp)) {
        Box(modifier = modifier.fillMaxWidth().padding(10.dp).clickable {
            onCoinClick(coin)
        }) {
            Row(
                modifier = modifier, verticalAlignment = Alignment
                    .CenterVertically
            ) {
                coil3.compose.AsyncImage(
                    modifier = modifier.size(50.dp).clip(shape = RoundedCornerShape(25.dp)),
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
                        color = LocalAppColors.current.onSecondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = coin.symbol,
                        modifier = modifier.padding(top = 5.dp),
                        maxLines = 1,
                        fontSize = 14.sp,
                        color = LocalAppColors.current.primary,
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
                    color = LocalAppColors.current.onSecondary,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = change,
                    modifier = modifier.padding(top = 5.dp),
                    maxLines = 1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDown) LocalAppColors.current.marketDown else LocalAppColors.current.marketUp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
}