package com.anpe.coolbbsyou.network.data.model.suggest
import com.google.gson.annotations.SerializedName


data class SuggestSearchEntity(
    @SerializedName("data")
    val `data`: List<Data>
)

data class Data(
    @SerializedName("adminscore")
    val adminscore: String,
    @SerializedName("allow_rating")
    val allowRating: Int,
    @SerializedName("apkRomVersion")
    val apkRomVersion: String,
    @SerializedName("apkTypeName")
    val apkTypeName: String,
    @SerializedName("apkTypeUrl")
    val apkTypeUrl: String,
    @SerializedName("apkUrl")
    val apkUrl: String,
    @SerializedName("apklength")
    val apklength: Int,
    @SerializedName("apkmd5")
    val apkmd5: String,
    @SerializedName("apkname")
    val apkname: String,
    @SerializedName("apkname2")
    val apkname2: String,
    @SerializedName("apksize")
    val apksize: String,
    @SerializedName("apktype")
    val apktype: Int,
    @SerializedName("apkversioncode")
    val apkversioncode: Int,
    @SerializedName("apkversionname")
    val apkversionname: String,
    @SerializedName("catDir")
    val catDir: String,
    @SerializedName("catName")
    val catName: String,
    @SerializedName("catid")
    val catid: Int,
    @SerializedName("changelog")
    val changelog: String,
    @SerializedName("comment_block_num")
    val commentBlockNum: Int,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("comment_status")
    val commentStatus: Int,
    @SerializedName("commentStatusText")
    val commentStatusText: String,
    @SerializedName("commentnum")
    val commentnum: Int,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("developername")
    val developername: String,
    @SerializedName("developeruid")
    val developeruid: Int,
    @SerializedName("digest")
    val digest: Int,
    @SerializedName("downCount")
    val downCount: String,
    @SerializedName("downnum")
    val downnum: Int,
    @SerializedName("entityId")
    val entityId: Int,
    @SerializedName("entityType")
    val entityType: String,
    @SerializedName("extraFlag")
    val extraFlag: String,
    @SerializedName("favnum")
    val favnum: Int,
    @SerializedName("followCount")
    val followCount: Int,
    @SerializedName("follownum")
    val follownum: Int,
    @SerializedName("get_timewit_cpc")
    val getTimewitCpc: Int,
    @SerializedName("hot_num")
    val hotNum: Int,
    @SerializedName("hot_num_txt")
    val hotNumTxt: String,
    @SerializedName("hotlabel")
    val hotlabel: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_coolapk_cpa")
    val isCoolapkCpa: Int,
    @SerializedName("is_forum_app")
    val isForumApp: Int,
    @SerializedName("isbiz")
    val isbiz: Int,
    @SerializedName("iscps")
    val iscps: Int,
    @SerializedName("ishot")
    val ishot: Int,
    @SerializedName("ishp")
    val ishp: Int,
    @SerializedName("keywords")
    val keywords: String,
    @SerializedName("last_comment_update")
    val lastCommentUpdate: Int,
    @SerializedName("lastupdate")
    val lastupdate: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("open_link")
    val openLink: String,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("pubStatusText")
    val pubStatusText: String,
    @SerializedName("pubdate")
    val pubdate: Int,
    @SerializedName("recommend")
    val recommend: Int,
    @SerializedName("replyCount")
    val replyCount: Int,
    @SerializedName("replynum")
    val replynum: Int,
    @SerializedName("romversion")
    val romversion: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("score_v10")
    val scoreV10: Double,
    @SerializedName("sdkversion")
    val sdkversion: Int,
    @SerializedName("shortTags")
    val shortTags: String,
    @SerializedName("shortlabel")
    val shortlabel: String,
    @SerializedName("shorttitle")
    val shorttitle: String,
    @SerializedName("star")
    val star: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("statusText")
    val statusText: String,
    @SerializedName("target_id")
    val targetId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updateFlag")
    val updateFlag: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("voteCount")
    val voteCount: Int,
    @SerializedName("votenum")
    val votenum: Int
)