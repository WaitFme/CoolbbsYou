package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class UserAction(
    @SerializedName("follow")
    val follow: Int = 0,
    @SerializedName("rating")
    val rating: Int = 0
)