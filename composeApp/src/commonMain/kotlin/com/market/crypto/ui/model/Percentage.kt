package com.market.crypto.ui.model


// Import for `pow`
import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.math.round

@Serializable
data class Percentage(private val percentage: String?) {

    val amount: Double = percentage.toSanitizedDoubleOrZero()

    private val roundedAmount: Double = amount.roundToScale(2)
    val isPositive: Boolean = roundedAmount > 0
    val isNegative: Boolean = roundedAmount < 0

    val formattedAmount: String =
        when {
            percentage == null -> "-- %"
            isNegative -> formatPercentage(amount / 100)
            else -> "+" + formatPercentage(amount / 100)
        }

    private fun formatPercentage(value: Double): String {
        return "${value.roundToScale(2)}%"
    }
}

// Extension function for sanitizing and converting a string to Double
fun String?.toSanitizedDoubleOrZero(): Double {
    return this?.toDoubleOrNull() ?: 0.0
}

// Extension function for rounding a Double to a specific number of decimal places
fun Double.roundToScale(scale: Int): Double {
    val factor = 10.0.pow(scale)
    return round(this * factor) / factor
}



