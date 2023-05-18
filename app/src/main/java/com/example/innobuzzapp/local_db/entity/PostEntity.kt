package com.example.innobuzzapp.local_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Nullable

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val body: String
) : Serializable