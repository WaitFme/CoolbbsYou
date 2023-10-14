package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("admintype")
    val admintype: Int = 0,
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: Int = 0,
    @SerializedName("avatar_plugin_status")
    val avatarPluginStatus: Int = 0,
    @SerializedName("avatar_plugin_url")
    val avatarPluginUrl: String = "",
    @SerializedName("avatarstatus")
    val avatarstatus: Int = 0,
    @SerializedName("block_status")
    val blockStatus: Int = 0,
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("displayUsername")
    val displayUsername: String = "",
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("experience")
    val experience: Int = 0,
    @SerializedName("feed_plugin_open_url")
    val feedPluginOpenUrl: String = "",
    @SerializedName("feed_plugin_url")
    val feedPluginUrl: String = "",
    @SerializedName("fetchType")
    val fetchType: String = "",
    @SerializedName("groupid")
    val groupid: Int = 0,
    @SerializedName("isDeveloper")
    val isDeveloper: Int = 0,
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("level_detail_url")
    val levelDetailUrl: String = "",
    @SerializedName("level_today_message")
    val levelTodayMessage: String = "",
    @SerializedName("logintime")
    val logintime: Int = 0,
    @SerializedName("next_level_experience")
    val nextLevelExperience: Int = 0,
    @SerializedName("next_level_percentage")
    val nextLevelPercentage: String = "",
    @SerializedName("regdate")
    val regdate: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("userAvatar")
    val userAvatar: String = "",
    @SerializedName("userBigAvatar")
    val userBigAvatar: String = "",
    @SerializedName("userSmallAvatar")
    val userSmallAvatar: String = "",
    @SerializedName("user_type")
    val userType: Int = 0,
    @SerializedName("usergroupid")
    val usergroupid: Int = 0,
    @SerializedName("username")
    val username: String = "",
    @SerializedName("usernamestatus")
    val usernamestatus: Int = 0,
    @SerializedName("verify_icon")
    val verifyIcon: String = "",
    @SerializedName("verify_label")
    val verifyLabel: String = "",
    @SerializedName("verify_show_type")
    val verifyShowType: Int = 0,
    @SerializedName("verify_status")
    val verifyStatus: Int = 0,
    @SerializedName("verify_title")
    val verifyTitle: String = ""
)