package com.ashkan.samplecompose.data.api

import com.ashkan.samplecompose.data.model.login.LoginResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {

    @GET("users/1")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String,
    ): LoginResponseModel
}