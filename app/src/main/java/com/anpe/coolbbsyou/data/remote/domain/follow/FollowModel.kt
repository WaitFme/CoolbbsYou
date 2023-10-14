package com.anpe.coolbbsyou.data.remote.domain.follow
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FollowModel(
    @Json(name = "data")
    val `data`: Int = 0
)