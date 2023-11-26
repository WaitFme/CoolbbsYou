package com.anpe.coolbbsyou.net.model.suggest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuggestSearchModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf()
)