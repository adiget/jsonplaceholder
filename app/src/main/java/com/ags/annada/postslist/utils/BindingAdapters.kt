package com.annada.android.sample.jsonposts.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.ui.comments.CommentsListAdapter
import com.ags.annada.postslist.ui.postsusers.PostsWithUserListAdapter

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(
            parentActivity,
            Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("app:items")
fun setPostWithUsers(listView: RecyclerView, items: List<PostWithUser>?) {
    items?.let {
        (listView.adapter as PostsWithUserListAdapter).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setComments(listView: RecyclerView, items: List<Comment>?) {
    items?.let {
        (listView.adapter as CommentsListAdapter).submitList(items)
    }
}

@BindingAdapter("initials")
fun bindUserInitials(textView: TextView, name: String) {
    val firstLetter = name.first()
    val space = name.lastIndexOf(" ")
    val lastName = space.plus(1).let { name.substring(it) }
    val lastLetter = lastName.first()
    val sb = StringBuilder()
    val initials = sb.append(firstLetter).append(lastLetter)

    textView.text = initials
}