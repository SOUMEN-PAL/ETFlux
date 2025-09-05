package org.soumen.core.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.request
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class BaseApiService {
    
    protected val client = HttpClient(engine = OkHttp.create()){
        install(Logging){
            level = LogLevel.INFO
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

        install(DefaultRequest){
            url{
                protocol = URLProtocol.HTTPS
                host = AlphaVantageApi.BASE_URL
            }
        }

        install(authPlugin())
    }
    
}

fun authPlugin() = createClientPlugin(
    name = "AuthPlugin"
){
    onRequest { request,_->
        val apikey = AlphaVantageApi.API_KEY
        request.url.parameters.append("apikey", apikey)
    }
}

object AlphaVantageApi {
    val BASE_URL = ApiConfigs.baseURL
    val API_KEY = ApiConfigs.apiKey

    // Functions
    const val OVERVIEW = "OVERVIEW"
    const val TIME_SERIES_DAILY = "TIME_SERIES_DAILY_ADJUSTED"
    const val TIME_SERIES_MONTHLY = "TIME_SERIES_MONTHLY_ADJUSTED"
    const val SYMBOL_SEARCH = "SYMBOL_SEARCH"
    const val TOP_GAINERS_LOSERS = "TOP_GAINERS_LOSERS"
}