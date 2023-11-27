package com.anpe.coolbbsyou.net.model.today

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Entity(
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "pic")
    val pic: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = ""
)