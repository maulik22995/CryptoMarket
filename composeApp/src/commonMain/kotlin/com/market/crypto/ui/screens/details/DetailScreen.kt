package com.market.crypto.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.market.crypto.model.CoinChart
import com.market.crypto.model.CoinDetails
import com.market.crypto.ui.components.ErrorState
import com.market.crypto.ui.components.LoadingIndicator
import com.market.crypto.ui.model.ChartPeriod
import com.market.crypto.ui.screens.details.components.CoinChartCard
import com.market.crypto.ui.screens.details.components.CoinChartRangeCard
import com.market.crypto.ui.screens.details.components.EmptyTopBar
import com.market.crypto.ui.screens.details.components.MarketStatsCard
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.card_header_market_stats
import cryptomarket.composeapp.generated.resources.ic_back
import cryptomarket.composeapp.generated.resources.ic_favourite
import cryptomarket.composeapp.generated.resources.ic_fill_heart
import cryptomarket.composeapp.generated.resources.ic_gainer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel<DetailsViewModel>(),
    onNavigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreenUI(
        uiState,
        onNavigateUp,
        onClickChartPeriod = { chartPeriod ->
            viewModel.updateChartPeriod(chartPeriod)
        },
        onToggleFavourite = { viewModel.toggleFavourite() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenUI(
    uiState: DetailsUiState,
    onNavigateUp: () -> Unit,
    onClickChartPeriod: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier,
    onToggleFavourite: (() -> Unit)? = null
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(topBar = {
        when (uiState) {
            is DetailsUiState.Success -> {
                DetailsTopBar(
                    coinDetails = uiState.coinDetails,
                    isCoinFavourite = uiState.isCoinFavourite,
                    onToggleFavourite = onToggleFavourite ?: {},
                    onNavigateUp = onNavigateUp,
                    scrollBehavior = scrollBehavior,
                    modifier = modifier
                )
            }

            else -> {
                EmptyTopBar(onNavigateUp)
            }
        }
    }, modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {scaffoldPadding ->
        when(uiState){
            is DetailsUiState.Success -> {
                DetailsContent(
                    uiState.coinDetails,
                    uiState.coinChart,
                    uiState.chartPeriod,
                    onClickChartPeriod,
                    Modifier.padding(scaffoldPadding)
                )
            }
            is DetailsUiState.Error -> ErrorState(uiState.message ?: "Unknown error")
            DetailsUiState.Loading -> LoadingIndicator()
        }
    }
}

@Composable
fun DetailsContent(
    coinDetails: CoinDetails,
    coinChart: CoinChart,
    chartPeriod: ChartPeriod,
    onClickChartPeriod: (ChartPeriod) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(LocalAppColors.current.background)
        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        CoinChartCard(
            coinDetails.currentPrice,
            coinChart.prices,
            coinChart.periodPriceChangePercentage,
            chartPeriod,
            onClickChartPeriod
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Chart Range",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        CoinChartRangeCard(
            currentPrice = coinDetails.currentPrice,
            minPrice = coinChart.minPrice,
            maxPrice = coinChart.maxPrice,
            isPricesEmpty = coinChart.prices.isEmpty()
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.card_header_market_stats),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        MarketStatsCard(
            coinDetails
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    coinDetails: CoinDetails,
    isCoinFavourite: Boolean,
    onToggleFavourite: () -> Unit,
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier
) {
    LargeTopAppBar(
        navigationIcon = {
            IconButton(modifier = Modifier.size(35.dp).padding(start = 10.dp),onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coinDetails.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = coinDetails.symbol,
                        style = MaterialTheme.typography.titleSmall,
                        color = LocalAppColors.current.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                coil3.compose.AsyncImage(
                    modifier = modifier.size(44.dp).clip(shape = RoundedCornerShape(25.dp)),
                    model = coinDetails.imageUrl,
                    contentDescription = coinDetails.name,
                    contentScale = ContentScale.Fit,
                    onError = { it ->
                        println("url error>> ${coinDetails.imageUrl} error is ${it.result.throwable}")
                    }
                )
                IconButton(onClick = onToggleFavourite) {
                    Icon(
                        painter = if (isCoinFavourite)painterResource(Res.drawable.ic_fill_heart) else painterResource(Res.drawable.ic_favourite),
                        contentDescription = if (isCoinFavourite) "Unfavourite" else "Favourite",
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = LocalAppColors.current.background,
            scrolledContainerColor = LocalAppColors.current.background
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}
