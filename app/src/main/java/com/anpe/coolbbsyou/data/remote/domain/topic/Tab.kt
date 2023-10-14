package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class Tab(
    @SerializedName("entityId")
    val entityId: Int = 0,
    @SerializedName("entityType")
    val entityType: String = "",
    @SerializedName("page_name")
    val pageName: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)