package com.example.innobuzzapp.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.innobuzzapp.local_db.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(postsList: List<PostEntity>): LongArray

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()
}