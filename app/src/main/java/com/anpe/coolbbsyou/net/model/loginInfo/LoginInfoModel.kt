package com.anpe.coolbbsyou.net.model.loginInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginInfoModel(
    @Json(name = "data")
    val `data`: Data = Data(),
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "error")
    val error: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "messageStatus")
    val messageStatus: Int = 0,
)