package com.anpe.coolbbsyou.net.model.index


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExtraDataArr(
    @Json(name = "cardDividerBottom")
    val cardDividerBottom: Int = 0,
    @Json(name = "cardDividerBottomVX")
    val cardDividerBottomVX: String = "",
    @Json(name = "cardDividerTopVX")
    val cardDividerTopVX: String = "",
    @Json(name = "cardId")
    val cardId: Int = 0,
    @Json(name = "cardPageName")
    val cardPageName: String? = "",
    @Json(name = "column")
    val column: String = "",
    @Json(name = "Due.To")
    val dueTo: String = "",
    @Json(name = "Good.Name")
    val goodName: String = "",
    @Json(name = "info")
    val info: String = "",
    @Json(name = "pagination")
    val pagination: String = "",
    @Json(name = "recommendPos")
    val recommendPos: String = "",
    @Json(name = "row")
    val row: String = "",
    @Json(name = "viewBackgroundStyle")
    val viewBackgroundStyle: String = ""
)