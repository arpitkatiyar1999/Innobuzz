package com.example.innobuzzapp.ui.main.repo

import android.util.Log
import com.example.innobuzzapp.local_db.database.AppDatabase
import com.example.innobuzzapp.local_db.entity.PostEntity
import com.example.innobuzzapp.remote.api_service.ApiService
import com.example.innobuzzapp.utils.SealedResult
import com.example.innobuzzapp.utils.requestAwait
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : MainRepo {

    override suspend fun getPostsFromServer(): SealedResult<ArrayList<PostEntity>?> {
        Log.e("abc", "getPostsFromServer: ")
        return requestAwait {
            apiService.getAllPostFromServerAsync()
        }
    }

    override suspend fun savePostListToDb(postList: List<PostEntity>): LongArray {
        return appDatabase.postDao().insertAllPosts(postList)
    }

    override suspend fun deleteDataFromDb() {
        appDatabase.postDao().deleteAllPosts()
    }

    override suspend fun getAllPostsFromDatabase(): ArrayList<PostEntity>? {
        return appDatabase.postDao().getAllPosts() as ArrayList<PostEntity>?
    }


}