package com.anpe.coolbbsyou.data.remote.domain.loginState
import com.google.gson.annotations.SerializedName


data class LoginStateModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val error: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageStatus")
    val messageStatus: Int,
)

data class Data(
    @SerializedName("adminType")
    val adminType: Int,
    @SerializedName("notifyCount")
    val notifyCount: NotifyCount,
    @SerializedName("pushId")
    val pushId: String,
    @SerializedName("subAdmin")
    val subAdmin: Int,
    @SerializedName("systemConfig")
    val systemConfig: SystemConfig,
    @SerializedName("token")
    val token: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("username")
    val username: String
)

data class NotifyCount(
    @SerializedName("atcommentme")
    val atcommentme: Int,
    @SerializedName("atme")
    val atme: Int,
    @SerializedName("badge")
    val badge: Int,
    @SerializedName("cloudInstall")
    val cloudInstall: Int,
    @SerializedName("commentme")
    val commentme: Int,
    @SerializedName("contacts_follow")
    val contactsFollow: Int,
    @SerializedName("dateline")
    val dateline: Int,
    @SerializedName("feedlike")
    val feedlike: Int,
    @SerializedName("message")
    val message: Int,
    @SerializedName("notification")
    val notification: Int
)

data class SystemConfig(
    @SerializedName("system_config")
    val systemConfig: String
)