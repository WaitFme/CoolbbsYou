package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ad_link_url")
    val adLinkUrl: String = "",
    @SerializedName("ad_pic_url")
    val adPicUrl: String = "",
    @SerializedName("admin_uid")
    val adminUid: Int = 0,
    @SerializedName("admin_username")
    val adminUsername: String = "",
    @SerializedName("allow_rate")
    val allowRate: Int = 0,
    @SerializedName("bind_avatar_plugin_id")
    val bindAvatarPluginId: Int = 0,
    @SerializedName("bind_avatar_plugin_keywords")
    val bindAvatarPluginKeywords: String = "",
    @SerializedName("bind_goods_id")
    val bindGoodsId: Int = 0,
    @SerializedName("bind_product_id")
    val bindProductId: Int = 0,
    @SerializedName("click")
    val click: Int = 0,
    @SerializedName("commentnum")
    val commentnum: Int = 0,
    @SerializedName("commentnum_txt")
    val commentnumTxt: String = "",
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("dateline")
    val dateline: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("disallow_headline")
    val disallowHeadline: Int = 0,
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("follownum")
    val follownum: Int = 0,
    @SerializedName("follownum_txt")
    val follownumTxt: String = "",
    @SerializedName("hash")
    val hash: String = "",
    @SerializedName("hot_num")
    val hotNum: Int = 0,
    @SerializedName("hot_num_txt")
    val hotNumTxt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("intro")
    val intro: String = "",
    @SerializedName("is_live")
    val isLive: Int = 0,
    @SerializedName("is_recommend")
    val isRecommend: Int = 0,
    @SerializedName("keywords")
    val keywords: String = "",
    @SerializedName("lastupdate")
    val lastupdate: Int = 0,
    @SerializedName("level_limit")
    val levelLimit: Int = 0,
    @SerializedName("link_goods_list")
    val linkGoodsList: String = "",
    @SerializedName("link_goods_list_title")
    val linkGoodsListTitle: String = "",
    @SerializedName("live_end_at")
    val liveEndAt: Int = 0,
    @SerializedName("livePresenterNames")
    val livePresenterNames: String = "",
    @SerializedName("live_start_at")
    val liveStartAt: Int = 0,
    @SerializedName("live_status")
    val liveStatus: Int = 0,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("only_self_keywords")
    val onlySelfKeywords: String = "",
    @SerializedName("open_rate")
    val openRate: Int = 0,
    @SerializedName("phone_info")
    val phoneInfo: String = "",
    @SerializedName("phone_price")
    val phonePrice: String = "",
    @SerializedName("product_title")
    val productTitle: String = "",
    @SerializedName("publish_limit")
    val publishLimit: Int = 0,
    @SerializedName("rating_average_score")
    val ratingAverageScore: String = "",
    @SerializedName("rating_total_num")
    val ratingTotalNum: Int = 0,
    @SerializedName("recent_follow_list")
    val recentFollowList: List<RecentFollow> = listOf(),
    @SerializedName("recommendRows")
    val recommendRows: List<RecommendRow> = listOf(),
    @SerializedName("relation_tag")
    val relationTag: String = "",
    @SerializedName("replynum")
    val replynum: Int = 0,
    @SerializedName("selectedTab")
    val selectedTab: String = "",
    @SerializedName("show_date")
    val showDate: String = "",
    @SerializedName("simple_rating")
    val simpleRating: Int = 0,
    @SerializedName("sort")
    val sort: Int = 0,
    @SerializedName("star_1_count")
    val star1Count: Int = 0,
    @SerializedName("star_2_count")
    val star2Count: Int = 0,
    @SerializedName("star_3_count")
    val star3Count: Int = 0,
    @SerializedName("star_4_count")
    val star4Count: Int = 0,
    @SerializedName("star_5_count")
    val star5Count: Int = 0,
    @SerializedName("star_average_score")
    val starAverageScore: String = "",
    @SerializedName("star_total_count")
    val starTotalCount: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("tabList")
    val tabList: List<Tab> = listOf(),
    @SerializedName("tag_category")
    val tagCategory: String = "",
    @SerializedName("tag_pics")
//    val tagPics: List<String> = listOf(),
    val tagPics: String = "",
    @SerializedName("tag_type")
    val tagType: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("top_feed_id")
    val topFeedId: Int = 0,
    @SerializedName("top_ids")
    val topIds: List<Any> = listOf(),
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("userAction")
    val userAction: UserAction = UserAction(),
    @SerializedName("username")
    val username: String = "",
    @SerializedName("video_url")
    val videoUrl: String = "",
    @SerializedName("votenum")
    val votenum: Int = 0,
    @SerializedName("votenum1")
    val votenum1: Int = 0,
    @SerializedName("votenum2")
    val votenum2: Int = 0,
    @SerializedName("votenum3")
    val votenum3: Int = 0,
    @SerializedName("votenum4")
    val votenum4: Int = 0,
    @SerializedName("votenum5")
    val votenum5: Int = 0,
    @SerializedName("votescore")
    val votescore: Int = 0
)