package com.ags.annada.postslist.ui.postsusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.PostsUsersFragmentBinding
import com.ags.annada.postslist.utils.EventObserver
import com.ags.annada.postslist.viewmodel.postsusers.PostsWithUsersListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsWithUserFragment : Fragment() {

    private val viewModel by viewModels<PostsWithUsersListViewModel>()
    private lateinit var adapter: PostsWithUserListAdapter

    private lateinit var binding: PostsUsersFragmentBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = PostsUsersFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        setupNavigation()
        setupAdapter()
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun setupAdapter() {
        val viewModel = binding.viewmodel

        if (viewModel != null) {
            adapter = PostsWithUserListAdapter(viewModel)
            binding.postsList.adapter = adapter
        } else {
            Log.d("setupAdapter()", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupNavigation() {
        viewModel.selectItemEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("SELECTED ID", "selected=${it}")
            openComments(it)
        })
    }

    private fun openComments(postId: Int) {
        val action =
            PostsWithUserFragmentDirections.actionPostWithUserFragmentToCommentsFragment(postId)
        findNavController().navigate(action)
    }
}