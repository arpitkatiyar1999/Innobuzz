package com.example.innobuzzapp.utils

import android.view.View
import kotlinx.coroutines.Deferred

suspend fun <T> requestAwait(call: () -> Deferred<T>): SealedResult<T> {
    return try {
        val result = call().await()
        SealedResult.success(result)
    } catch (exception: Exception) {
        SealedResult.error(exception)
    }
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}