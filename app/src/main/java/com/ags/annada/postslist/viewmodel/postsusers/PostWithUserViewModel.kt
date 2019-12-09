package com.annada.android.sample.jsonposts.vm

import androidx.lifecycle.MutableLiveData
import com.ags.annada.postslist.room.entities.PostWithUser

class PostWithUserViewModel : BaseViewModel() {
    val id = MutableLiveData<Int>()
    val name = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    fun bind(postWithUser: PostWithUser) {
        id.value = postWithUser.id
        name.value = postWithUser.name
        username.value = postWithUser.username
        title.value = postWithUser.title
        body.value = postWithUser.body
    }
}