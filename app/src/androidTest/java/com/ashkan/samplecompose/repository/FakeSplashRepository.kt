package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.network.model.splash.AppConfigModel
import com.ashkan.samplecompose.data.network.repository.splash.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSplashRepository : SplashRepository {

    override suspend fun getAppConfig(): Flow<Result<AppConfigModel>> =
        flow {
            emit(
                Result.success(AppConfigModel(false, ""))
            )
        }
}