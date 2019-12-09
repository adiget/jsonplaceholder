package com.annada.android.sample.jsonposts.vm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ags.annada.postslist.R
import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.room.daos.PostDao
import com.ags.annada.postslist.room.daos.UserDao
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.room.entities.User
import com.ags.annada.postslist.ui.PostListener
import com.ags.annada.postslist.ui.PostsWithUserListAdapter
import com.ags.annada.postslist.utils.SingleLiveEvent
import com.example.gabriel.jsonplaceholder.data.PostRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

class PostsWithUsersListViewModel(private val postDao: PostDao, private val userDao: UserDao) : BaseViewModel() {
    internal val selectItemEvent = SingleLiveEvent<Int>()

    val postWithUsersListAdapter: PostsWithUserListAdapter = PostsWithUserListAdapter(PostListener { postId ->
        selectItemEvent.value = postId
    })

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPostsWithUsers() }

    var repository: PostRepository = PostRepository(postDao, userDao)
    val allPostsWithUsers: LiveData<List<PostWithUser>> = repository.allPostsWithUsers

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

            repository.fetchAllPostsAndUsers()

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
}