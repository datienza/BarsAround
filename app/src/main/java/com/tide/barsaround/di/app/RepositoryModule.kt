package com.tide.barsaround.di.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePlacesRepository(
        rocketsRemoteDataSource: PlacesRemoteDataSource,
    ) = PlacesRepository(placesRemoteDataSource, connectivity)
}
