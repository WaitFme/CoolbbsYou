package com.anpe.coolbbsyou.net.model.space

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpaceModel(
    @Json(name = "data")
    val `data`: Data = Data()
)