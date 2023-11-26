package com.anpe.coolbbsyou.net.model.nofitication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationModel(
    @Json(name ="data")
    val `data`: List<Data> = listOf()
)