package com.example.innobuzzapp.ui.main.repo

import com.example.innobuzzapp.local_db.entity.PostEntity
import com.example.innobuzzapp.utils.SealedResult

interface MainRepo {

    suspend fun getPostsFromServer(): SealedResult<ArrayList<PostEntity>?>

    suspend fun savePostListToDb(postList: List<PostEntity>): LongArray

    suspend fun deleteDataFromDb()

    suspend fun getAllPostsFromDatabase(): ArrayList<PostEntity>?
}