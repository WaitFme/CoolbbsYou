package com.anpe.coolbbsyou.data.local.entity.later

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("later_table")
data class LaterEntity(
    @PrimaryKey val feedId: Long,
    val feedTitle: String,
    val feedMessage: String,
)
