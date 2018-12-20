package com.tide.barsaround.di.app

import com.tide.barsaround.data.remote.NearByRemoteDataSource
import com.tide.barsaround.data.remote.network.NearByApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideNearByRemoteDataSource(rocketsApi: NearByApi) = NearByRemoteDataSource(rocketsApi)
}
