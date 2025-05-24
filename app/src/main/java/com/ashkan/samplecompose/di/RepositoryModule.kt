package com.ashkan.samplecompose.di

import com.ashkan.samplecompose.data.api.LoginApiService
import com.ashkan.samplecompose.data.api.PostsApiService
import com.ashkan.samplecompose.data.api.SplashApiService
import com.ashkan.samplecompose.data.repository.home.HomeRepository
import com.ashkan.samplecompose.data.repository.home.HomeRepositoryImpl
import com.ashkan.samplecompose.data.repository.login.LoginRepository
import com.ashkan.samplecompose.data.repository.login.LoginRepositoryImpl
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

    @Provides
    fun provideLoginRepository(apiService: LoginApiService): LoginRepository =
        LoginRepositoryImpl(apiService)

    @Provides
    fun provideHomeRepository(apiService: PostsApiService): HomeRepository =
        HomeRepositoryImpl(apiService)
}