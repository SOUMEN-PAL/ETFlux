package org.soumen.core.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "monthly_stock",
    indices = [Index(value = ["symbol", "date"], unique = true)]
)
data class MonthlyStockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val symbol: String,       // from MetaData
    val date: String,         // key from the map
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long
)