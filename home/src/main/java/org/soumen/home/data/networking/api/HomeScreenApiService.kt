package org.soumen.home.data.networking.api

import io.ktor.client.call.body
import io.ktor.client.request.get
import org.soumen.core.api.AlphaVantageApi
import org.soumen.core.api.BaseApiService
import org.soumen.home.data.networking.homeApiDataModels.HomeApiResponse

class HomeScreenApiService: BaseApiService() {

    suspend fun getTopGainersLosers(): HomeApiResponse {
        // You can change the return type to your data model later
        return client.get {
            url {
                // endpoint function parameter
                parameters.append("function", AlphaVantageApi.TOP_GAINERS_LOSERS)
                // the authPlugin already appends API key
            }
        }.body()
    }

}