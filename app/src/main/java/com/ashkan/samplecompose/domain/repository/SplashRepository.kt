package com.ashkan.samplecompose.domain.repository

import com.ashkan.samplecompose.domain.model.splash.AppConfigModel
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getAppConfig(): Flow<Result<AppConfigModel>>
}