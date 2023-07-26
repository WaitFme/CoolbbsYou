package com.anpe.coolbbsyou.network.data.model.profile
import com.google.gson.annotations.SerializedName


data class ProfileEntity(
    @SerializedName("data")
    val `data`: Data
)

data class Data(
    @SerializedName("admintype")
    val admintype: Int,
    @SerializedName("albumFavNum")
    val albumFavNum: Int,
    @SerializedName("albumNum")
    val albumNum: Int,
    @SerializedName("apkCommentNum")
    val apkCommentNum: Int,
    @SerializedName("apkDevNum")
    val apkDevNum: Int,
    @SerializedName("apkFollowNum")
    val apkFollowNum: Int,
    @SerializedName("apkRatingNum")
    val apkRatingNum: Int,
    @SerializedName("astro")
    val astro: String,
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: Int,
    @SerializedName("avatar_plugin_status")
    val avatarPluginStatus: Int,
    @SerializedName("avatar_plugin_url")
    val avatarPluginUrl: String,
    @SerializedName("avatarstatus")
    val avatarstatus: Int,
    @SerializedName("be_like_num")
    val beLikeNum: Int,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("birthday")
    val birthday: Int,
    @SerializedName("birthmonth")
    val birthmonth: Int,
    @SerializedName("birthyear")
    val birthyear: Int,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("discoveryNum")
    val discoveryNum: Int,
    @SerializedName("displayUsername")
    val displayUsername: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("emailstatus")
    val emailstatus: Int,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("experience")
    val experience: Int,
    @SerializedName("fans")
    val fans: Int,
    @SerializedName("feed")
    val feed: Int,
    @SerializedName("feed_plugin_open_url")
    val feedPluginOpenUrl: String,
    @SerializedName("feed_plugin_url")
    val feedPluginUrl: String,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("follow")
    val follow: Int,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("goodsNum")
    val goodsNum: Int,
    @SerializedName("groupid")
    val groupid: Int,
    @SerializedName("isBeBlackList")
    val isBeBlackList: Int,
    @SerializedName("isBlackList")
    val isBlackList: Int,
    @SerializedName("isDeveloper")
    val isDeveloper: Int,
    @SerializedName("isFans")
    val isFans: Int,
    @SerializedName("isFollow")
    val isFollow: Int,
    @SerializedName("isIgnoreList")
    val isIgnoreList: Int,
    @SerializedName("isLimitList")
    val isLimitList: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("level_detail_url")
    val levelDetailUrl: String,
    @SerializedName("level_today_message")
    val levelTodayMessage: String,
    @SerializedName("logintime")
    val logintime: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("mobilestatus")
    val mobilestatus: Int,
    @SerializedName("next_level_experience")
    val nextLevelExperience: Int,
    @SerializedName("next_level_percentage")
    val nextLevelPercentage: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("regdate")
    val regdate: Int,
    @SerializedName("replyNum")
    val replyNum: Int,
    @SerializedName("selectedTab")
    val selectedTab: String,
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
    val verifyTitle: String,
    @SerializedName("weibo")
    val weibo: String
)