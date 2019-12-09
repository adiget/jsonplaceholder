package com.ags.annada.postslist.viewmodel.comments

import androidx.lifecycle.MutableLiveData
import com.ags.annada.postslist.room.entities.Comment
import com.annada.android.sample.jsonposts.vm.BaseViewModel

class CommentViewModel : BaseViewModel() {
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    fun bind(commentObj: Comment) {
        name.value = commentObj.name
        email.value = commentObj.email
        body.value = commentObj.body
    }
}