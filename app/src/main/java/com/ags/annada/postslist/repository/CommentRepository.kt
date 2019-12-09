package com.ags.annada.postslist.repository

import androidx.lifecycle.LiveData
import com.ags.annada.postslist.api.ApiService
import com.ags.annada.postslist.room.daos.CommentDao
import com.ags.annada.postslist.room.entities.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentRepository @Inject constructor(private val dao: CommentDao): BaseRepository() {
    @Inject
    lateinit var api: ApiService

    val allComments = dao.all

    fun fetchAllComments() {
        CoroutineScope(Dispatchers.IO).launch{
            if (allComments.value.isNullOrEmpty()) {
                val comments = api.getComments()
                dao.insertAll(comment = *comments.toTypedArray())
            }
        }
    }

    fun getCommentWithId(postId: Int): LiveData<List<Comment>>{
        return dao.getCommentWithId(postId)
    }
}