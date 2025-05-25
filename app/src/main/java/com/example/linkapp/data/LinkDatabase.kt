package com.example.linkapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LinkEntity::class], version = 1)
abstract class LinkDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao

    companion object {
        @Volatile private var INSTANCE: LinkDatabase? = null

        fun getDatabase(context: Context): LinkDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LinkDatabase::class.java,
                    "link_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}