package com.anpe.coolbbsyou.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anpe.coolbbsyou.data.local.entity.later.LaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LaterDao {
    @Upsert
    fun upsertLater(vararg laterEntity: LaterEntity)

    @Delete
    fun deleteLater(vararg laterEntity: LaterEntity)

    @Query("DELETE FROM LaterEntity")
    fun deleteAllLater()

    @Query("SELECT * FROM LaterEntity")
    fun getAllLater(): Flow<List<LaterEntity>>
}