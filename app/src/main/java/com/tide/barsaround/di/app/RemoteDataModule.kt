package com.tide.barsaround.di.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun providePlacesRemoteDataSource(rocketsApi: PlacesApi) =
        PlacesRemoteDataSource(rocketsApi)
}
