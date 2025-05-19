package com.ashkan.samplecompose.data.repository.splash

import com.ashkan.samplecompose.data.core.apiWrapper
import com.ashkan.samplecompose.data.api.SplashApiService
import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import kotlinx.coroutines.flow.Flow

class SplashRepositoryImpl(
    private val apiService: SplashApiService
) : SplashRepository {

    override fun getAppConfig(): Flow<Result<AppConfigModel>> =
        apiWrapper {
            apiService.getAppConfig()
        }
}