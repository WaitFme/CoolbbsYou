package com.anpe.coolbbsyou.data.remote.domain.product


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecentFollow(
    @Json(name = "entityId")
    val entityId: String = "",
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "extraData")
    val extraData: String = "",
    @Json(name = "uid")
    val uid: String = "",
    @Json(name = "userAvatar")
    val userAvatar: String = "",
    @Json(name = "userInfo")
    val userInfo: UserInfo = UserInfo()
)