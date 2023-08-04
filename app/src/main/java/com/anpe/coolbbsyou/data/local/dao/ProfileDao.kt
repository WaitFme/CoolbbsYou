package com.anpe.coolbbsyou.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anpe.coolbbsyou.data.local.entity.profile.ProfileEntity

@Dao
interface ProfileDao {
    @Upsert
    fun upsertProfile(vararg profileEntity: ProfileEntity)

    @Delete
    fun deleteProfile(vararg profileEntity: ProfileEntity)

    @Query("DELETE FROM ProfileEntity")
    fun deleteAllProfile()

    @Query("SELECT * FROM ProfileEntity")
    suspend fun getAllWatch(): List<ProfileEntity>
}