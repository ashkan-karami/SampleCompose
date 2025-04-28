package com.ashkan.samplecompose.data.splash

import com.ashkan.samplecompose.data.core.NetworkResponse
import com.ashkan.samplecompose.data.splash.model.AppConfigResponseDto
import retrofit2.http.GET

interface SplashApiService {

    @GET("users/1")
    suspend fun getAppConfig(): NetworkResponse<AppConfigResponseDto>
}