package com.example.gabriel.jsonplaceholder.data

import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.room.PostDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val database: PostDatabase,
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    val allPostsWithUsers = database.postDao().fetchAllPostsAndUsers()

    fun fetchAllPostsAndUsers() {
        CoroutineScope(ioDispatcher).launch {
            if (allPostsWithUsers.value.isNullOrEmpty()) {
                val posts = apiService.getPosts()
                database.postDao().insertAll(post = *posts.toTypedArray())

                val users = apiService.getUsers()
                database.userDao().insertAll(user = *users.toTypedArray())
            }
        }
    }
}