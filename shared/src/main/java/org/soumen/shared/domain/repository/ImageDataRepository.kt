package org.soumen.shared.domain.repository

import org.soumen.core.db.dao.ImageEntityDao
import org.soumen.core.db.entities.ImageDataEntity
import org.soumen.shared.data.network.ImageService

class ImageDataRepository(
    private val  imageService: ImageService,
    private val imageDao : ImageEntityDao
) {

    suspend fun getImageDataURL(ticker: String): Result<String> = runCatching {
        imageDao.getImageURL(ticker) ?: run {
            val data = imageService.fetchImageData(ticker).first()
            imageDao.insertData(
                ImageDataEntity(
                    image = data.image,
                    name = data.name,
                    ticker = data.ticker
                )
            )
            data.image
        }
    }



}