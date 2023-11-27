package com.anpe.coolbbsyou.net.model.topic

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tab(
    @Json(name = "entityId")
    val entityId: Int = 0,
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "page_name")
    val pageName: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = ""
)