package com.ags.annada.postslist.room.entities

data class PostWithUser(
    val id: Int,
    val title: String,
    val body: String,
    val name: String,
    val username: String
)