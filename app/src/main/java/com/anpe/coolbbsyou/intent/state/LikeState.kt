package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.like.LikeModel

data class LikeState(
    val isLike: Boolean = false,
    val likeModel: LikeModel = LikeModel()
)
