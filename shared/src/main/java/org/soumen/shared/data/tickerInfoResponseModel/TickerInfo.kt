package org.soumen.shared.data.tickerInfoResponseModel

import kotlinx.serialization.Serializable

@Serializable
data class TickerInfo(
    val AssetType: String? = "",
    val CIK: String? = "",
    val Country: String? = "",
    val Currency: String? = "",
    val Description: String? = "",
    val Exchange: String? = "",
    val Name: String? = "",
    val Symbol: String? = ""
)