package com.anpe.coolbbsyou.data.entity.device

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("device_info")
data class DeviceEntity(
    @PrimaryKey
    val name: String,
    val manufacturer: String,
    val brand: String,
    val model: String
)
