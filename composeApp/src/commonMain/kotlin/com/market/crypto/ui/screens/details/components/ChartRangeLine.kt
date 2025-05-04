package com.market.crypto.ui.screens.details.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import com.market.crypto.extension.toSanitisedDoubleOrZero
import com.market.crypto.ui.theme.LocalAppColors

@Composable
fun ChartRangeLine(
    currentPrice : String,
    minPrice : String,
    maxPrice : String,
    modifier: Modifier = Modifier
){
    val currentPriceValue = currentPrice.toSanitisedDoubleOrZero()
    val minPriceValue = minPrice.toSanitisedDoubleOrZero()
    val maxPriceValue = maxPrice.toSanitisedDoubleOrZero()

    val latestMinPrice = remember(currentPriceValue,minPriceValue) { minOf(currentPriceValue, minPriceValue) }
    val latestMaxPrice = remember(currentPriceValue,maxPriceValue) { maxOf(currentPriceValue, maxPriceValue) }

    val currentMinDiff = remember(currentPriceValue,latestMinPrice) {
        currentPriceValue - latestMinPrice
    }

    val maxMinDiff = remember(latestMaxPrice, latestMinPrice) {
        latestMaxPrice - latestMinPrice
    }

    val currentPriceRatio = remember(currentMinDiff, maxMinDiff) {
        when {
            (currentMinDiff.compareTo(maxMinDiff) == 0) -> 0.5f
            (currentMinDiff.compareTo(0.0) == 0) -> 0f
            (maxMinDiff.compareTo(0.0) == 0) -> 1f
            else -> (currentMinDiff / maxMinDiff).toFloat()
        }
    }


    val negativeRed = LocalAppColors.current.marketDown


    val positiveGreen = LocalAppColors.current.marketUp

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeightMiddle = size.height / 2f
        val lineWidth = 20f

        drawLine(
            start = Offset(
                x = canvasWidth * currentPriceRatio,
                y = canvasHeightMiddle
            ),
            end = Offset(
                x = canvasWidth,
                y = canvasHeightMiddle
            ),
            color = negativeRed,
            strokeWidth = lineWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            start = Offset(
                x = 0f,
                y = canvasHeightMiddle
            ),
            end = Offset(
                x = canvasWidth * currentPriceRatio,
                y = canvasHeightMiddle
            ),
            color = positiveGreen,
            strokeWidth = lineWidth,
            cap = StrokeCap.Round
        )
    }
}