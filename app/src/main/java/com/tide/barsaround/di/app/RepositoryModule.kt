package com.tide.barsaround.di.app

import com.tide.barsaround.data.remote.NearByRemoteDataSource
import com.tide.barsaround.repository.NearbyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNearByRepository(nearByRemoteDataSource: NearByRemoteDataSource) = NearbyRepository(nearByRemoteDataSource)
}
