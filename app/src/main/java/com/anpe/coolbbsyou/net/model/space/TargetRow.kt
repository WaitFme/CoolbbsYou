package com.anpe.coolbbsyou.net.model.space

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TargetRow(
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "isFollow")
    val isFollow: Int = 0,
    @Json(name = "logo")
    val logo: String = "",
    @Json(name = "star_average_score")
    val starAverageScore: Double = 0.0,
    @Json(name = "star_total_count")
    val starTotalCount: Int = 0,
    @Json(name = "subTitle")
    val subTitle: String = "",
    @Json(name = "targetType")
    val targetType: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = ""
)