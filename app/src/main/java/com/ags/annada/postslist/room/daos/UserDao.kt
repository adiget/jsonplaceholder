package com.ags.annada.postslist.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.User


@Dao
interface UserDao {

    @get:Query("SELECT * from user_table")
    val all: LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg user: User)

    @Query("DELETE from user_table")
    suspend fun deleteAll()
}