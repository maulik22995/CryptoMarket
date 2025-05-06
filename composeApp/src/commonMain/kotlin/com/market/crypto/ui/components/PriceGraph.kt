package com.market.crypto.ui.components

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.market.crypto.ui.theme.LocalAppColors
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PriceGraph(
    prices: ImmutableList<Double>,
    priceChangePercentage: String,
    isGraphAnimated: Boolean,
    modifier: Modifier
) {

    val minPrice = remember(prices) {
        prices.minOrNull() ?: 0.0
    }

    val maxPrice = remember(prices) {
        prices.maxOrNull() ?: 0.0
    }
    val isDown = priceChangePercentage.startsWith("-")
    val color = if (isDown) LocalAppColors.current.positiveGreen else LocalAppColors.current.negativeRed
    val graphPathColor = remember { color }
    val graphFillGradientColor = remember(graphPathColor) {
        graphPathColor.copy(alpha = 0.5f)
    }
    val animatedPathProgress = remember(prices) {
        AnimationState(0f)
    }

    Canvas(modifier = modifier) {
        val graphPath = calculatedGraphPath(
            prices = prices,
            minPrice = minPrice,
            maxPrice = maxPrice,
            graphHeight = size.height,
            pointSpacing = size.width / prices.size
        )

        val animatedGraphPath = calculateAnimatedPath(
            graphPath = graphPath,
            animatedPathProgress = animatedPathProgress.value
        )

        drawGraphPath(
            path = if (isGraphAnimated) animatedGraphPath else graphPath,
            color = graphPathColor
        )

        val fillPath = calculateFillPath(graphPath = graphPath)

        drawFillPath(
            path = fillPath,
            color = graphFillGradientColor
        )
    }

    if (isGraphAnimated) {
        LaunchedEffect(prices) {
            animatedPathProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = LinearEasing
                )
            )
        }
    }
}

private fun calculatedGraphPath(
    prices: ImmutableList<Double>,
    minPrice: Double,
    maxPrice: Double,
    graphHeight: Float,
    pointSpacing: Float
): Path {
    if (minPrice == maxPrice) {
        calculateStraightPath(
            prices, graphHeight, pointSpacing
        )
    }

    return Path().apply {
        for ((index, price) in prices.withIndex()) {
            val currentX = index * pointSpacing
            val currentRatio = (price - minPrice) / (maxPrice - minPrice)
            val currentY = graphHeight - (currentRatio * graphHeight).toFloat()
            val nextX = (index + 1) * pointSpacing
            val nextPrice = prices.getOrNull(index + 1) ?: prices.last()
            val nextRatio = (nextPrice - minPrice) / (maxPrice - minPrice)
            val nextY = graphHeight - (nextRatio * graphHeight).toFloat()

            if (index == 0) {
                moveTo(currentX, currentY)
            }

            quadraticTo(
                currentX,
                currentY,
                (currentX + nextX) / 2f,
                (currentY + nextY) / 2f
            )
        }
    }

}

private fun calculateStraightPath(
    prices: ImmutableList<Double>,
    graphHeight: Float,
    pointSpacing: Float
): Path {
    return Path().apply {
        moveTo(0f, graphHeight / 2)
        lineTo(prices.lastIndex * pointSpacing, graphHeight / 2)
    }
}

private fun calculateAnimatedPath(
    graphPath: Path,
    animatedPathProgress: Float
): Path {
    val pathMeasure = PathMeasure()
    pathMeasure.setPath(graphPath, false)

    val animatedGraphPath = Path()
    pathMeasure.getSegment(
        startDistance = 0f,
        stopDistance = animatedPathProgress * pathMeasure.length,
        animatedGraphPath
    )

    return animatedGraphPath

}

private fun DrawScope.drawGraphPath(
    path: Path,
    color: Color
) {
    drawPath(path, color, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
}

private fun DrawScope.calculateFillPath(
    graphPath: Path
):Path{
    return graphPath.apply {
        lineTo(size.width * 2 ,size.height)
        lineTo(0f, size.height)
        close()
    }
}

private fun DrawScope.drawFillPath(
    path: Path,
    color: Color
) {
    drawPath(
        path = path,
        brush = Brush.verticalGradient(
            colors = listOf(
                color,
                Color.Transparent
            ),
            endY = size.height
        )
    )
}