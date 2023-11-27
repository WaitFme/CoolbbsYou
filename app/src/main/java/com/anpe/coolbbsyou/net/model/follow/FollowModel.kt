package com.anpe.coolbbsyou.net.model.follow

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FollowModel(
    @Json(name = "data")
    val `data`: Int = 0
)