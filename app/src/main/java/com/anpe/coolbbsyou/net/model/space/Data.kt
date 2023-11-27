package com.anpe.coolbbsyou.net.model.space

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "admintype")
    val admintype: Int = 0,
    @Json(name = "albumFavNum")
    val albumFavNum: Int = 0,
    @Json(name = "albumNum")
    val albumNum: Int = 0,
    @Json(name = "apkCommentNum")
    val apkCommentNum: Int = 0,
    @Json(name = "apkDevNum")
    val apkDevNum: Int = 0,
    @Json(name = "apkFollowNum")
    val apkFollowNum: Int = 0,
    @Json(name = "apkRatingNum")
    val apkRatingNum: Int = 0,
    @Json(name = "astro")
    val astro: String = "",
    @Json(name = "avatar_cover_status")
    val avatarCoverStatus: Int = 0,
    @Json(name = "avatar_plugin_status")
    val avatarPluginStatus: Int = 0,
    @Json(name = "avatar_plugin_url")
    val avatarPluginUrl: String = "",
    @Json(name = "avatarstatus")
    val avatarstatus: Int = 0,
    @Json(name = "be_like_num")
    val beLikeNum: Int = 0,
    @Json(name = "bio")
    val bio: String = "",
    @Json(name = "block_status")
    val blockStatus: Int = 0,
    @Json(name = "blog")
    val blog: String = "",
    @Json(name = "city")
    val city: String = "",
    @Json(name = "cover")
    val cover: String = "",
    @Json(name = "discoveryNum")
    val discoveryNum: Int = 0,
    @Json(name = "displayUsername")
    val displayUsername: String = "",
    @Json(name = "entityId")
    val entityId: Int = 0,
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "experience")
    val experience: Int = 0,
    @Json(name = "fans")
    val fans: Int = 0,
    @Json(name = "feed")
    val feed: Int = 0,
    @Json(name = "feed_plugin_open_url")
    val feedPluginOpenUrl: String = "",
    @Json(name = "feed_plugin_url")
    val feedPluginUrl: String = "",
    @Json(name = "fetchType")
    val fetchType: String = "",
    @Json(name = "follow")
    val follow: Int = 0,
    @Json(name = "gender")
    val gender: Int = 0,
    @Json(name = "goods_count")
    val goodsCount: Int = 0,
    @Json(name = "goodsNum")
    val goodsNum: Int = 0,
    @Json(name = "goods_store_status")
    val goodsStoreStatus: Int = 0,
    @Json(name = "groupid")
    val groupid: Int = 0,
    @Json(name = "homeTabCardRows")
    val homeTabCardRows: List<HomeTabCardRow> = listOf(),
    @Json(name = "isBeBlackList")
    val isBeBlackList: Int = 0,
    @Json(name = "isBlackList")
    val isBlackList: Int = 0,
    @Json(name = "isDeveloper")
    val isDeveloper: Int = 0,
    @Json(name = "isFans")
    val isFans: Int = 0,
    @Json(name = "isFollow")
    val isFollow: Int = 0,
    @Json(name = "isIgnoreList")
    val isIgnoreList: Int = 0,
    @Json(name = "isLimitList")
    val isLimitList: Int = 0,
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
    @Json(name = "province")
    val province: String = "",
    @Json(name = "regdate")
    val regdate: Int = 0,
    @Json(name = "replyNum")
    val replyNum: Int = 0,
    @Json(name = "selectedTab")
    val selectedTab: String = "",
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "top_ids")
    val topIds: List<Any> = listOf(),
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
    val verifyTitle: String = "",
    @Json(name = "weibo")
    val weibo: String = ""
)