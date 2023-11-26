package com.anpe.coolbbsyou.net.model.detail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailModel(
    @Json(name = "data")
    val `data`: Data = Data()
)