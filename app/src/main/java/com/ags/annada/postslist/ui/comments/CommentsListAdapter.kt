package com.ags.annada.postslist.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.ItemCommentBinding
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.viewmodel.comments.CommentViewModel

class CommentsListAdapter (): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {
    private lateinit var commentsList: List<Comment>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_comment,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsListAdapter.ViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    fun updateCommentsList(commentsList: List<Comment>) {
        this.commentsList = commentsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (::commentsList.isInitialized) commentsList.size else 0
    }

    class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = CommentViewModel()

        fun bind(comment: Comment) {
            viewModel.bind(comment)

            binding.viewModel = viewModel
        }
    }
}

