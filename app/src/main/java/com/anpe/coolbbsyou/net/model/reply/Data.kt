package com.anpe.coolbbsyou.net.model.reply

import com.anpe.coolbbsyou.net.model.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "avatarFetchType")
    val avatarFetchType: String = "",
    @Json(name = "block_status")
    val blockStatus: Int = 0,
    @Json(name = "burynum")
    val burynum: Int = 0,
    @Json(name = "dateline")
    val dateline: Int = 0,
    @Json(name = "entityId")
    val entityId: Int = 0,
    @Json(name = "entityTemplate")
    val entityTemplate: String = "",
    @Json(name = "entityType")
    val entityType: String = "",
    @Json(name = "extra_fromApi")
    val extraFromApi: String = "",
    @Json(name = "feedUid")
    val feedUid: Int = 0,
    @Json(name = "fetchType")
    val fetchType: String = "",
    @Json(name = "fid")
    val fid: Int = 0,
    @Json(name = "ftype")
    val ftype: Int = 0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "infoHtml")
    val infoHtml: String = "",
    @Json(name = "isFeedAuthor")
    val isFeedAuthor: Int = 0,
    @Json(name = "is_folded")
    val isFolded: Int = 0,
    @Json(name = "lastupdate")
    val lastupdate: Int = 0,
    @Json(name = "likenum")
    val likenum: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "message_status")
    val messageStatus: Int = 0,
    @Json(name = "pic")
    val pic: String = "",
    @Json(name = "picArr")
    val picArr: List<String> = listOf(),
    @Json(name = "rank_score")
    val rankScore: Int = 0,
    @Json(name = "recent_reply_ids")
    val recentReplyIds: String = "",
    @Json(name = "replyRows")
    val replyRows: List<ReplyRow> = listOf(),
    @Json(name = "replyRowsCount")
    val replyRowsCount: Int = 0,
    @Json(name = "replyRowsMore")
    val replyRowsMore: Int = 0,
    @Json(name = "replynum")
    val replynum: Int = 0,
    @Json(name = "reportnum")
    val reportnum: Int = 0,
    @Json(name = "rid")
    val rid: Int = 0,
    @Json(name = "rrid")
    val rrid: Int = 0,
    @Json(name = "ruid")
    val ruid: Int = 0,
    @Json(name = "rusername")
    val rusername: String = "",
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "uid")
    val uid: Int = 0,
    @Json(name = "userAction")
    val userAction: UserAction = UserAction(),
    @Json(name = "userAvatar")
    val userAvatar: String = "",
    @Json(name = "userInfo")
    val userInfo: UserInfo = UserInfo(),
    @Json(name = "username")
    val username: String = ""
)