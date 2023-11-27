package com.anpe.coolbbsyou.net.model.loginInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "adminType")
    val adminType: Int = 0,
    @Json(name = "notifyCount")
    val notifyCount: NotifyCount = NotifyCount(),
    @Json(name = "pushId")
    val pushId: String = "",
    @Json(name = "subAdmin")
    val subAdmin: Int = 0,
    @Json(name = "systemConfig")
    val systemConfig: SystemConfig = SystemConfig(),
    @Json(name = "token")
    val token: String = "",
    @Json(name = "uid")
    val uid: String = "",
    @Json(name = "userAvatar")
    val userAvatar: String = "",
    @Json(name = "username")
    val username: String = ""
)
