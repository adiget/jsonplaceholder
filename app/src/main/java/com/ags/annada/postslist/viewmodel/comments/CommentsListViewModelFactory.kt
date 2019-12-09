package com.ags.annada.postslist.viewmodel.comments

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.annada.android.sample.jsonposts.room.PostDatabase

class CommentsListViewModelFactory(private val activity: AppCompatActivity, private val postId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, PostDatabase::class.java, "post")
                    .build()
            @Suppress("UNCHECKED_CAST")
            return CommentsListViewModel(db.commentDao(), postId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}