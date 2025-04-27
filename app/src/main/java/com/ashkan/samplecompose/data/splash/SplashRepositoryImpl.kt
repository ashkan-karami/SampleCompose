package com.ashkan.samplecompose.data.splash

import com.ashkan.samplecompose.data.core.apiWrapper
import com.ashkan.samplecompose.data.splash.model.AppConfigResponseDto
import com.ashkan.samplecompose.data.splash.model.mapper
import com.ashkan.samplecompose.domain.model.splash.AppConfigModel
import com.ashkan.samplecompose.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow

class SplashRepositoryImpl(
    private val apiService: SplashApiService
): SplashRepository {

    override fun getAppConfig(): Flow<Result<AppConfigModel>> =
        apiWrapper(
            mapper = AppConfigResponseDto::mapper
        ) {
            apiService.getAppConfig()
        }
}