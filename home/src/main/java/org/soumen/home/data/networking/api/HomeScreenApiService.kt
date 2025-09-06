package org.soumen.home.data.networking.api

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import org.soumen.core.api.AlphaVantageApi
import org.soumen.core.api.BaseApiService
import org.soumen.home.data.networking.homeApiDataModels.HomeApiResponse
import org.soumen.home.data.networking.timeSeriesResponseDataModel.MonthlyStockResponse

class HomeScreenApiService: BaseApiService() {




    suspend fun getTopGainersLosers(): HomeApiResponse {
        val data =  client.get {
            url {
                // endpoint function parameter
                parameters.append("function", AlphaVantageApi.TOP_GAINERS_LOSERS)

                // the authPlugin already appends API key
            }
        }


        Log.e("HomeScreenApiService", "Response: ${data.bodyAsText()} ${data.status.value}" )

        return data.body()
    }

    suspend fun getTickerMonthlyData(ticker : String): MonthlyStockResponse {
        val response = client.get {
            url{
                parameters.append("function", AlphaVantageApi.TIME_SERIES_MONTHLY)
                parameters.append("symbol", ticker)
            }
        }
//        Log.e("HomeScreenApiService", "Monthly Response: ${response.bodyAsText()} ${response.status.value}" )
        return response.body()
    }

}