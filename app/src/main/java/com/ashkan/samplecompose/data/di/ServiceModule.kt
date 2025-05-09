package com.ashkan.samplecompose.data.di

import com.ashkan.samplecompose.data.splash.SplashApiService
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