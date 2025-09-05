package org.soumen.home.data.networking.homeApiDataModels

import kotlinx.serialization.Serializable

@Serializable
data class HomeApiResponse(
    val last_updated: String? = null,
    val metadata: String? = null,
    val most_actively_traded: List<MostActivelyTraded>? = null,
    val top_gainers: List<TopGainer>? = null,
    val top_losers: List<TopLoser>?=null
)