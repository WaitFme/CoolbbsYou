package com.anpe.coolbbsyou.net.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAction(
    @Json(name = "collect")
    val collect: Int = 0,
    @Json(name = "favorite")
    val favorite: Int = 0,
    @Json(name = "follow")
    val follow: Int = 0,
    @Json(name = "followAuthor")
    val followAuthor: Int = 0,
    @Json(name = "like")
    val like: Int = 0,
)
