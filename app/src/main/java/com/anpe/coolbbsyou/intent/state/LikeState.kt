package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.data.remote.domain.like.LikeModel

data class LikeState(
    val isLike: Boolean = false,
    val likeModel: LikeModel = LikeModel()
)
