package com.ags.annada.postslist.viewmodel.comments

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ags.annada.postslist.R
import com.ags.annada.postslist.repository.CommentRepository
import com.ags.annada.postslist.room.entities.Comment
import kotlinx.coroutines.*

class CommentsListViewModel @ViewModelInject constructor(
    private val repository: CommentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadComments() }

    val allComments: LiveData<List<Comment>> =
        repository.getCommentWithId(savedStateHandle.get<Int>("postId") ?: 0)
    private val viewModelJob = Job()


    private val errorHandler = CoroutineExceptionHandler { _, error ->
        onRetrieveCommentsFinish()
        when (error) {
            is Exception -> onRetrieveCommentsError()
        }
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)

    init {
        loadComments()
    }

    private fun loadComments() {
        uiScope.launch {
            onRetrieveCommentsStart()

            repository.fetchAllComments()

            onRetrieveCommentsFinish()
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    private suspend fun onRetrieveCommentsStart() {
        withContext(Dispatchers.Main) {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null
        }
    }

    private fun onRetrieveCommentsFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveCommentsError() {
        errorMessage.value = R.string.loading_error_comments
    }
}