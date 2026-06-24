package com.harbourspace.unsplash.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UnsplashItem::class], version = 1, exportSchema = false)
@TypeConverters(UnsplashConverters::class)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun unsplashDao(): UnsplashDao

    companion object {
        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getDatabase(context: Context): UnsplashDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnsplashDatabase::class.java,
                    "unsplash_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
