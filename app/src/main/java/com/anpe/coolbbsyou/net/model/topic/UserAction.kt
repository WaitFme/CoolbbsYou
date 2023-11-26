package com.anpe.coolbbsyou.net.model.topic

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAction(
    @Json(name = "follow")
    val follow: Int = 0,
    @Json(name = "rating")
    val rating: Int = 0
)