package com.ashkan.samplecompose

import android.content.Context
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.di.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
object TestDataStoreModule {

    @Provides
    @Singleton
    fun provideTestPreferencesDataStore(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}