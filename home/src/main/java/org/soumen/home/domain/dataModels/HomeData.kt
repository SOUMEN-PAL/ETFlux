package org.soumen.home.domain.dataModels

import org.soumen.home.data.networking.homeApiDataModels.MostActivelyTraded
import org.soumen.home.data.networking.homeApiDataModels.TopGainer
import org.soumen.home.data.networking.homeApiDataModels.TopLoser

data class HomeData(
    val lastUpdated: String,
    val metadata: String,
    val topGainers: List<Data>,
    val topLosers: List<Data>
)
