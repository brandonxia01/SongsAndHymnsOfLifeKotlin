package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.darrengu.songsandhymnsoflife.SongApplication

@Database(entities = [(Song::class), (Category::class), (SongJoinCategory::class)], version = 1)
abstract class SongDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "database_name"

        val INSTANCE: SongDatabase
                = Room.databaseBuilder(SongApplication.instance, SongDatabase::class.java, DB_NAME).build()
    }

    abstract fun getAppDAO(): SongDAO
}