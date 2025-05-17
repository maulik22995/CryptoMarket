package com.market.crypto.ui.screens.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.market.crypto.ui.theme.LocalAppColors
import cryptomarket.composeapp.generated.resources.Res
import cryptomarket.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyTopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
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
        title = {},
        modifier = modifier,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = LocalAppColors.current.background
        ),
    )
}

@Preview
@Composable
private fun DetailsEmptyTopBarPreview() {
    MaterialTheme {
        EmptyTopBar(
            onNavigateUp = {}
        )
    }
}