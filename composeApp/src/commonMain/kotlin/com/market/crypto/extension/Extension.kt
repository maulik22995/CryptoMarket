package com.market.crypto.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.round


private val million = 1000000
private val billion = 1000000000
private val trillion = 1000000000000
private val quadrillion = 1000000000000000
private val quintillion = 1000000000000000000

private val belowOneRange = -1.00..1.00
private val belowMillionRange = 1.00..<million.toDouble()
private val millionRange = million.toDouble()..<billion.toDouble()
private val billionRange = billion.toDouble()..<trillion.toDouble()
private val trillionRange = trillion.toDouble()..<quadrillion.toDouble()
private val quadrillionRange = quadrillion.toDouble()..<quintillion.toDouble()



fun String?.toSanitisedDoubleOrZero(): Double {
    return try {
        if (this != null) {
            val sanitisedString = this.filterNot { it == ',' }.trim()
            sanitisedString.toDouble()
        } else {
            0.0
        }
    } catch (e: NumberFormatException) {
        0.0
    }
}

fun String?.formattedAmount() : String {
    val amount = this?.toDoubleOrNull()?.let {
        round(it * 100) / 100  // Round to 2 decimal places
    } ?: 0.00  // Default value if conversion fails

    return when {
        // Must be checked in this order
        this.isNullOrBlank() -> "$--"
        amount in belowOneRange -> "$${amount}"
        amount in belowMillionRange -> "$${amount}"
        else -> formatLargeAmount(amount)
    }
}

private fun formatLargeAmount(amount : Double): String {

    val divisor = when (amount) {
        in millionRange -> million
        in billionRange -> billion
        in trillionRange -> trillion
        in quadrillionRange -> quadrillion
        else -> 1
    }

    val symbol = when (amount) {
        in millionRange -> "M"
        in billionRange -> "B"
        in trillionRange -> "T"
        in quadrillionRange -> "Q"
        else -> ""
    }

    val shortenedAmount = (amount / divisor.toDouble()).let {
        round(it * 100) / 100  // Round to 2 decimal places
    }
    return "$shortenedAmount $symbol"
}

fun formatDate(timestamp: Long?): String {
    if (timestamp == null) return ""

    val instant = Instant.fromEpochMilliseconds(timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth
    val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val year = dateTime.year

    return "$day $month $year"
}