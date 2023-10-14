package com.anpe.coolbbsyou.data.remote.domain.space


import com.google.gson.annotations.SerializedName

data class TargetRow(
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("isFollow")
    val isFollow: Int = 0,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("star_average_score")
    val starAverageScore: Double = 0.0,
    @SerializedName("star_total_count")
    val starTotalCount: Int = 0,
    @SerializedName("subTitle")
    val subTitle: String = "",
    @SerializedName("targetType")
    val targetType: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)