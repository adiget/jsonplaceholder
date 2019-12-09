package com.ags.annada.postslist.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.PostWithUser

@Dao
interface CommentDao {

    @get:Query("SELECT * from comment_table")
    val all: LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(vararg comment: Comment)

    @Query("SELECT * from comment_table WHERE postId = :key")
    fun getCommentWithId(key: Int): LiveData<List<Comment>>

    @Query("DELETE from comment_table")
    suspend fun deleteAll()
}