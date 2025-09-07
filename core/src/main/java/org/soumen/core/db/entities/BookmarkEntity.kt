package org.soumen.core.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark",
    foreignKeys = [
        ForeignKey(
            entity = WatchlistEntity::class,
            parentColumns = ["watchlistId"],
            childColumns = ["watchlistId"],
            onDelete = ForeignKey.CASCADE // If watchlist is deleted, its bookmarks are deleted too
        )
    ],
    indices = [Index("watchlistId")] // improves lookup performance
)
data class BookmarkEntity(
    @PrimaryKey
    val ticker : String,
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    val volume: String,
    val watchlistId : Long,
)
