package com.ashkan.samplecompose.data.network.repository.splash

import com.ashkan.samplecompose.data.network.model.splash.AppConfigModel
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    suspend fun getAppConfig(): Flow<Result<AppConfigModel>>
}