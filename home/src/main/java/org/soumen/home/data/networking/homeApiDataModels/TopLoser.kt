package org.soumen.home.data.networking.homeApiDataModels
import kotlinx.serialization.Serializable

@Serializable
data class TopLoser(
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)