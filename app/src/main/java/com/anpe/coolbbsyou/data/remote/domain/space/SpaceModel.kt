package com.anpe.coolbbsyou.data.remote.domain.space


import com.google.gson.annotations.SerializedName

data class SpaceModel(
    @SerializedName("data")
    val `data`: Data = Data()
)