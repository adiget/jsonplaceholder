package com.annada.android.sample.jsonposts.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModel
import com.annada.android.sample.jsonposts.room.PostDatabase


class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PostsWithUsersListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, PostDatabase::class.java, "post")
                    .build()
            @Suppress("UNCHECKED_CAST")
            return PostsWithUsersListViewModel(db.postDao(), db.userDao()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}