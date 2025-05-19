package com.ashkan.samplecompose.di

import com.ashkan.samplecompose.data.api.SplashApiService
import com.ashkan.samplecompose.data.repository.splash.SplashRepositoryImpl
import com.ashkan.samplecompose.data.repository.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideSplashRepository(apiService: SplashApiService): SplashRepository =
        SplashRepositoryImpl(apiService)
}