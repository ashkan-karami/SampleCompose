package com.ashkan.samplecompose.data.network.api

import com.ashkan.samplecompose.data.network.model.splash.AppConfigModel
import retrofit2.http.GET

interface SplashApiService {

    @GET("users/1")
    suspend fun getAppConfig(): AppConfigModel
}