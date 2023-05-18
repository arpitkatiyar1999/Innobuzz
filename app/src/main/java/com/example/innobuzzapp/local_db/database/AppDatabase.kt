package com.example.innobuzzapp.local_db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.innobuzzapp.local_db.dao.PostDao
import com.example.innobuzzapp.local_db.entity.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}