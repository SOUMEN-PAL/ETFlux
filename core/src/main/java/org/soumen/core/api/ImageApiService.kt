package org.soumen.core.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class ImageApiService{
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
                host = ApiConfigs.imageBaseURL
            }
        }
        install(imageAuthPlugin())
    }
}

fun imageAuthPlugin() = createClientPlugin(
    name = "imageAuthPlugin"
){
    onRequest { request, _ ->

        fun imageAuthPlugin() = createClientPlugin(
            name = "imageAuthPlugin"
        ){
            onRequest { request, _ ->
                request.headers.append("X-Api-Key", ApiConfigs.imageApiKey)
            }
        }

    }
}
