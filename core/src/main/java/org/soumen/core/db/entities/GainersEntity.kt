package org.soumen.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "GainerEntity")
data class GainersEntity(
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    @PrimaryKey
    val ticker: String,
    val volume: String
)

@Entity(tableName = "LosersEntity")
data class LosersEntity(
    val changeAmount: String,
    val changePercentage: String,
    val price: String,
    @PrimaryKey
    val ticker: String,
    val volume: String
)
