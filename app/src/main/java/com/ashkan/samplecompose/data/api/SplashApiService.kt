package com.ashkan.samplecompose.data.api

import com.ashkan.samplecompose.data.core.NetworkResponse
import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import retrofit2.http.GET

interface SplashApiService {

    @GET("users/1")
    suspend fun getAppConfig(): NetworkResponse<AppConfigModel>
}