package com.anpe.coolbbsyou.net.model.createFeed

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateFeedModel(
    @Json(name = "data")
    val `data`: Data = Data(),
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "error")
    val error: String = "",
    @Json(name = "message")
    val message: String = "",
    @Json(name = "forwardUrl")
    val forwardUrl: String = "",
    @Json(name = "messageExtra")
    val messageExtra: String = "",
)
