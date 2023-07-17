package com.anpe.coolbbsyou.network.data.model.details
import com.google.gson.annotations.SerializedName


data class DetailsEntity(
    @SerializedName("data")
    val `data`: Data
)

data class Data(
    @SerializedName("at_count")
    val atCount: Int,
    @SerializedName("avatarFetchType")
    val avatarFetchType: String,
    @SerializedName("block_status")
    val blockStatus: Int,
    @SerializedName("burynum")
    val burynum: Int,
    @SerializedName("canDisallowReply")
    val canDisallowReply: Int,
    @SerializedName("change_count")
    val changeCount: Int,
    @SerializedName("comment_block_num")
    val commentBlockNum: Int,
    @SerializedName("commentnum")
    val commentnum: Int,
    @SerializedName("create_time")
    val createTime: Int,
    @SerializedName("dateline")
    val dateline: Int,
    @SerializedName("device_build")
    val deviceBuild: String,
    @SerializedName("device_name")
    val deviceName: String,
    @SerializedName("device_rom")
    val deviceRom: String,
    @SerializedName("device_title")
    val deviceTitle: String,
    @SerializedName("device_title_url")
    val deviceTitleUrl: String,
    @SerializedName("disallow_reply")
    val disallowReply: Int,
    @SerializedName("disallow_repost")
    val disallowRepost: Int,
    @SerializedName("dyh_id")
    val dyhId: Int,
    @SerializedName("dyh_name")
    val dyhName: String,
    @SerializedName("editor_title")
    val editorTitle: String,
    @SerializedName("enableModify")
    val enableModify: Int,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityTemplate")
    val entityTemplate: String,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("extra_fromApi")
    val extraFromApi: String,
    @SerializedName("extra_info")
    val extraInfo: String,
    @SerializedName("extra_key")
    val extraKey: String,
    @SerializedName("extra_pic")
    val extraPic: String,
    @SerializedName("extra_status")
    val extraStatus: Int,
    @SerializedName("extra_title")
    val extraTitle: String,
    @SerializedName("extra_type")
    val extraType: Int,
    @SerializedName("extra_url")
    val extraUrl: String,
    @SerializedName("favnum")
    val favnum: Int,
    @SerializedName("feed_score")
    val feedScore: Int,
    @SerializedName("feedType")
    val feedType: String,
    @SerializedName("feedTypeName")
    val feedTypeName: String,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("fid")
    val fid: Int,
    @SerializedName("forwardSourceType")
    val forwardSourceType: String,
    @SerializedName("forwardid")
    val forwardid: String,
    @SerializedName("forwardnum")
    val forwardnum: Int,
    @SerializedName("fromid")
    val fromid: Int,
    @SerializedName("fromname")
    val fromname: String,
    @SerializedName("hitnum")
    val hitnum: Int,
    @SerializedName("hotReplyRows")
    val hotReplyRows: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("infoHtml")
    val infoHtml: String,
    @SerializedName("ip_location")
    val ipLocation: String,
    @SerializedName("is_anonymous")
    val isAnonymous: Int,
    @SerializedName("is_headline")
    val isHeadline: Int,
    @SerializedName("is_hidden")
    val isHidden: Int,
    @SerializedName("is_html_article")
    val isHtmlArticle: Int,
    @SerializedName("is_ks_doc")
    val isKsDoc: Int,
    @SerializedName("isModified")
    val isModified: Int,
    @SerializedName("is_pre_recommended")
    val isPreRecommended: Int,
    @SerializedName("is_white_feed")
    val isWhiteFeed: Int,
    @SerializedName("issummary")
    val issummary: Int,
    @SerializedName("istag")
    val istag: Int,
    @SerializedName("label")
    val label: String,
    @SerializedName("last_change_time")
    val lastChangeTime: Int,
    @SerializedName("lastupdate")
    val lastupdate: Int,
    @SerializedName("likenum")
    val likenum: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("long_location")
    val longLocation: String,
    @SerializedName("media_info")
    val mediaInfo: String,
    @SerializedName("media_pic")
    val mediaPic: String,
    @SerializedName("media_type")
    val mediaType: Int,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("message_brief")
    val messageBrief: String,
    @SerializedName("message_cover")
    val messageCover: String,
    @SerializedName("message_keywords")
    val messageKeywords: String,
    @SerializedName("message_length")
    val messageLength: Int,
    @SerializedName("message_raw_output")
    val messageRawOutput: String,
    @SerializedName("message_signature")
    val messageSignature: String,
    @SerializedName("message_status")
    val messageStatus: Int,
    @SerializedName("message_title")
    val messageTitle: String,
    @SerializedName("message_title_md5")
    val messageTitleMd5: String,
    @SerializedName("pic")
    val pic: String,
    @SerializedName("picArr")
    val picArr: List<String>,
    @SerializedName("post_signature")
    val postSignature: String,
    @SerializedName("publish_status")
    val publishStatus: Int,
    @SerializedName("question_answer_num")
    val questionAnswerNum: Int,
    @SerializedName("question_follow_num")
    val questionFollowNum: Int,
    @SerializedName("rank_score")
    val rankScore: Int,
    @SerializedName("recent_hot_reply_ids")
    val recentHotReplyIds: String,
    @SerializedName("recent_like_list")
    val recentLikeList: String,
    @SerializedName("recent_reply_ids")
    val recentReplyIds: String,
    @SerializedName("recommend")
    val recommend: Int,
    @SerializedName("related_dyh_ids")
    val relatedDyhIds: String,
    @SerializedName("relateddata")
    val relateddata: List<Any>,
    @SerializedName("relatednum")
    val relatednum: Int,
    @SerializedName("reply_order")
    val replyOrder: String,
    @SerializedName("replyRows")
    val replyRows: List<Any>,
    @SerializedName("replyRowsCount")
    val replyRowsCount: Int,
    @SerializedName("replyRowsMore")
    val replyRowsMore: Int,
    @SerializedName("replynum")
    val replynum: Int,
    @SerializedName("reportnum")
    val reportnum: Int,
    @SerializedName("share_num")
    val shareNum: Int,
    @SerializedName("shareUrl")
    val shareUrl: String,
    @SerializedName("sourceFeed")
    val sourceFeed: Any,
    @SerializedName("source_id")
    val sourceId: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("tag_count")
    val tagCount: Int,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("tcat")
    val tcat: Int,
    @SerializedName("tid")
    val tid: Long,
    @SerializedName("tinfo")
    val tinfo: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tpic")
    val tpic: String,
    @SerializedName("ttitle")
    val ttitle: String,
    @SerializedName("ttype")
    val ttype: Int,
    @SerializedName("turl")
    val turl: String,
    @SerializedName("turlTarget")
    val turlTarget: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("url_count")
    val urlCount: Int,
    @SerializedName("userAction")
    val userAction: UserAction,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("userInfo")
    val userInfo: UserInfo,
    @SerializedName("user_tags")
    val userTags: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("viewnum")
    val viewnum: Int,
    @SerializedName("vote_score")
    val voteScore: Int
)

data class UserAction(
    @SerializedName("collect")
    val collect: Int,
    @SerializedName("favorite")
    val favorite: Int,
    @SerializedName("follow")
    val follow: Int,
    @SerializedName("followAuthor")
    val followAuthor: Int,
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