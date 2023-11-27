package com.anpe.coolbbsyou.net.model.nofitication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotifyCount(
    @Json(name ="atcommentme")
    val atcommentme: Int = 0,
    @Json(name ="atme")
    val atme: Int = 0,
    @Json(name ="badge")
    val badge: Int = 0,
    @Json(name ="cloudInstall")
    val cloudInstall: Int = 0,
    @Json(name ="commentme")
    val commentme: Int = 0,
    @Json(name ="contacts_follow")
    val contactsFollow: Int = 0,
    @Json(name ="dateline")
    val dateline: Int = 0,
    @Json(name ="feedlike")
    val feedlike: Int = 0,
    @Json(name ="message")
    val message: Int = 0,
    @Json(name ="notification")
    val notification: Int = 0
)