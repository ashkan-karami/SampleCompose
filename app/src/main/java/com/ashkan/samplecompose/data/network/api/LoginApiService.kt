package com.ashkan.samplecompose.data.network.api

import com.ashkan.samplecompose.data.network.model.login.LoginResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {

    @GET("users/1")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String,
    ): LoginResponseModel
}