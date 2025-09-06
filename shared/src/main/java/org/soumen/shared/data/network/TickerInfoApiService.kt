package org.soumen.shared.data.network

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.soumen.core.api.AlphaVantageApi
import org.soumen.core.api.BaseApiService
import org.soumen.shared.data.tickerInfoResponseModel.TickerInfo

class TickerInfoApiService : BaseApiService() {

    suspend fun getTicketData(ticker: String): TickerInfo {
        val response = client.get {
            url {
                parameters.append("symbol", ticker)
                parameters.append("function", AlphaVantageApi.OVERVIEW)
            }
        }

        Log.e("TickerInfo", response.bodyAsText())
        return response.body()
    }

}