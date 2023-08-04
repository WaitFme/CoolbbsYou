package com.anpe.coolbbsyou.data.domain.nofitication
import com.google.gson.annotations.SerializedName


data class NotificationEntity(
    @SerializedName("data")
    val `data`: List<Data>
)

data class Data(
    @SerializedName("dateline")
    val dateline: Int,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("from_type")
    val fromType: Int,
    @SerializedName("fromUserAvatar")
    val fromUserAvatar: String,
    @SerializedName("fromUserInfo")
    val fromUserInfo: FromUserInfo,
    @SerializedName("fromuid")
    val fromuid: Int,
    @SerializedName("fromusername")
    val fromusername: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isnew")
    val isnew: Int,
    @SerializedName("list_group")
    val listGroup: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("notifyCount")
    val notifyCount: NotifyCount,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("url")
    val url: String
)

data class FromUserInfo(
    @SerializedName("admintype")
    val admintype: Int,
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: Int,
    @SerializedName("avatar_plugin_status")
    val avatarPluginStatus: Int,
    @SerializedName("avatar_plugin_url")
    val avatarPluginUrl: String,
    @SerializedName("avatarstatus")
    val avatarstatus: Int,
    @SerializedName("block_status")
    val blockStatus: Int,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("displayUsername")
    val displayUsername: String,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("experience")
    val experience: Int,
    @SerializedName("feed_plugin_open_url")
    val feedPluginOpenUrl: String,
    @SerializedName("feed_plugin_url")
    val feedPluginUrl: String,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("groupid")
    val groupid: Int,
    @SerializedName("isDeveloper")
    val isDeveloper: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("level_detail_url")
    val levelDetailUrl: String,
    @SerializedName("level_today_message")
    val levelTodayMessage: String,
    @SerializedName("logintime")
    val logintime: Int,
    @SerializedName("next_level_experience")
    val nextLevelExperience: Int,
    @SerializedName("next_level_percentage")
    val nextLevelPercentage: String,
    @SerializedName("regdate")
    val regdate: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("userBigAvatar")
    val userBigAvatar: String,
    @SerializedName("userSmallAvatar")
    val userSmallAvatar: String,
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("usergroupid")
    val usergroupid: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("usernamestatus")
    val usernamestatus: Int,
    @SerializedName("verify_icon")
    val verifyIcon: String,
    @SerializedName("verify_label")
    val verifyLabel: String,
    @SerializedName("verify_show_type")
    val verifyShowType: Int,
    @SerializedName("verify_status")
    val verifyStatus: Int,
    @SerializedName("verify_title")
    val verifyTitle: String
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