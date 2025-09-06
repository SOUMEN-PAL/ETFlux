package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.soumen.core.db.entities.TickerInfoEntity

@Dao
interface TickerEntityDao {
    @Upsert
    suspend fun upsertTickerInfo(entity: TickerInfoEntity)

    @Query("SELECT * FROM TickerInfoEntity WHERE Symbol = :ticker LIMIT 1")
    suspend fun getTickerData(ticker : String) : TickerInfoEntity?
}