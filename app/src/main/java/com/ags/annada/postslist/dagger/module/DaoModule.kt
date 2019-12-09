package com.example.gabriel.jsonplaceholder.di.module

import com.annada.android.sample.jsonposts.room.PostDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {
    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase) = database.postDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: PostDatabase) = database.commentDao()

    @Provides
    @Singleton
    fun provideUserDao(database: PostDatabase) = database.commentDao()
}