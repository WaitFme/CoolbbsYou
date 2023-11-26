package com.anpe.coolbbsyou.net.model.topic

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicModel(
    @Json(name = "data")
    val `data`: Data = Data()
)