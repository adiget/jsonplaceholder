package com.ags.annada.postslist.api

import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.User
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("users")
    suspend fun getUsers(): List<User>
}