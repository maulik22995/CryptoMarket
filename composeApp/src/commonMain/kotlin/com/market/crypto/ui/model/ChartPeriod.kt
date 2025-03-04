package com.market.crypto.ui.model


enum class ChartPeriod(
    val shortNameId: String,
    val longNameId: String,
    val stringName: String
) {
    Hour(
        shortNameId = "1H",
        longNameId = "Past hour",
        stringName = "1h"
    ),
    Day(
        shortNameId = "1D",
        longNameId = "Past day",
        stringName = "24h"
    ),
    Week(
        shortNameId = "1W",
        longNameId = "Past week",
        stringName = "7d"
    ),
    Month(
        shortNameId = "1M",
        longNameId = "Past month",
        stringName = "30d"
    ),
    ThreeMonth(
        shortNameId = "3M",
        longNameId = "Past 3 month",
        stringName = "3m"
    ),
    Year(
        shortNameId = "1Y",
        longNameId = "Past year",
        stringName = "1y"
    ),
    FiveYear(
        shortNameId = "5Y",
        longNameId = "Past 5 years",
        stringName = "5y"
    )
}
