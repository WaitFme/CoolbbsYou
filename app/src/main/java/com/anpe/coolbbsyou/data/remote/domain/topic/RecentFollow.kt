package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class RecentFollow(
    @SerializedName("entityId")
    val entityId: String = "",
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("extraData")
    val extraData: String = "",
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("userAvatar")
    val userAvatar: String = "",
    @SerializedName("userInfo")
    val userInfo: UserInfo = UserInfo()
)