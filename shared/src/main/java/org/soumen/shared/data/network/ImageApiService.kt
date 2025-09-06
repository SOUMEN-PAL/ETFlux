package org.soumen.shared.data.network

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import org.soumen.core.api.ImageApiService
import org.soumen.shared.data.imageResponseModel.ImageDataResponseItem

class ImageService : ImageApiService() {

    suspend fun fetchImageData(ticker : String): List<ImageDataResponseItem> {
        val dat = client.get{
           url{
               parameters.append("ticker", ticker)
           }
        }
        return dat.body()
    }
}