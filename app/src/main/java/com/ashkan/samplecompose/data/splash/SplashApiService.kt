package com.ashkan.samplecompose.data.splash

import com.ashkan.samplecompose.data.core.NetworkResponse
import com.ashkan.samplecompose.data.splash.model.AppConfigResponseDto
import retrofit2.http.GET

interface SplashApiService {

    @GET("/V2/Main/AppConfig")
    suspend fun getAppConfig(): NetworkResponse<AppConfigResponseDto?>
}