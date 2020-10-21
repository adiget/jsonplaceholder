package com.ags.annada.postslist.ui.comments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ags.annada.postslist.R
import com.ags.annada.postslist.databinding.FragmentCommentsBinding
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    companion object {
        val ARG_POST_ID = "ARG_POST_ID"
    }

    private val viewModel by viewModels<CommentsListViewModel>()
    private lateinit var adapter: CommentsListAdapter
    private lateinit var binding: FragmentCommentsBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = FragmentCommentsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

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
            adapter = CommentsListAdapter()
            binding.commentsList.adapter = adapter
        } else {
            Log.d("setupAdapter()", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}