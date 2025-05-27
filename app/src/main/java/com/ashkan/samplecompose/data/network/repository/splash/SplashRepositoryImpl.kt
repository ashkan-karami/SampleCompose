package com.ashkan.samplecompose.data.network.repository.splash

import com.ashkan.samplecompose.data.network.core.apiWrapper
import com.ashkan.samplecompose.data.network.api.SplashApiService
import com.ashkan.samplecompose.data.network.model.splash.AppConfigModel
import kotlinx.coroutines.flow.Flow

class SplashRepositoryImpl(
    private val apiService: SplashApiService
) : SplashRepository {

    override suspend fun getAppConfig(): Flow<Result<AppConfigModel>> =
        apiWrapper {
            apiService.getAppConfig()
        }
}