package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import com.ashkan.samplecompose.data.repository.splash.SplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSplashRepository : SplashRepository {

    override fun getAppConfig(): Flow<Result<AppConfigModel>> =
        flow {
            emit(
                Result.success(AppConfigModel(false, ""))
            )
        }
}