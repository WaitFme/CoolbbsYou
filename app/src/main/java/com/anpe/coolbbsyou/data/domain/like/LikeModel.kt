package com.anpe.coolbbsyou.data.domain.like

data class LikeModel(
    val `data`: Data
)

data class Data(
    val count: Int,
    val recentLikeList: Any
)