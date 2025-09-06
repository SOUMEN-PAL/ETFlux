package org.soumen.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TickerInfoEntity")
data class TickerInfoEntity(
    val AssetType: String,
    val CIK: String,
    val Country: String,
    val Currency: String,
    val Description: String,
    val Exchange: String,
    val Name: String,
    @PrimaryKey
    val Symbol: String
)
