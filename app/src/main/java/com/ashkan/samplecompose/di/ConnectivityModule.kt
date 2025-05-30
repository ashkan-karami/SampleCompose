package com.ashkan.samplecompose.di

import android.content.Context
import com.ashkan.samplecompose.util.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Provides
    fun provideConnectionState(@ApplicationContext context: Context): ConnectivityManager =
        ConnectivityManager(context)
}