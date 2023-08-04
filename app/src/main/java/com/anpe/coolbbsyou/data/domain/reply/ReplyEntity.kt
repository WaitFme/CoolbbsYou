package com.anpe.coolbbsyou.data.domain.reply

import com.google.gson.annotations.SerializedName

data class ReplyEntity(
    @SerializedName("data")
    val `data`: List<Data>
)

data class Data(
    @SerializedName("avatarFetchType")
    val avatarFetchType: String,
    @SerializedName("block_status")
    val blockStatus: Int,
    @SerializedName("burynum")
    val burynum: Int,
    @SerializedName("dateline")
    val dateline: Int,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityTemplate")
    val entityTemplate: String,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("extra_fromApi")
    val extraFromApi: String,
    @SerializedName("feedUid")
    val feedUid: Int,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("fid")
    val fid: Int,
    @SerializedName("ftype")
    val ftype: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("infoHtml")
    val infoHtml: String,
    @SerializedName("isFeedAuthor")
    val isFeedAuthor: Int,
    @SerializedName("is_folded")
    val isFolded: Int,
    @SerializedName("lastupdate")
    val lastupdate: Int,
    @SerializedName("likenum")
    val likenum: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("message_sp")
    val messageSp: String,
    @SerializedName("message_status")
    val messageStatus: Int,
    @SerializedName("pic")
    val pic: String,
    @SerializedName("picArr")
    val picArr: List<String>,
    @SerializedName("rank_score")
    val rankScore: Int,
    @SerializedName("recent_reply_ids")
    val recentReplyIds: String,
    @SerializedName("replynum")
    val replynum: Int,
    @SerializedName("reportnum")
    val reportnum: Int,
    @SerializedName("rid")
    val rid: Int,
    @SerializedName("rrid")
    val rrid: Int,
    @SerializedName("ruid")
    val ruid: Int,
    @SerializedName("rusername")
    val rusername: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("userAction")
    val userAction: UserAction,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("userInfo")
    val userInfo: UserInfo,
    @SerializedName("username")
    val username: String
)

data class UserAction(
    @SerializedName("like")
    val like: Int
)

data class UserInfo(
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