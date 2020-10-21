package com.ags.annada.postslist.viewmodel.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ags.annada.postslist.room.entities.Comment

class CommentViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    fun bind(commentObj: Comment) {
        name.value = commentObj.name
        email.value = commentObj.email
        body.value = commentObj.body
    }
}