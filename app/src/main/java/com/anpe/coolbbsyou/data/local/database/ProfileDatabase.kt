package com.anpe.coolbbsyou.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anpe.coolbbsyou.data.local.dao.ProfileDao
import com.anpe.coolbbsyou.data.local.entity.profile.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
abstract class ProfileDatabase: RoomDatabase() {
    companion object {
        private var instance: ProfileDatabase? = null

        @Synchronized
        fun getProfileDatabase(context: Context): ProfileDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = ProfileDatabase::class.java,
                    name = "profile_database"
                ).build()
            }

            return instance as ProfileDatabase
        }
    }

    abstract fun getProfileDao(): ProfileDao
}