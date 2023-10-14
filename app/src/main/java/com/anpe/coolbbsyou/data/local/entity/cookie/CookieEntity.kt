package com.anpe.coolbbsyou.data.local.entity.cookie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cookie_table")
data class CookieEntity(
    @PrimaryKey
    val name: String,
    val domain: String,
    val content: String
)
