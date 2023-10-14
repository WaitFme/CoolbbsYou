package com.anpe.coolbbsyou.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anpe.coolbbsyou.data.local.dao.CookieDao
import com.anpe.coolbbsyou.data.local.dao.DeviceDao
import com.anpe.coolbbsyou.data.local.dao.LaterDao
import com.anpe.coolbbsyou.data.local.entity.cookie.CookieEntity
import com.anpe.coolbbsyou.data.local.entity.device.DeviceEntity
import com.anpe.coolbbsyou.data.local.entity.later.LaterEntity

@Database(entities = [CookieEntity::class, DeviceEntity::class, LaterEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {
    companion object {
        private var instance: MyDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = MyDatabase::class.java,
                    name = "my_database"
                ).build()
            }

            return instance as MyDatabase
        }
    }

    abstract fun getCookieDao(): CookieDao

    abstract fun getDeviceDao(): DeviceDao

    abstract fun getLaterDao(): LaterDao
}