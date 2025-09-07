package org.soumen.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.soumen.core.db.entities.BookmarkEntity

@Dao
interface BookmarkEntityDao {

    // Insert a single bookmark
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    // Insert multiple bookmarks
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(bookmarks: List<BookmarkEntity>)

    // Delete a bookmark by entity
    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    // Delete all bookmarks for a given watchlist
    @Query("DELETE FROM bookmark WHERE ticker = :ticker")
    suspend fun deleteFormBookmark(ticker: String)

    // Get all bookmarks for a given watchlist
    @Query("SELECT * FROM bookmark WHERE watchlistId = :watchlistId")
    suspend fun getBookmarksForWatchlist(watchlistId: Long): List<BookmarkEntity>

    // Get a single bookmark by ticker (unique since it’s PK)
    @Query("SELECT * FROM bookmark WHERE ticker = :ticker")
    suspend fun getBookmarkByTicker(ticker: String): BookmarkEntity?

    @Query("SELECT COUNT(*) FROM bookmark WHERE ticker = :ticker")
    fun isBookmarkedFlow(ticker: String): Flow<Int>
}