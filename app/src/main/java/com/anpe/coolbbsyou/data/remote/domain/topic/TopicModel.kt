package com.anpe.coolbbsyou.data.remote.domain.topic


import com.google.gson.annotations.SerializedName

data class TopicModel(
    @SerializedName("data")
    val `data`: Data = Data()
)