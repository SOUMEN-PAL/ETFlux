package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.soumen.core.db.entities.ImageDataEntity

@Dao
interface ImageEntityDao {
    @Upsert
    suspend fun insertData(data : ImageDataEntity)

    @Query("SELECT image FROM IMAGE_DATA WHERE ticker = :ticker")
    suspend fun getImageURL(ticker: String) : String?
}