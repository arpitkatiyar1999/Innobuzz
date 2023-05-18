package com.example.innobuzzapp.ui.main.post_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.innobuzzapp.base.BaseViewModel
import com.example.innobuzzapp.local_db.entity.PostEntity
import com.example.innobuzzapp.ui.main.repo.MainRepo
import com.example.innobuzzapp.utils.DatabaseException
import com.example.innobuzzapp.utils.Event
import com.example.innobuzzapp.utils.SealedResult
import com.example.innobuzzapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListsViewModel @Inject constructor(private val mainRepo: MainRepo) : BaseViewModel() {
    val postList = ArrayList<PostEntity>()
    private val _postListFromDbLiveData = MutableLiveData<Event<State<Nothing?>>>()
    val postListFromDbLiveData: LiveData<Event<State<Nothing?>>> get() = _postListFromDbLiveData

    private val _postListFromServerLiveData =
        MutableLiveData<Event<State<ArrayList<PostEntity>?>>>()
    val postListFromServerLiveData: LiveData<Event<State<ArrayList<PostEntity>?>>> get() = _postListFromServerLiveData


    private val _refreshLiveData = MutableLiveData<Event<State<Nothing?>>>()
    val refreshLiveData: LiveData<Event<State<Nothing?>>> get() = _refreshLiveData

    fun getPostsFromDatabase() {
        _postListFromDbLiveData.postValue(Event(State.loading()))
        viewModelScope.launch {
            val data = mainRepo.getAllPostsFromDatabase()
            data?.let {
                postList.clear()
                postList.addAll(it)
            }
            _postListFromDbLiveData.postValue(Event(State.success(null)))
        }
    }

    fun getPostsFromServer(deleteAllData: Boolean) {
        _postListFromServerLiveData.postValue(Event(State.loading()))
        viewModelScope.launch {
            if (deleteAllData) {
                mainRepo.deleteDataFromDb()
            }
            when (val response = mainRepo.getPostsFromServer()) {
                is SealedResult.Success -> {
                    if (response.data != null) {
                        val dbInsertionResult = mainRepo.savePostListToDb(response.data)
                        if (dbInsertionResult.size != response.data.size) {
                            val exception =
                                DatabaseException("Post List Insertion Failed To Database")
                            _postListFromDbLiveData.postValue(
                                Event(
                                    State.error(
                                        exception.message,
                                        exception
                                    )
                                )
                            )
                        } else {
                            /*
                                could have added the post list from server to postList but i made database as the single source of truth ,so fetched the current list
                                from data database and added it to postList
                             */
                            mainRepo.getAllPostsFromDatabase()?.let {
                                postList.clear()
                                postList.addAll(it)
                            }
                            _postListFromServerLiveData.postValue(Event(State.success(null)))
                        }

                    } else {
                        _postListFromServerLiveData.postValue(Event(State.success(null)))
                    }
                }

                is SealedResult.Error -> {
                    _postListFromServerLiveData.postValue(
                        Event(
                            State.error(
                                response.exception.message ?: "Some Error Occurred",
                                response.exception,
                            )
                        )
                    )
                }
            }
        }
    }
}