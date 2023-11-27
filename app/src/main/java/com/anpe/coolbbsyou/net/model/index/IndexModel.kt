package com.anpe.coolbbsyou.net.model.index


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IndexModel(
    @Json(name = "data")
    val `data`: List<Data> = listOf(),
    @Json(name = "error")
    val error: Any = Any(),
    @Json(name = "message")
    val message: String = "",
    @Json(name = "status")
    val status: Int = 0
)