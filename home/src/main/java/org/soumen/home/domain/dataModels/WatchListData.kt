package org.soumen.home.domain.dataModels

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class WatchListData(
    val watchlistId : Long,
    val watchlistName : String
)
