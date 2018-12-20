package com.tide.barsaround.di.app

import com.tide.barsaround.data.remote.network.NearByApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideNearByApi(retrofit: Retrofit) = retrofit.create(NearByApi::class.java)
}
