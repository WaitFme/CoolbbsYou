package com.anpe.coolbbsyou.net.model.today

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExtraDataArr(
    @Json(name = "cardDividerBottom")
    val cardDividerBottom: Int = 0,
    @Json(name = "cardId")
    val cardId: Int = 0,
    @Json(name = "cardPageName")
    val cardPageName: String = "",
    @Json(name = "pageTitle")
    val pageTitle: String = ""
)