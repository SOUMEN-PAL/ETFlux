package org.soumen.shared.domain.repository

import android.util.Log
import org.soumen.core.api.ApiConfigs
import org.soumen.core.db.dao.ImageEntityDao
import org.soumen.core.db.entities.ImageDataEntity
import org.soumen.shared.data.network.ImageService
import org.soumen.shared.data.network.TickerInfoApiService

class ImageDataRepository(
    private val  imageService: ImageService,
    private val tickerInfoApiService: TickerInfoApiService,
    private val imageDao : ImageEntityDao
) {

    suspend fun getImageDataURL(ticker: String): Result<String> = runCatching {
        imageDao.getImageURL(ticker) ?: run {

            val cleanTicker = ticker.replace(Regex("[^A-Za-z0-9]"), "")
            val imageLink = "https://img.logo.dev/ticker/$cleanTicker?token=${ApiConfigs.clearbitApiKey}"
            Log.e("FullName" ,imageLink)
            imageDao.insertData(
                ImageDataEntity(
                    image = imageLink,
                    name =  "",
                    ticker = ""
                )
            )
            imageLink
        }
    }

}