package com.anpe.coolbbsyou.net.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAction(
    @Json(name = "buy")
    val buy: Int = 0,
    @Json(name = "follow")
    val follow: Int = 0,
    @Json(name = "like")
    val like: Int = 0,
    @Json(name = "rating")
    val rating: Int = 0,
    @Json(name = "rating_feed_url")
    val ratingFeedUrl: String = "",
    @Json(name = "wish")
    val wish: Int = 0
)