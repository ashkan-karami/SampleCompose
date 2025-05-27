package com.ashkan.samplecompose.di

import com.ashkan.samplecompose.data.network.api.LoginApiService
import com.ashkan.samplecompose.data.network.api.PostsApiService
import com.ashkan.samplecompose.data.network.api.SplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideSplashApiService(retrofit: Retrofit): SplashApiService =
        retrofit.create(SplashApiService::class.java)

    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService =
        retrofit.create(LoginApiService::class.java)

    @Provides
    fun providePostApiService(retrofit: Retrofit): PostsApiService =
        retrofit.create(PostsApiService::class.java)
}