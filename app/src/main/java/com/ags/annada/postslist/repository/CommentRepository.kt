package com.ags.annada.postslist.repository

import androidx.lifecycle.LiveData
import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.PostDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(private val database: PostDatabase) {
    @Inject
    lateinit var api: ApiService

    val allComments = database.commentDao().all

    fun fetchAllComments() {
        CoroutineScope(Dispatchers.IO).launch{
            if (allComments.value.isNullOrEmpty()) {
                val comments = api.getComments()
                database.commentDao().insertAll(comment = *comments.toTypedArray())
            }
        }
    }

    fun getCommentWithId(postId: Int): LiveData<List<Comment>>{
        return database.commentDao().getCommentWithId(postId)
    }
}