package com.example.gabriel.jsonplaceholder.data

import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.repository.BaseRepository
import com.ags.annada.postslist.room.daos.PostDao
import com.ags.annada.postslist.room.daos.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostRepository (val postDao: PostDao, val userDao: UserDao): BaseRepository() {
    @Inject
    lateinit var api: ApiService

    val allPostsWithUsers = postDao.fetchAllPostsAndUsers()

    fun fetchAllPostsAndUsers() {
        CoroutineScope(Dispatchers.IO).launch{
            if (allPostsWithUsers.value.isNullOrEmpty()) {
                val posts = api.getPosts()
                postDao.insertAll(post = *posts.toTypedArray())

                val users = api.getUsers()
                userDao.insertAll(user = *users.toTypedArray())
            }
        }
    }
}