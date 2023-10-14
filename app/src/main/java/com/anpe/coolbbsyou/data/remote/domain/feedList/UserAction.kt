package com.anpe.coolbbsyou.data.remote.domain.feedList


import com.google.gson.annotations.SerializedName

data class UserAction(
    @SerializedName("collect")
    val collect: Int = 0,
    @SerializedName("favorite")
    val favorite: Int = 0,
    @SerializedName("follow")
    val follow: Int = 0,
    @SerializedName("like")
    val like: Int = 0
)