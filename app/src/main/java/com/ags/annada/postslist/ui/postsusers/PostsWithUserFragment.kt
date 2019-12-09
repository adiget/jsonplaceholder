package com.ags.annada.postslist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.PostsUsersFragmentBinding
import com.annada.android.sample.jsonposts.vm.PostsWithUsersListViewModel
import com.annada.android.sample.jsonposts.vm.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class PostsWithUserFragment : Fragment() {

    companion object {
        fun newInstance() = PostsWithUserFragment()
    }

    internal lateinit var callback: OnPostSelectedListener
    private lateinit var viewModel: PostsWithUsersListViewModel
    private lateinit var binding: PostsUsersFragmentBinding
    private var errorSnackbar: Snackbar? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    fun setOnPostSelectedListener(callback: OnPostSelectedListener) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))
            .get(PostsWithUsersListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        viewModel.selectItemEvent.observe(this, Observer {
            if (it != null) {
                Log.d("SELECTED ID", "selected=${it}");
                callback.onPostSelected(it)
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.posts_users_fragment, container, false)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView = binding.postsList

        recyclerView.layoutManager = linearLayoutManager

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        layoutManager = (recyclerView.layoutManager as LinearLayoutManager?)!!

        binding.viewModel = viewModel

        viewModel.allPostsWithUsers.observe(this, Observer { postsWithUsers ->
            // Update the cached copy of the posts in the adapter.
            postsWithUsers?.let { viewModel.postWithUsersListAdapter.setPostsWithUsers(it) }
        })


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

    interface OnPostSelectedListener {
        fun onPostSelected(postId: Int)
    }
}