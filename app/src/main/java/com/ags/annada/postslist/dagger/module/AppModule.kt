package com.ags.annada.postslist.dagger.module

import android.content.Context
import androidx.room.Room
import com.ags.annada.postslist.room.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PostDatabase::class.java,
            "Posts.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}