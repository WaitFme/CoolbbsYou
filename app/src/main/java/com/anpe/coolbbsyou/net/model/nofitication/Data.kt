package com.anpe.coolbbsyou.net.model.nofitication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name ="dateline")
    val dateline: Int = 0,
    @Json(name ="entityId")
    val entityId: Int = 0,
    @Json(name ="entityType")
    val entityType: String = "",
    @Json(name ="from_type")
    val fromType: Int = 0,
    @Json(name ="fromUserAvatar")
    val fromUserAvatar: String = "",
    @Json(name ="fromUserInfo")
    val fromUserInfo: FromUserInfo = FromUserInfo(),
    @Json(name ="fromuid")
    val fromuid: Int = 0,
    @Json(name ="fromusername")
    val fromusername: String = "",
    @Json(name ="id")
    val id: Int = 0,
    @Json(name ="isnew")
    val isnew: Int = 0,
    @Json(name ="list_group")
    val listGroup: Int = 0,
    @Json(name ="note")
    val note: String = "",
    @Json(name ="notifyCount")
    val notifyCount: NotifyCount = NotifyCount(),
    @Json(name ="slug")
    val slug: String = "",
    @Json(name ="type")
    val type: String = "",
    @Json(name ="uid")
    val uid: Int = 0,
    @Json(name ="url")
    val url: String = ""
)