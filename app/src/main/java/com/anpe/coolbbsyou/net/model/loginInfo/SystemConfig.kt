package com.anpe.coolbbsyou.net.model.loginInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SystemConfig(
    @Json(name = "system_config")
    val systemConfig: String = ""
)