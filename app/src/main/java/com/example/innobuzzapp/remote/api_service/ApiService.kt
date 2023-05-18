package com.example.innobuzzapp.remote.api_service

import com.example.innobuzzapp.local_db.entity.PostEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getAllPostFromServerAsync(): Deferred<ArrayList<PostEntity>>
}