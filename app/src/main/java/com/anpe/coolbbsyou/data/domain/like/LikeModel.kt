package com.anpe.coolbbsyou.data.domain.like

import com.google.gson.annotations.SerializedName

data class LikeModel(
    @SerializedName("data")
    val data: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val error: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageStatus")
    val messageStatus: Int
)