package com.anpe.coolbbsyou.data.remote.domain.feedList


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("at_count")
    val atCount: Int = 0,
    @SerializedName("avatarFetchType")
    val avatarFetchType: String = "",
    @SerializedName("block_status")
    val blockStatus: Int = 0,
    @SerializedName("burynum")
    val burynum: Int = 0,
    @SerializedName("canDisallowReply")
    val canDisallowReply: Int = 0,
    @SerializedName("change_count")
    val changeCount: Int = 0,
    @SerializedName("comment_block_num")
    val commentBlockNum: Int = 0,
    @SerializedName("commentnum")
    val commentnum: Int = 0,
    @SerializedName("country_for_ershou")
    val countryForErshou: String = "",
    @SerializedName("create_time")
    val createTime: Int = 0,
    @SerializedName("dateline")
    val dateline: Int = 0,
    @SerializedName("device_build")
    val deviceBuild: String = "",
    @SerializedName("device_name")
    val deviceName: String = "",
    @SerializedName("device_rom")
    val deviceRom: String = "",
    @SerializedName("device_title")
    val deviceTitle: String = "",
    @SerializedName("device_title_url")
    val deviceTitleUrl: String = "",
    @SerializedName("disallow_reply")
    val disallowReply: Int = 0,
    @SerializedName("disallow_repost")
    val disallowRepost: Int = 0,
    @SerializedName("dyh_id")
    val dyhId: Int = 0,
    @SerializedName("dyh_name")
    val dyhName: String = "",
    @SerializedName("editor_title")
    val editorTitle: String = "",
    @SerializedName("enableModify")
    val enableModify: Int = 0,
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityTemplate")
    val entityTemplate: String = "",
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("ershou_info")
    val ershouInfo: ErshouInfo = ErshouInfo(),
    @SerializedName("extra_fromApi")
    val extraFromApi: String = "",
    @SerializedName("extra_info")
    val extraInfo: String = "",
    @SerializedName("extra_key")
    val extraKey: String = "",
    @SerializedName("extra_pic")
    val extraPic: String = "",
    @SerializedName("extra_status")
    val extraStatus: Int = 0,
    @SerializedName("extra_title")
    val extraTitle: String = "",
    @SerializedName("extra_type")
    val extraType: Int = 0,
    @SerializedName("extra_url")
    val extraUrl: String = "",
    @SerializedName("favnum")
    val favnum: Int = 0,
    @SerializedName("feed_score")
    val feedScore: Int = 0,
    @SerializedName("feedType")
    val feedType: String = "",
    @SerializedName("feedTypeName")
    val feedTypeName: String = "",
    @SerializedName("fetchType")
    val fetchType: String = "",
    @SerializedName("fid")
    val fid: Int = 0,
    @SerializedName("forwardSourceType")
    val forwardSourceType: String = "",
    @SerializedName("forwardid")
    val forwardid: String = "",
    @SerializedName("forwardnum")
    val forwardnum: Int = 0,
    @SerializedName("fromid")
    val fromid: Int = 0,
    @SerializedName("fromname")
    val fromname: String = "",
    @SerializedName("hitnum")
    val hitnum: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("include_goods_ids")
    val includeGoodsIds: List<String> = listOf(),
    @SerializedName("info")
    val info: String = "",
    @SerializedName("infoHtml")
    val infoHtml: String = "",
    @SerializedName("is_anonymous")
    val isAnonymous: Int = 0,
    @SerializedName("is_headline")
    val isHeadline: Int = 0,
    @SerializedName("is_hidden")
    val isHidden: Int = 0,
    @SerializedName("is_html_article")
    val isHtmlArticle: Int = 0,
    @SerializedName("is_ks_doc")
    val isKsDoc: Int = 0,
    @SerializedName("isModified")
    val isModified: Int = 0,
    @SerializedName("is_pre_recommended")
    val isPreRecommended: Int = 0,
    @SerializedName("is_white_feed")
    val isWhiteFeed: Int = 0,
    @SerializedName("issummary")
    val issummary: Int = 0,
    @SerializedName("istag")
    val istag: Int = 0,
    @SerializedName("label")
    val label: String = "",
    @SerializedName("last_change_time")
    val lastChangeTime: Int = 0,
    @SerializedName("lastupdate")
    val lastupdate: Int = 0,
    @SerializedName("likenum")
    val likenum: Int = 0,
    @SerializedName("location")
    val location: String = "",
    @SerializedName("long_location")
    val longLocation: String = "",
    @SerializedName("media_info")
    val mediaInfo: String = "",
    @SerializedName("media_pic")
    val mediaPic: String = "",
    @SerializedName("media_type")
    val mediaType: Int = 0,
    @SerializedName("media_url")
    val mediaUrl: String = "",
    @SerializedName("message")
    val message: String = "",
    @SerializedName("message_cover")
    val messageCover: String = "",
    @SerializedName("message_keywords")
    val messageKeywords: String = "",
    @SerializedName("message_length")
    val messageLength: Int = 0,
    @SerializedName("message_signature")
    val messageSignature: String = "",
    @SerializedName("message_status")
    val messageStatus: Int = 0,
    @SerializedName("message_title")
    val messageTitle: String = "",
    @SerializedName("message_title_md5")
    val messageTitleMd5: String = "",
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("picArr")
    val picArr: List<String> = listOf(),
    @SerializedName("post_signature")
    val postSignature: String = "",
    @SerializedName("pre_recommend_self")
    val preRecommendSelf: Int = 0,
    @SerializedName("publish_status")
    val publishStatus: Int = 0,
    @SerializedName("question_answer_num")
    val questionAnswerNum: Int = 0,
    @SerializedName("question_follow_num")
    val questionFollowNum: Int = 0,
    @SerializedName("rank_score")
    val rankScore: Int = 0,
    @SerializedName("recent_hot_reply_ids")
    val recentHotReplyIds: String = "",
    @SerializedName("recent_like_list")
    val recentLikeList: String = "",
    @SerializedName("recent_reply_ids")
    val recentReplyIds: String = "",
    @SerializedName("recommend")
    val recommend: Int = 0,
    @SerializedName("related_dyh_ids")
    val relatedDyhIds: String = "",
    @SerializedName("relateddata")
    val relateddata: List<Any> = listOf(),
    @SerializedName("relatednum")
    val relatednum: Int = 0,
    @SerializedName("replyRows")
    val replyRows: List<Any> = listOf(),
    @SerializedName("replyRowsCount")
    val replyRowsCount: Int = 0,
    @SerializedName("replyRowsMore")
    val replyRowsMore: Int = 0,
    @SerializedName("replynum")
    val replynum: Int = 0,
    @SerializedName("reportnum")
    val reportnum: Int = 0,
    @SerializedName("share_num")
    val shareNum: Int = 0,
    @SerializedName("shareUrl")
    val shareUrl: String = "",
    @SerializedName("sourceFeed")
    val sourceFeed: Any = Any(),
    @SerializedName("source_id")
    val sourceId: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("tag_count")
    val tagCount: Int = 0,
    @SerializedName("tags")
    val tags: String = "",
    @SerializedName("tcat")
    val tcat: Int = 0,
    @SerializedName("tid")
    val tid: Long = 0,
    @SerializedName("tinfo")
    val tinfo: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("tpic")
    val tpic: String = "",
    @SerializedName("ttitle")
    val ttitle: String = "",
    @SerializedName("ttype")
    val ttype: Int = 0,
    @SerializedName("turl")
    val turl: String = "",
    @SerializedName("turlTarget")
    val turlTarget: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("url_count")
    val urlCount: Int = 0,
    @SerializedName("userAction")
    val userAction: UserAction = UserAction(),
    @SerializedName("userAvatar")
    val userAvatar: String = "",
    @SerializedName("userInfo")
    val userInfo: UserInfo = UserInfo(),
    @SerializedName("user_tags")
    val userTags: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("viewnum")
    val viewnum: Int = 0,
    @SerializedName("vote_score")
    val voteScore: Int = 0
)