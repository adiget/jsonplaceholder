package com.ags.annada.postslist.dagger.component

import com.ags.annada.postslist.dagger.module.NetworkModule
import com.ags.annada.postslist.repository.CommentRepository
import com.example.gabriel.jsonplaceholder.data.PostRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface RepositoryInjector{
    fun inject(postsRepository: PostRepository)
    fun inject(commentRepository: CommentRepository)

    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}