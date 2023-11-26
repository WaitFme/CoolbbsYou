package com.anpe.coolbbsyou.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anpe.coolbbsyou.data.entity.device.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Upsert
    fun upsert(vararg device: DeviceEntity)

    @Delete
    fun delete(vararg device: DeviceEntity)

    @Query("DELETE FROM device_info")
    fun deleteAll()

    @Query("SELECT * FROM device_info")
    fun getDeviceAll(): Flow<List<DeviceEntity>>
}