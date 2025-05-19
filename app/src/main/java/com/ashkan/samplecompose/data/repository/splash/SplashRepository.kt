package com.ashkan.samplecompose.data.repository.splash

import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getAppConfig(): Flow<Result<AppConfigModel>>
}