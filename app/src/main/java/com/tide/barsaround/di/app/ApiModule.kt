package com.tide.barsaround.di.app

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun providePlacesApi(retrofit: Retrofit) = retrofit.create(PlacesApi::class.java)
}
