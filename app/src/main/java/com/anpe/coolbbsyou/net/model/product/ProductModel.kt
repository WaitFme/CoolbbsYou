package com.anpe.coolbbsyou.net.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductModel(
    @Json(name = "data")
    val `data`: Data = Data()
)