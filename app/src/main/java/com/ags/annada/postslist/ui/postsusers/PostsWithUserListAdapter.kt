package com.ags.annada.postslist.ui.postsusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.postslist.databinding.ItemPostUserBinding
import com.ags.annada.postslist.room.entities.PostWithUser
import com.ags.annada.postslist.viewmodel.postsusers.PostsWithUsersListViewModel

class PostsWithUserListAdapter(
    private val viewModel: PostsWithUsersListViewModel
) : ListAdapter<PostWithUser, PostsWithUserListAdapter.ViewHolder>(PostsWithUserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(val binding: ItemPostUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PostsWithUsersListViewModel, item: PostWithUser) {
            binding.viewmodel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostUserBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class PostsWithUserDiffCallback : DiffUtil.ItemCallback<PostWithUser>() {
    override fun areItemsTheSame(oldItem: PostWithUser, newItem: PostWithUser): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PostWithUser, newItem: PostWithUser): Boolean {
        return oldItem == newItem
    }
}
