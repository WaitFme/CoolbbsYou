package com.anpe.coolbbsyou.data.remote.domain.search

import com.google.gson.annotations.SerializedName


data class SearchModel(
    @SerializedName("data")
    val `data`: List<Data>
)

data class Data(
    @SerializedName("description")
    val description: String,
    @SerializedName("at_count")
    val atCount: String,
    @SerializedName("avatarFetchType")
    val avatarFetchType: String,
    @SerializedName("block_status")
    val blockStatus: String,
    @SerializedName("burynum")
    val burynum: String,
    @SerializedName("comment_addition")
    val commentAddition: String,
    @SerializedName("comment_addition_pic")
    val commentAdditionPic: String,
    @SerializedName("comment_bad")
    val commentBad: String,
    @SerializedName("comment_bad_pic")
    val commentBadPic: String,
    @SerializedName("comment_block_num")
    val commentBlockNum: String,
    @SerializedName("comment_general")
    val commentGeneral: String,
    @SerializedName("comment_general_pic")
    val commentGeneralPic: String,
    @SerializedName("comment_good")
    val commentGood: String,
    @SerializedName("comment_good_pic")
    val commentGoodPic: String,
    @SerializedName("commentnum")
    val commentnum: String,
    @SerializedName("dateline")
    val dateline: String,
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
    val disallowReply: String,
    @SerializedName("dyh_id")
    val dyhId: String,
    @SerializedName("dyh_name")
    val dyhName: String,
    @SerializedName("enableModify")
    val enableModify: Int,
    @SerializedName("entities")
    val entities: List<Entity>,
    @SerializedName("entityId")
    val entityId: String,
    @SerializedName("entityTemplate")
    val entityTemplate: String,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("extraData")
    val extraData: String,
    @SerializedName("extra_fromApi")
    val extraFromApi: String,
    @SerializedName("extra_info")
    val extraInfo: String,
    @SerializedName("extra_key")
    val extraKey: String,
    @SerializedName("extra_pic")
    val extraPic: String,
    @SerializedName("extra_status")
    val extraStatus: String,
    @SerializedName("extra_title")
    val extraTitle: String,
    @SerializedName("extra_type")
    val extraType: String,
    @SerializedName("extra_url")
    val extraUrl: String,
    @SerializedName("favnum")
    val favnum: String,
    @SerializedName("feedType")
    val feedType: String,
    @SerializedName("feedTypeName")
    val feedTypeName: String,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("fid")
    val fid: String,
    @SerializedName("forwardSourceType")
    val forwardSourceType: String,
    @SerializedName("forwardid")
    val forwardid: String,
    @SerializedName("forwardnum")
    val forwardnum: String,
    @SerializedName("fromid")
    val fromid: String,
    @SerializedName("fromname")
    val fromname: String,
    @SerializedName("hitnum")
    val hitnum: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("index_name")
    val indexName: String,
    @SerializedName("info")
    val info: String,
    @SerializedName("infoHtml")
    val infoHtml: String,
    @SerializedName("is_anonymous")
    val isAnonymous: String,
    @SerializedName("is_hidden")
    val isHidden: String,
    @SerializedName("is_html_article")
    val isHtmlArticle: String,
    @SerializedName("isModified")
    val isModified: Int,
    @SerializedName("is_owner")
    val isOwner: Int,
    @SerializedName("is_pre_recommended")
    val isPreRecommended: Int,
    @SerializedName("issummary")
    val issummary: String,
    @SerializedName("istag")
    val istag: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("lastupdate")
    val lastupdate: String,
    @SerializedName("likenum")
    val likenum: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("media_info")
    val mediaInfo: String,
    @SerializedName("media_pic")
    val mediaPic: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("message_cover")
    val messageCover: String,
    @SerializedName("message_keywords")
    val messageKeywords: String,
    @SerializedName("message_length")
    val messageLength: String,
    @SerializedName("message_signature")
    val messageSignature: String,
    @SerializedName("message_status")
    val messageStatus: String,
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
    @SerializedName("_querySearchTime")
    val querySearchTime: Double,
    @SerializedName("_queryTotal")
    val queryTotal: Int,
    @SerializedName("_queryViewTotal")
    val queryViewTotal: Int,
    @SerializedName("question_answer_num")
    val questionAnswerNum: String,
    @SerializedName("question_follow_num")
    val questionFollowNum: String,
    @SerializedName("rank_score")
    val rankScore: String,
    @SerializedName("rating_score")
    val ratingScore: Int,
    @SerializedName("rating_score_old")
    val ratingScoreOld: Int,
    @SerializedName("recent_hot_reply_ids")
    val recentHotReplyIds: String,
    @SerializedName("recent_like_list")
    val recentLikeList: String,
    @SerializedName("recent_reply_ids")
    val recentReplyIds: String,
    @SerializedName("recommend")
    val recommend: String,
    @SerializedName("relateddata")
    val relateddata: List<Any>,
    @SerializedName("relatednum")
    val relatednum: String,
    @SerializedName("replynum")
    val replynum: Int,
    @SerializedName("reportnum")
    val reportnum: String,
    @SerializedName("shareUrl")
    val shareUrl: String,
    @SerializedName("show_owner")
    val showOwner: Int,
    @SerializedName("sourceFeed")
    val sourceFeed: String,
    @SerializedName("source_id")
    val sourceId: String,
    @SerializedName("star")
    val star: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("tag_count")
    val tagCount: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("tcat")
    val tcat: String,
    @SerializedName("tid")
    val tid: String,
    @SerializedName("tinfo")
    val tinfo: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tpic")
    val tpic: String,
    @SerializedName("ttitle")
    val ttitle: String,
    @SerializedName("ttype")
    val ttype: String,
    @SerializedName("turl")
    val turl: String,
    @SerializedName("turlTarget")
    val turlTarget: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("url_count")
    val urlCount: String,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("userInfo")
    val userInfo: UserInfo,
    @SerializedName("user_tags")
    val userTags: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("viewnum")
    val viewnum: String,
    @SerializedName("vote_score")
    val voteScore: String
)

