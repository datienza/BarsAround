package com.tide.barsaround.di.app

import com.tide.barsaround.BarsAroundApplication
import com.tide.barsaround.di.activity.ActivityBindingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivityBindingModule::class,
    AppModule::class,
    NetModule::class,
    RepositoryModule::class,
    RemoteDataModule::class,
    ApiModule::class
])
interface AppComponent {
    fun inject(application: BarsAroundApplication): BarsAroundApplication
}
