package com.example.innobuzzapp.listeners

import com.example.innobuzzapp.local_db.entity.PostEntity

interface PostListClickListener {
    fun onPostClicked(postEntity: PostEntity)
}