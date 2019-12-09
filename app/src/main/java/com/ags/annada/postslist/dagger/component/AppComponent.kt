package com.ags.annada.postslist.dagger.component

import com.ags.annada.postslist.App
import com.example.gabriel.jsonplaceholder.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun appModule(appModule: AppModule): Builder
    }
}