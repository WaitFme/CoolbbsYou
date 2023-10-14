package com.anpe.coolbbsyou.data.remote.domain.space


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("admintype")
    val admintype: Int = 0,
    @SerializedName("albumFavNum")
    val albumFavNum: Int = 0,
    @SerializedName("albumNum")
    val albumNum: Int = 0,
    @SerializedName("apkCommentNum")
    val apkCommentNum: Int = 0,
    @SerializedName("apkDevNum")
    val apkDevNum: Int = 0,
    @SerializedName("apkFollowNum")
    val apkFollowNum: Int = 0,
    @SerializedName("apkRatingNum")
    val apkRatingNum: Int = 0,
    @SerializedName("astro")
    val astro: String = "",
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: Int = 0,
    @SerializedName("avatar_plugin_status")
    val avatarPluginStatus: Int = 0,
    @SerializedName("avatar_plugin_url")
    val avatarPluginUrl: String = "",
    @SerializedName("avatarstatus")
    val avatarstatus: Int = 0,
    @SerializedName("be_like_num")
    val beLikeNum: Int = 0,
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("block_status")
    val blockStatus: Int = 0,
    @SerializedName("blog")
    val blog: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("discoveryNum")
    val discoveryNum: Int = 0,
    @SerializedName("displayUsername")
    val displayUsername: String = "",
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("experience")
    val experience: Int = 0,
    @SerializedName("fans")
    val fans: Int = 0,
    @SerializedName("feed")
    val feed: Int = 0,
    @SerializedName("feed_plugin_open_url")
    val feedPluginOpenUrl: String = "",
    @SerializedName("feed_plugin_url")
    val feedPluginUrl: String = "",
    @SerializedName("fetchType")
    val fetchType: String = "",
    @SerializedName("follow")
    val follow: Int = 0,
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("goods_count")
    val goodsCount: Int = 0,
    @SerializedName("goodsNum")
    val goodsNum: Int = 0,
    @SerializedName("goods_store_status")
    val goodsStoreStatus: Int = 0,
    @SerializedName("groupid")
    val groupid: Int = 0,
    @SerializedName("homeTabCardRows")
    val homeTabCardRows: List<HomeTabCardRow> = listOf(),
    @SerializedName("isBeBlackList")
    val isBeBlackList: Int = 0,
    @SerializedName("isBlackList")
    val isBlackList: Int = 0,
    @SerializedName("isDeveloper")
    val isDeveloper: Int = 0,
    @SerializedName("isFans")
    val isFans: Int = 0,
    @SerializedName("isFollow")
    val isFollow: Int = 0,
    @SerializedName("isIgnoreList")
    val isIgnoreList: Int = 0,
    @SerializedName("isLimitList")
    val isLimitList: Int = 0,
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
    @SerializedName("province")
    val province: String = "",
    @SerializedName("regdate")
    val regdate: Int = 0,
    @SerializedName("replyNum")
    val replyNum: Int = 0,
    @SerializedName("selectedTab")
    val selectedTab: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("top_ids")
    val topIds: List<Any> = listOf(),
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
    val verifyTitle: String = "",
    @SerializedName("weibo")
    val weibo: String = ""
)