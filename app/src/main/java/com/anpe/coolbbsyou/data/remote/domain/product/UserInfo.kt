package com.anpe.coolbbsyou.data.remote.domain.product


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "admintype")
    val admintype: Int = 0,
    @Json(name = "avatar_cover_status")
    val avatarCoverStatus: Int = 0,
    @Json(name = "avatar_plugin_status")
    val avatarPluginStatus: Int = 0,
    @Json(name = "avatar_plugin_url")
    val avatarPluginUrl: String = "",
    @Json(name = "avatarstatus")
    val avatarstatus: Int = 0,
    @Json(name = "block_status")
    val blockStatus: Int = 0,
    @Json(name = "cover")
    val cover: String = "",
    @Json(name = "displayUsername")
    val displayUsername: String = "",
    @Json(name = "entityId")
    val entityId: Int = 0,
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "experience")
    val experience: Int = 0,
    @Json(name = "feed_plugin_open_url")
    val feedPluginOpenUrl: String = "",
    @Json(name = "feed_plugin_url")
    val feedPluginUrl: String = "",
    @Json(name = "fetchType")
    val fetchType: String = "",
    @Json(name = "groupid")
    val groupid: Int = 0,
    @Json(name = "isDeveloper")
    val isDeveloper: Int = 0,
    @Json(name = "level")
    val level: Int = 0,
    @Json(name = "level_detail_url")
    val levelDetailUrl: String = "",
    @Json(name = "level_today_message")
    val levelTodayMessage: String = "",
    @Json(name = "logintime")
    val logintime: Int = 0,
    @Json(name = "next_level_experience")
    val nextLevelExperience: Int = 0,
    @Json(name = "next_level_percentage")
    val nextLevelPercentage: String = "",
    @Json(name = "regdate")
    val regdate: Int = 0,
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "uid")
    val uid: Int = 0,
    @Json(name = "url")
    val url: String = "",
    @Json(name = "userAvatar")
    val userAvatar: String = "",
    @Json(name = "userBigAvatar")
    val userBigAvatar: String = "",
    @Json(name = "userSmallAvatar")
    val userSmallAvatar: String = "",
    @Json(name = "user_type")
    val userType: Int = 0,
    @Json(name = "usergroupid")
    val usergroupid: Int = 0,
    @Json(name = "username")
    val username: String = "",
    @Json(name = "usernamestatus")
    val usernamestatus: Int = 0,
    @Json(name = "verify_icon")
    val verifyIcon: String = "",
    @Json(name = "verify_label")
    val verifyLabel: String = "",
    @Json(name = "verify_show_type")
    val verifyShowType: Int = 0,
    @Json(name = "verify_status")
    val verifyStatus: Int = 0,
    @Json(name = "verify_title")
    val verifyTitle: String = ""
)