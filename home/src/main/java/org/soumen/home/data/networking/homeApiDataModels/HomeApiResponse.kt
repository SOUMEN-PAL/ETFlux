package org.soumen.home.data.networking.homeApiDataModels

import kotlinx.serialization.Serializable

@Serializable
data class HomeApiResponse(
    val lastUpdated: String,
    val metadata: String,
    val mostActivelyTraded: List<MostActivelyTraded>,
    val topGainers: List<TopGainer>,
    val topLosers: List<TopLoser>
)