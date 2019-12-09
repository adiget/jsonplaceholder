package com.ags.annada.postslist.dagger.component

import com.ags.annada.postslist.dagger.module.NetworkModule
import com.ags.annada.postslist.viewmodel.comments.CommentsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(commentsListViewModel: CommentsListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}