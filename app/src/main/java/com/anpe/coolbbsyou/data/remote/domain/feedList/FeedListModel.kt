package com.anpe.coolbbsyou.data.remote.domain.feedList


import com.google.gson.annotations.SerializedName

data class FeedListModel(
    @SerializedName("data")
    val `data`: List<Data> = listOf()
)