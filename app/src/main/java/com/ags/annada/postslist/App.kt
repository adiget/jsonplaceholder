package com.ags.annada.postslist

import android.app.Application
import com.ags.annada.postslist.dagger.component.DaggerAppComponent
import com.example.gabriel.jsonplaceholder.di.module.AppModule

class App : Application() {

        override fun onCreate() {
            super.onCreate()
            DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)
        }
    }