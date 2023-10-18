package com.anpe.coolbbsyou.data.remote.domain.space


import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("admintype")
    val admintype: Int = 0,
    @SerializedName("alias_title")
    val aliasTitle: String = "",
    @SerializedName("allow_rating")
    val allowRating: Int = 0,
    @SerializedName("avatar_cover_status")
    val avatarCoverStatus: Int = 0,
    @SerializedName("avatar_plugin_status")
    val avatarPluginStatus: Int = 0,
    @SerializedName("avatarstatus")
    val avatarstatus: Int = 0,
    @SerializedName("block_status")
    val blockStatus: Int = 0,
    @SerializedName("brand_id")
    val brandId: Int = 0,
    @SerializedName("buy_count")
    val buyCount: Int = 0,
    @SerializedName("category_id")
    val categoryId: Int = 0,
    @SerializedName("config_num")
    val configNum: Int = 0,
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("coverArr")
    val coverArr: List<String> = listOf(),
    @SerializedName("create_time")
    val createTime: Int = 0,
    @SerializedName("create_uid")
    val createUid: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("device_info")
    val deviceInfo: String = "",
    @SerializedName("device_owner_status")
    val deviceOwnerStatus: Int = 0,
    @SerializedName("disallow_headline")
    val disallowHeadline: Int = 0,
    @SerializedName("displayUsername")
    val displayUsername: String = "",
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("experience")
    val experience: Int = 0,
    @SerializedName("feed_answer_num")
    val feedAnswerNum: Int = 0,
    @SerializedName("feed_article_num")
    val feedArticleNum: Int = 0,
    @SerializedName("feed_comment_num")
    val feedCommentNum: Int = 0,
    @SerializedName("feed_comment_num_txt")
    val feedCommentNumTxt: String = "",
    @SerializedName("feed_review_num")
    val feedReviewNum: Int = 0,
    @SerializedName("feed_trade_num")
    val feedTradeNum: Int = 0,
    @SerializedName("feed_video_num")
    val feedVideoNum: Int = 0,
    @SerializedName("fetchType")
    val fetchType: String = "",
    @SerializedName("follow_num")
    val followNum: Int = 0,
    @SerializedName("follow_num_txt")
    val followNumTxt: String = "",
    @SerializedName("follow_time")
    val followTime: Int = 0,
    @SerializedName("groupid")
    val groupid: Int = 0,
    @SerializedName("hot_num")
    val hotNum: Int = 0,
    @SerializedName("hot_num_txt")
    val hotNumTxt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("index_title")
    val indexTitle: String = "",
    @SerializedName("isDeveloper")
    val isDeveloper: Int = 0,
    @SerializedName("is_recommend")
    val isRecommend: Int = 0,
    @SerializedName("last_update")
    val lastUpdate: Int = 0,
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("level_detail_url")
    val levelDetailUrl: String = "",
    @SerializedName("level_today_message")
    val levelTodayMessage: String = "",
    @SerializedName("link_tag")
    val linkTag: String = "",
    @SerializedName("logintime")
    val logintime: Int = 0,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("next_level_experience")
    val nextLevelExperience: Int = 0,
    @SerializedName("next_level_percentage")
    val nextLevelPercentage: String = "",
    @SerializedName("owner_buy_count")
    val ownerBuyCount: Int = 0,
    @SerializedName("owner_rating_total_num")
    val ownerRatingTotalNum: Int = 0,
    @SerializedName("owner_star_1_count")
    val ownerStar1Count: Int = 0,
    @SerializedName("owner_star_2_count")
    val ownerStar2Count: Int = 0,
    @SerializedName("owner_star_3_count")
    val ownerStar3Count: Int = 0,
    @SerializedName("owner_star_4_count")
    val ownerStar4Count: Int = 0,
    @SerializedName("owner_star_5_count")
    val ownerStar5Count: Int = 0,
    @SerializedName("owner_star_average_score")
    val ownerStarAverageScore: Double = 0.0,
    @SerializedName("owner_star_total_count")
    val ownerStarTotalCount: Int = 0,
    @SerializedName("price_currency")
    val priceCurrency: String = "",
    @SerializedName("price_max")
    val priceMax: Float = 0f, // Int
    @SerializedName("price_min")
    val priceMin: Float = 0f, // Int
    @SerializedName("rank_status")
    val rankStatus: Int = 0,
    @SerializedName("rating_average_score")
    val ratingAverageScore: Double = 0.0,
    @SerializedName("rating_average_score_1")
    val ratingAverageScore1: String = "",
    @SerializedName("rating_average_score_10")
    val ratingAverageScore10: String = "",
    @SerializedName("rating_average_score_2")
    val ratingAverageScore2: String = "",
    @SerializedName("rating_average_score_3")
    val ratingAverageScore3: String = "",
    @SerializedName("rating_average_score_4")
    val ratingAverageScore4: String = "",
    @SerializedName("rating_average_score_5")
    val ratingAverageScore5: String = "",
    @SerializedName("rating_average_score_6")
    val ratingAverageScore6: String = "",
    @SerializedName("rating_average_score_7")
    val ratingAverageScore7: String = "",
    @SerializedName("rating_average_score_8")
    val ratingAverageScore8: String = "",
    @SerializedName("rating_average_score_9")
    val ratingAverageScore9: String = "",
    @SerializedName("rating_total_num")
    val ratingTotalNum: Int = 0,
    @SerializedName("recent_30_days_star_average_score")
    val recent30DaysStarAverageScore: String = "",
    @SerializedName("recent_30_days_star_total_count")
    val recent30DaysStarTotalCount: Int = 0,
    @SerializedName("recent_7_days_star_average_score")
    val recent7DaysStarAverageScore: String = "",
    @SerializedName("recent_7_days_star_total_count")
    val recent7DaysStarTotalCount: Int = 0,
    @SerializedName("recent_num_star_average_score")
    val recentNumStarAverageScore: String = "",
    @SerializedName("recent_num_star_total_count")
    val recentNumStarTotalCount: Int = 0,
    @SerializedName("recommend_image_num")
    val recommendImageNum: Int = 0,
    @SerializedName("recommend_video_num")
    val recommendVideoNum: Int = 0,
    @SerializedName("regdate")
    val regdate: Int = 0,
    @SerializedName("release_status")
    val releaseStatus: Int = 0,
    @SerializedName("release_time")
    val releaseTime: String = "",
    @SerializedName("sell_url")
    val sellUrl: String = "",
    @SerializedName("series_id")
    val seriesId: Int = 0,
    @SerializedName("share_num")
    val shareNum: Int = 0,
    @SerializedName("short_title")
    val shortTitle: String = "",
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
    val starAverageScore: Double = 0.0,
    @SerializedName("star_total_count")
    val starTotalCount: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("tagArr")
    val tagArr: List<String> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("update_time")
    val updateTime: Int = 0,
    @SerializedName("update_uid")
    val updateUid: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("userAvatar")
    val userAvatar: String = "",
    @SerializedName("userBigAvatar")
    val userBigAvatar: String = "",
    @SerializedName("userInfo")
    val userInfo: UserInfoX = UserInfoX(),
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
    @SerializedName("vote_bury_num")
    val voteBuryNum: Int = 0,
    @SerializedName("vote_dig_num")
    val voteDigNum: Int = 0,
    @SerializedName("vote_dig_percentage")
    val voteDigPercentage: Int = 0,
    @SerializedName("wish_count")
    val wishCount: Int = 0
)