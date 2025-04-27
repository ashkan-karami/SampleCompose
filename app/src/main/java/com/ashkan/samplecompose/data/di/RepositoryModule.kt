package com.ashkan.samplecompose.data.di

import com.ashkan.samplecompose.data.splash.SplashApiService
import com.ashkan.samplecompose.data.splash.SplashRepositoryImpl
import com.ashkan.samplecompose.domain.repository.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideSplashRepository(apiService: SplashApiService): SplashRepository =
        SplashRepositoryImpl(apiService)
}