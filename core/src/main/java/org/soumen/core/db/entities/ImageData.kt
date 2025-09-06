package org.soumen.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_data")
data class ImageDataEntity(
    val image: String,
    val name: String,
    @PrimaryKey
    val ticker: String
)