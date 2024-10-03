package com.market.crypto.ui.screens.market

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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import coil3.util.DebugLogger
import com.market.crypto.data.source.local.model.CoinSort
import com.market.crypto.data.source.remote.model.CoinApiModel
import com.market.crypto.ui.components.LoadingIndicator
import com.market.crypto.ui.model.MarketUiState
import com.market.crypto.ui.theme.LocalAppColors
import kotlinx.collections.immutable.ImmutableList
import okio.FileSystem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MarketScreen(marketViewModel: MarketViewModel = koinViewModel<MarketViewModel>()) {
    val uiState by marketViewModel.uiState.collectAsStateWithLifecycle()

    MarketScreenUI(
        uiState
    )
}

@Composable
fun MarketScreenUI(
    uiState: MarketUiState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    Scaffold(
        topBar = {

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
                        onCoinClick = {},
                        coinSort = uiState.coinSort,
                        onUpdateCoinSort = {},
                        lazyListState = listState
                    )
                }
            }
        }
    }
}

@Composable
fun MarketContent(
    coins: ImmutableList<CoinApiModel>, onCoinClick: (CoinApiModel) -> Unit,
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
            items(
                count = coins.size,
                key = { coins[it].uuid!! },
                itemContent = { index ->
                    val coinListItem = coins[index]

                    MarketCoinItem(
                        coinListItem,
                        Modifier
                    )
                }
            )
        }
    }
}

@Composable
fun MarketCoinItem(
    coin: CoinApiModel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        coil3.compose.AsyncImage(
            modifier = modifier.size(40.dp),
            model = coin.iconUrl,
            contentDescription = coin.name,
            contentScale = ContentScale.Inside,
            onError = { it ->
                println("url error>> ${coin.iconUrl} error is ${it.result.throwable}")
            }
        )

        Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = coin.name!!,
                maxLines = 1,
                color = LocalAppColors.current.primary,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = coin.symbol!!,
                maxLines = 1,
                color = LocalAppColors.current.primary,
                overflow = TextOverflow.Ellipsis
            )
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