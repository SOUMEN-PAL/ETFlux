package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.soumen.core.db.entities.MonthlyStockEntity

@Dao
interface MonthlyStockEntityDao {
    @Upsert
    suspend fun upsertAll(entities: List<MonthlyStockEntity>)

    @Query(
        """
    SELECT * 
    FROM monthly_stock 
    WHERE symbol = :ticker 
    ORDER BY date DESC 
    LIMIT :limit
    """
    )
    suspend fun getAll(limit: Int, ticker: String): List<MonthlyStockEntity>?
}