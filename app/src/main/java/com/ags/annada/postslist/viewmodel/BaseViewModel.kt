package com.annada.android.sample.jsonposts.vm

import androidx.lifecycle.ViewModel
import com.ags.annada.postslist.dagger.component.DaggerViewModelInjector
import com.ags.annada.postslist.dagger.component.ViewModelInjector
import com.ags.annada.postslist.dagger.module.NetworkModule
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModel


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is CommentsListViewModel -> injector.inject(this)
        }
    }
}