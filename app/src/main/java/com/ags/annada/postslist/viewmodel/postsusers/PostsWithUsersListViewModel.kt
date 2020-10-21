package com.ags.annada.postslist.viewmodel.postsusers

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ags.annada.postslist.R
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.utils.Event
import com.ags.annada.postslist.utils.SingleLiveEvent
import com.example.gabriel.jsonplaceholder.data.PostRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class PostsWithUsersListViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _selectItemEvent = MutableLiveData<Event<Int>>()
    val selectItemEvent: LiveData<Event<Int>> = _selectItemEvent

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPostsWithUsers() }

    val allPostsWithUsers: LiveData<List<PostWithUser>> = postRepository.allPostsWithUsers

    private val viewModelJob = Job()
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        onRetrievePostsFinish()
        when (error) {
            is Exception -> onRetrievePostsError()
        }
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)

    init {
        loadPostsWithUsers()
    }

    private fun loadPostsWithUsers() {
        uiScope.launch {
            onRetrievePostsStart()

            postRepository.fetchAllPostsAndUsers()

            onRetrievePostsFinish()
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    private suspend fun onRetrievePostsStart() {
        withContext(Main) {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null
        }
    }

    private fun onRetrievePostsFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostsError() {
        errorMessage.value = R.string.loading_error
    }

    fun onClickItem(item: PostWithUser) {
        _selectItemEvent.value = Event(item.id)
    }
}