package com.anpe.coolbbsyou.net.model.today

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodayCoolModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf()
)