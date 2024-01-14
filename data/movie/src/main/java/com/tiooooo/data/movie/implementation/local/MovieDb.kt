package com.tiooooo.data.movie.implementation.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tiooooo.data.movie.implementation.local.dao.SearchHistoryDao
import com.tiooooo.data.movie.implementation.local.entity.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
    ],
    version = 1,
)
abstract class MovieDb : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private const val DB_NAME = "Account.db"

        @Volatile
        private var INSTANCE: MovieDb? = null

        @Synchronized
        fun getInstance(ctx: Context): MovieDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    ctx.applicationContext,
                    MovieDb::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MovieDb
        }
    }
}
