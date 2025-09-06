package org.soumen.shared.data.imageResponseModel

import kotlinx.serialization.Serializable

@Serializable
data class ImageDataResponseItem(
    val image: String,
    val name: String,
    val ticker: String
)