package com.ags.annada.postslist.viewmodel.comments

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import com.ags.annada.postslist.R
import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.repository.CommentRepository
import com.ags.annada.postslist.room.daos.CommentDao
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.ui.comments.CommentsListAdapter
import com.annada.android.sample.jsonposts.vm.BaseViewModel
import com.example.gabriel.jsonplaceholder.data.PostRepository
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import javax.inject.Inject

class CommentsListViewModel(private val commentDao: CommentDao, postId: Int) : BaseViewModel() {
    val commentsListAdapter: CommentsListAdapter = CommentsListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadComments() }

    var repository: CommentRepository = CommentRepository(commentDao)
    val allComments: LiveData<List<Comment>> = repository.getCommentWithId(postId)
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