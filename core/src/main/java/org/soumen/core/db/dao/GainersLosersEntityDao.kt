package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.soumen.core.db.entities.GainersEntity
import org.soumen.core.db.entities.LosersEntity

@Dao
interface GainersLosersEntityDao {
    @Upsert
    suspend fun upsertGainers(entities: List<GainersEntity>)

    @Upsert
    suspend fun upsertLosers(entities: List<LosersEntity>)

    @Query("SELECT * FROM GainerEntity")
    suspend fun getAllGainers(): List<GainersEntity>

    @Query(value = "SELECT * FROM LosersEntity")
    suspend fun getAllLosers() : List<LosersEntity>

    @Query("DELETE FROM GainerEntity")
    suspend fun deleteAllGainers()

    @Query("DELETE FROM LosersEntity")
    suspend fun deleteAllLosers()
}