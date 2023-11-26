package com.anpe.coolbbsyou.net.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Session(
    @Json(name = "isAdministrator")
    val isAdministrator: Boolean = false,
    @Json(name = "isLogin")
    val isLogin: Boolean = false,
    @Json(name = "isMember")
    val isMember: Boolean = false,
    @Json(name = "isSubAdmin")
    val isSubAdmin: Int = 0,
    @Json(name = "isSuperAdministrator")
    val isSuperAdministrator: Boolean = false,
    @Json(name = "isTester")
    val isTester: Boolean = false,
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "uid")
    val uid: Int = 0,
    @Json(name = "username")
    val username: Any = Any()
)