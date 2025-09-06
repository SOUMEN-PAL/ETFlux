package org.soumen.home.domain.dataModels

data class MonthlyTimeSeriesData(
    val symbol: String,       // from MetaData
    val date: String,         // key from the map
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long
)