package com.anpe.coolbbsyou.net.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendContent(
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