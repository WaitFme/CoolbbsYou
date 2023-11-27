package com.anpe.coolbbsyou.net.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf()
)