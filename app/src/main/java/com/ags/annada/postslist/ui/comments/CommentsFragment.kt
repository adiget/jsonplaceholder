package com.ags.annada.postslist.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.FragmentCommentsBinding
import com.ags.annada.postslist.databinding.PostsUsersFragmentBinding
import com.ags.annada.postslist.room.entities.Comment
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModel
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModelFactory
import com.annada.android.sample.jsonposts.vm.PostsWithUsersListViewModel
import com.annada.android.sample.jsonposts.vm.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CommentsFragment : Fragment() {
    companion object {
        val ARG_POST_ID = "ARG_POST_ID"
    }

    private lateinit var viewModel: CommentsListViewModel
    private lateinit var binding: FragmentCommentsBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_comments, container, false
        )

        val postId = arguments?.get(ARG_POST_ID)

        viewModel = ViewModelProviders.of(
            this,
            CommentsListViewModelFactory(activity as AppCompatActivity, postId as Int)
        ).get(CommentsListViewModel::class.java)

        viewModel.allComments.observe(this, Observer {
            viewModel.commentsListAdapter.updateCommentsList(it as List<Comment>) })


        val recyclerView  = binding.commentsList
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        binding.viewModel = viewModel

        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

}