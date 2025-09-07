package org.soumen.core.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey(autoGenerate = true)
    val watchlistId : Long = 0,
    @ColumnInfo(name = "watchlist_name")
    val watchlistName : String
)
