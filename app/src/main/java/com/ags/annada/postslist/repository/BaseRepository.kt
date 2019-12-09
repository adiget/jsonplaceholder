package com.ags.annada.postslist.repository

import com.ags.annada.postslist.dagger.component.DaggerRepositoryInjector
import com.ags.annada.postslist.dagger.component.RepositoryInjector
import com.ags.annada.postslist.dagger.module.NetworkModule
import com.example.gabriel.jsonplaceholder.data.PostRepository

abstract class BaseRepository {
    private val injector: RepositoryInjector = DaggerRepositoryInjector
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
            is PostRepository -> injector.inject(this)
            is CommentRepository -> injector.inject(this)
        }
    }
}