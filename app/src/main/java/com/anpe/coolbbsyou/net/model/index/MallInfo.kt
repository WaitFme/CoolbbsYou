package com.anpe.coolbbsyou.net.model.index


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MallInfo(
    @Json(name = "goods_count")
    val goodsCount: Int = 0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "mall_domain")
    val mallDomain: String = "",
    @Json(name = "mall_logo")
    val mallLogo: String = "",
    @Json(name = "mall_title")
    val mallTitle: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "sort")
    val sort: Int = 0
)