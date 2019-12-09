package com.ags.annada.postslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.ItemPostUserBinding
import com.ags.annada.postslist.room.entities.PostWithUser
import com.annada.android.sample.jsonposts.vm.PostWithUserViewModel

class PostsWithUserListAdapter(private val clickListener: PostListener) : RecyclerView.Adapter<PostsWithUserListAdapter.ViewHolder>() {
    private lateinit var postsWithUsers: List<PostWithUser>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_post_user,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsWithUserListAdapter.ViewHolder, position: Int) {
        holder.bind(postsWithUsers[position], clickListener)
    }

    fun setPostsWithUsers(postsWithUsers: List<PostWithUser>) {
        this.postsWithUsers = postsWithUsers
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (::postsWithUsers.isInitialized) postsWithUsers.size else 0
    }


    class ViewHolder(private val binding: ItemPostUserBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostWithUserViewModel()

        fun bind(postsWithUsers: PostWithUser, clickListener: PostListener) {
            viewModel.bind(postsWithUsers)
            binding.viewModel = viewModel
            binding.clickListener = clickListener
        }
    }
}

class PostListener(val clickListener: (postId: Int) -> Unit) {
    fun onClick(id: Int) = clickListener(id)
}