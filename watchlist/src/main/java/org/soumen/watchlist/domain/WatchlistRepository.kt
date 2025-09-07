package org.soumen.watchlist.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.soumen.core.db.dao.BookmarkEntityDao
import org.soumen.core.db.dao.WatchlistEntityDao
import org.soumen.home.domain.dataModels.Data
import org.soumen.watchlist.domain.datamodules.WatchlistData

class WatchlistRepository(
    private val watchlistEntityDao: WatchlistEntityDao,
    private val bookmarkEntityDao: BookmarkEntityDao
) {

    suspend fun getAllWatchlistItems() = watchlistEntityDao.getAllWatchlistOnce()
    fun getWatchList(): Flow<List<WatchlistData>> =
        watchlistEntityDao.getAllWatchlist().map { entities ->
            entities.map { entity ->
                WatchlistData(
                    id = entity.watchlistId,
                    name = entity.watchlistName
                )
            }
        }

    suspend fun deleteWatchList(id: Long) {
        watchlistEntityDao.deleteWatchlist(id)
    }

    suspend fun getAllBookmarksWithWatchlistID(id: Long): Result<List<Data>> {
        try{
            val data = bookmarkEntityDao.getBookmarksForWatchlist(id)
            val result =  data.map {
                Data(
                    changeAmount = it.changeAmount,
                    changePercentage = it.changePercentage,
                    price = it.price,
                    ticker = it.ticker,
                    volume = it.volume
                )
            }

            return Result.success(result)
        }catch (e : Exception){
            return Result.failure(e)
        }
    }

}