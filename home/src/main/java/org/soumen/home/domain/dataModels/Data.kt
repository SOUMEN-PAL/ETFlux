package org.soumen.home.domain.dataModels

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)