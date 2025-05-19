package com.ashkan.samplecompose.di

import android.content.Context
import com.ashkan.samplecompose.data.cache.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager =
        DataStoreManager(context)
}