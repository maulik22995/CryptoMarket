package com.market.crypto.data.source.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.market.crypto.ui.model.Percentage
import kotlinx.collections.immutable.ImmutableList

@Entity
data class FavouriteCoin(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val change: String
)
