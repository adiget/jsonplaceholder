package com.ags.annada.postslist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ags.annada.postslist.room.daos.CommentDao
import com.ags.annada.postslist.room.daos.PostDao
import com.ags.annada.postslist.room.daos.UserDao
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.Post
import com.ags.annada.postslist.room.entities.User

@Database(entities = [Post::class, Comment::class, User::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun userDao(): UserDao
}