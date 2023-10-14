package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class RecommendRow(
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("entityTypeName")
    val entityTypeName: String = "",
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)