data class Entity(
    @SerializedName("adminscore")
    val adminscore: String,
    @SerializedName("admintype")
    val admintype: String,
    @SerializedName("albumnum")
    val albumnum: String,
    @SerializedName("apk_alpha_md5")
    val apkAlphaMd5: String,
    @SerializedName("apk_beta_md5")
    val apkBetaMd5: String,
    @SerializedName("apkRomVersion")
    val apkRomVersion: String,
    @SerializedName("apk_sign_md5")
    val apkSignMd5: String,
    @SerializedName("apk_sign_sha1")
    val apkSignSha1: String,
    @SerializedName("apkTypeName")
    val apkTypeName: String,
    @SerializedName("apkTypeUrl")
    val apkTypeUrl: String,
    @SerializedName("apkUrl")
    val apkUrl: String,
    @SerializedName("apklength")
    val apklength: String,
    @SerializedName("apkmd5")
    val apkmd5: String,
    @SerializedName("apkname")
    val apkname: String,
    @SerializedName("apkname2")
    val apkname2: String,
    @SerializedName("apksize")
    val apksize: String,
    @SerializedName("apktype")
    val apktype: String,
    @SerializedName("apkversioncode")
    val apkversioncode: String,
    @SerializedName("apkversionname")
    val apkversionname: String,
    @SerializedName("autoupdate")
    val autoupdate: String,
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: String,
    @SerializedName("avatarstatus")
    val avatarstatus: String,
    @SerializedName("bury")
    val bury: String,
    @SerializedName("catDir")
    val catDir: String,
    @SerializedName("catName")
    val catName: String,
    @SerializedName("catid")
    val catid: String,
    @SerializedName("changelog")
    val changelog: String,
    @SerializedName("click")
    val click: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("comment_block_num")
    val commentBlockNum: String,
    @SerializedName("commentCount")
    val commentCount: String,
    @SerializedName("comment_status")
    val commentStatus: String,
    @SerializedName("commentStatusText")
    val commentStatusText: String,
    @SerializedName("commentnum")
    val commentnum: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("createdate")
    val createdate: String,
    @SerializedName("creatorname")
    val creatorname: String,
    @SerializedName("creatoruid")
    val creatoruid: String,
    @SerializedName("credit")
    val credit: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("developermail")
    val developermail: String,
    @SerializedName("developername")
    val developername: String,
    @SerializedName("developeruid")
    val developeruid: String,
    @SerializedName("digest")
    val digest: String,
    @SerializedName("digg")
    val digg: String,
    @SerializedName("displayUsername")
    val displayUsername: String,
    @SerializedName("downCount")
    val downCount: String,
    @SerializedName("downnum")
    val downnum: String,
    @SerializedName("emailstatus")
    val emailstatus: String,
    @SerializedName("entityId")
    val entityId: String,
    @SerializedName("entityTemplate")
    val entityTemplate: String,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("experience")
    val experience: String,
    @SerializedName("ext_group_ids")
    val extGroupIds: String,
    @SerializedName("extraFlag")
    val extraFlag: String,
    @SerializedName("fans")
    val fans: String,
    @SerializedName("favnum")
    val favnum: String,
    @SerializedName("feed")
    val feed: String,
    @SerializedName("fetchType")
    val fetchType: String,
    @SerializedName("follow")
    val follow: String,
    @SerializedName("followCount")
    val followCount: String,
    @SerializedName("follownum")
    val follownum: String,
    @SerializedName("fromid")
    val fromid: String,
    @SerializedName("fromname")
    val fromname: String,
    @SerializedName("fromtype")
    val fromtype: String,
    @SerializedName("group_id")
    val groupId: String,
    @SerializedName("groupexpires")
    val groupexpires: String,
    @SerializedName("groupid")
    val groupid: String,
    @SerializedName("hitnum")
    val hitnum: String,
    @SerializedName("hot_num")
    val hotNum: String,
    @SerializedName("hot_num_txt")
    val hotNumTxt: String,
    @SerializedName("hotlabel")
    val hotlabel: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("index_name")
    val indexName: String,
    @SerializedName("introduce")
    val introduce: String,
    @SerializedName("isDeveloper")
    val isDeveloper: String,
    @SerializedName("is_forum_app")
    val isForumApp: String,
    @SerializedName("isbiz")
    val isbiz: String,
    @SerializedName("iscps")
    val iscps: String,
    @SerializedName("ishot")
    val ishot: String,
    @SerializedName("ishp")
    val ishp: String,
    @SerializedName("isofficial")
    val isofficial: String,
    @SerializedName("ispad")
    val ispad: String,
    @SerializedName("keywords")
    val keywords: String,
    @SerializedName("last_comment_update")
    val lastCommentUpdate: String,
    @SerializedName("last_feed_status")
    val lastFeedStatus: String,
    @SerializedName("lastupdate")
    val lastupdate: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("level_detail_url")
    val levelDetailUrl: String,
    @SerializedName("level_today_message")
    val levelTodayMessage: String,
    @SerializedName("loginip")
    val loginip: String,
    @SerializedName("logintime")
    val logintime: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("maxsdkversion")
    val maxsdkversion: String,
    @SerializedName("mobilestatus")
    val mobilestatus: String,
    @SerializedName("next_level_experience")
    val nextLevelExperience: Int,
    @SerializedName("next_level_percentage")
    val nextLevelPercentage: String,
    @SerializedName("open_link")
    val openLink: String,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("pubStatusText")
    val pubStatusText: String,
    @SerializedName("pubdate")
    val pubdate: String,
    @SerializedName("_querySearchTime")
    val querySearchTime: Double,
    @SerializedName("_queryTotal")
    val queryTotal: Int,
    @SerializedName("_queryViewTotal")
    val queryViewTotal: Int,
    @SerializedName("rank_score")
    val rankScore: String,
    @SerializedName("realname")
    val realname: String,
    @SerializedName("realnamestatus")
    val realnamestatus: String,
    @SerializedName("recommend")
    val recommend: String,
    @SerializedName("regdate")
    val regdate: String,
    @SerializedName("regip")
    val regip: String,
    @SerializedName("replyCount")
    val replyCount: String,
    @SerializedName("replynum")
    val replynum: String,
    @SerializedName("role_ids")
    val roleIds: String,
    @SerializedName("romversion")
    val romversion: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("score_v10")
    val scoreV10: String,
    @SerializedName("screenList")
    val screenList: List<String>,
    @SerializedName("sdkversion")
    val sdkversion: String,
    @SerializedName("share_feed_num")
    val shareFeedNum: String,
    @SerializedName("shortTags")
    val shortTags: String,
    @SerializedName("shortlabel")
    val shortlabel: String,
    @SerializedName("shorttitle")
    val shorttitle: String,
    @SerializedName("star")
    val star: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("statusText")
    val statusText: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("target_id")
    val targetId: String,
    @SerializedName("thumbList")
    val thumbList: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("updateFlag")
    val updateFlag: String,
    @SerializedName("updatername")
    val updatername: String,
    @SerializedName("updateruid")
    val updateruid: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("userAvatar")
    val userAvatar: String,
    @SerializedName("userBigAvatar")
    val userBigAvatar: String,
    @SerializedName("userSmallAvatar")
    val userSmallAvatar: String,
    @SerializedName("usergroupid")
    val usergroupid: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("usernamestatus")
    val usernamestatus: String,
    @SerializedName("verify_icon")
    val verifyIcon: String,
    @SerializedName("verify_label")
    val verifyLabel: String,
    @SerializedName("verify_show_type")
    val verifyShowType: String,
    @SerializedName("verify_status")
    val verifyStatus: Int,
    @SerializedName("verify_title")
    val verifyTitle: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("viewnum")
    val viewnum: String,
    @SerializedName("voteCount")
    val voteCount: String,
    @SerializedName("votenum")
    val votenum: Int,
    @SerializedName("votescore")
    val votescore: String,
    @SerializedName("votescore_v10")
    val votescoreV10: String,
    @SerializedName("feednum")
    val feednum: String,
    @SerializedName("likenum")
    val likenum: String,
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