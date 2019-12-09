package com.ags.annada.postslist.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.PostWithUser


@Dao
interface PostDao {

    @get:Query("SELECT * from post_table")
    val all: LiveData<List<Post>>

    @get:Query("SELECT * from post_table")
    val allDbPosts: List<Post>


    @Query("SELECT p.id, p.title, p.body, u.name, u.username FROM post_table p INNER JOIN user_table u ON p.userId = u.id")
    fun fetchAllPostsAndUsers(): LiveData<List<PostWithUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(vararg post: Post)

    @Query("DELETE from post_table")
    suspend fun deleteAll()
}