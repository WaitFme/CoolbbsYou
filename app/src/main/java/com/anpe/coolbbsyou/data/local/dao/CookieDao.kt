package com.anpe.coolbbsyou.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.anpe.coolbbsyou.data.local.entity.cookie.CookieEntity

@Dao
interface CookieDao {
    @Upsert
    fun upsert(vararg cookieEntity: CookieEntity)

    @Delete
    fun delete(vararg cookieEntity: CookieEntity): Int

    @Query("DELETE FROM cookie_table")
    fun deleteAll(): Int

    @Query("SELECT * FROM cookie_table")
    fun getAll(): List<CookieEntity>

    @Query("SELECT * FROM cookie_table WHERE domain=:host")
    fun find(host: String): List<CookieEntity>
}