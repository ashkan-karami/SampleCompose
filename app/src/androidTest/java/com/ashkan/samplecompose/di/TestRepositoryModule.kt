package com.ashkan.samplecompose.di

import dagger.Module
import dagger.Provides
import com.ashkan.samplecompose.data.network.repository.home.HomeRepository
import com.ashkan.samplecompose.data.network.repository.login.LoginRepository
import com.ashkan.samplecompose.data.network.repository.splash.SplashRepository
import com.ashkan.samplecompose.repository.FakeHomeRepository
import com.ashkan.samplecompose.repository.FakeLoginRepository
import com.ashkan.samplecompose.repository.FakeSplashRepository
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {

    @Provides
    fun provideFakeSplashRepository(): SplashRepository {
        return FakeSplashRepository()
    }

    @Provides
    fun provideFakeLoginRepository(): LoginRepository {
        return FakeLoginRepository()
    }

    @Provides
    fun provideFakeHomeRepository(): HomeRepository {
        return FakeHomeRepository()
    }
}