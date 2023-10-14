package com.anpe.coolbbsyou.data.remote.domain.product


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendRow(
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "entityTypeName")
    val entityTypeName: String = "",
    @Json(name = "logo")
    val logo: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = ""
)