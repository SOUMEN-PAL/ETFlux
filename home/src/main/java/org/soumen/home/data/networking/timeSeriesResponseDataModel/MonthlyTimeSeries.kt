package org.soumen.home.data.networking.timeSeriesResponseDataModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyStockResponse(
    @SerialName("Meta Data")
    val metaData: MetaData,

    // Accept either "Monthly Time Series" or "Time Series (Monthly)" by making both nullable
    @SerialName("Monthly Time Series")
    private val monthlyTimeSeriesLegacy: Map<String, TimeSeriesData>? = null,
    @SerialName("Time Series (Monthly)")
    private val monthlyTimeSeriesNew: Map<String, TimeSeriesData>? = null
) {
    val monthlyTimeSeries: Map<String, TimeSeriesData>
        get() = monthlyTimeSeriesLegacy ?: monthlyTimeSeriesNew ?: emptyMap()
}

@Serializable
data class MetaData(
    @SerialName("1. Information") val information: String,
    @SerialName("2. Symbol") val symbol: String,
    @SerialName("3. Last Refreshed") val lastRefreshed: String,
    @SerialName("4. Time Zone") val timeZone: String
)

@Serializable
data class TimeSeriesData(
    @SerialName("1. open") val open: String,
    @SerialName("2. high") val high: String,
    @SerialName("3. low") val low: String,
    @SerialName("4. close") val close: String,
    @SerialName("5. volume") val volume: String
)
