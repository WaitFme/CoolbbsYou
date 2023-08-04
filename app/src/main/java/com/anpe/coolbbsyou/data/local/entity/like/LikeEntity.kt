package com.anpe.coolbbsyou.data.local.entity.like

import androidx.room.Entity

@Entity
data class LikeEntity(
    val data: Int,
    val status: Int,
    val error: Int,
    val message: String,
    val messageStatus: Int
)
