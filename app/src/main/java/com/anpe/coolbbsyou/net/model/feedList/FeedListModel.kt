package com.anpe.coolbbsyou.net.model.feedList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedListModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf()
)