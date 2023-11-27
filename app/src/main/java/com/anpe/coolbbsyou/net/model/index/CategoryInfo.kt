package com.anpe.coolbbsyou.net.model.index


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryInfo(
    @Json(name = "cat_id")
    val catId: Int = 0,
    @Json(name = "cat_tags")
    val catTags: String = "",
    @Json(name = "cat_title")
    val catTitle: String = "",
    @Json(name = "goods_count")
    val goodsCount: Int = 0
)