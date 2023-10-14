package com.anpe.coolbbsyou.data.remote.domain.like

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LikeModel(
    @Json(name = "data")
    val data: Int = -1,
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "error")
    val error: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "messageStatus")
    val messageStatus: Int = 0
)