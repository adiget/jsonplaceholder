package com.example.gabriel.jsonplaceholder.di.module

import android.app.Application
import androidx.room.Room
import com.annada.android.sample.jsonposts.room.PostDatabase
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.Nullable
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PostDatabase? = PostDatabase.getInstance(app)
}