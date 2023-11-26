package com.anpe.coolbbsyou.net.model.reply

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReplyModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf()
)