package com.anpe.coolbbsyou.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anpe.coolbbsyou.data.local.dao.LaterDao
import com.anpe.coolbbsyou.data.local.entity.later.LaterEntity

@Database(entities = [LaterEntity::class], version = 1, exportSchema = false)
abstract class LaterDatabase: RoomDatabase() {
    companion object {
        private var instance: LaterDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): LaterDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = LaterDatabase::class.java,
                    name = "later_database"
                ).build()
            }

            return instance as LaterDatabase
        }
    }

    abstract fun getLaterDao(): LaterDao
}