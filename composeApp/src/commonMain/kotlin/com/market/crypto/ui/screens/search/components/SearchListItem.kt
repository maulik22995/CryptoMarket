package com.market.crypto.ui.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.market.crypto.model.SearchCoin
import com.market.crypto.ui.theme.LocalAppColors

@Composable
fun SearchListItem(
    searchCoin: SearchCoin,
    onCoinClick: (SearchCoin) -> Unit,
    cardShape: Shape,
    modifier: Modifier = Modifier
){
    Surface(
        shape = cardShape,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCoinClick(searchCoin)
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {


            coil3.compose.AsyncImage(
                modifier = modifier.size(50.dp).clip(shape = RoundedCornerShape(25.dp)),
                model = searchCoin.imageUrl,
                contentDescription = searchCoin.name,
                contentScale = ContentScale.Fit,
                onError = { it ->
                    println("url error>> ${searchCoin.imageUrl} error is ${it.result.throwable}")
                }
            )


            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = searchCoin.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = searchCoin.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalAppColors.current.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}