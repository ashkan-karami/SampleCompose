package com.ashkan.samplecompose.di

import com.ashkan.samplecompose.data.api.SplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): SplashApiService =
        retrofit.create(SplashApiService::class.java)
}