package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.soumen.core.db.entities.WatchlistEntity

@Dao
interface WatchlistEntityDao {

    @Upsert
    suspend fun upsertWatchlist(watchlist : WatchlistEntity)

    @Query("SELECT * FROM watchlist")
    fun getAllWatchlist() : Flow<List<WatchlistEntity>>

}