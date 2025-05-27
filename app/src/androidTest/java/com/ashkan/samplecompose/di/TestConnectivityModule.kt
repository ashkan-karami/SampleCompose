package com.ashkan.samplecompose.di

import android.content.Context
import com.ashkan.samplecompose.util.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ConnectivityModule::class]
)
object TestConnectivityModule {

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        ConnectivityManager(context)
}