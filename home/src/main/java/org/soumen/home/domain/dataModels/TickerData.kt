package org.soumen.home.domain.dataModels

data class TickerData(
    val AssetType: String ="",
    val CIK: String = "",
    val Country: String = "",
    val Currency: String = "",
    val Description: String = "",
    val Exchange: String = "",
    val Name: String = "",
    val Symbol: String = ""
)
