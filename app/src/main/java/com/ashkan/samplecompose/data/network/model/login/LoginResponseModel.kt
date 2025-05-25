package com.ashkan.samplecompose.data.network.model.login

import com.squareup.moshi.Json

data class LoginResponseModel(
    @Json(name = "token")
    val token: String?,
    @Json(name = "refreshToken")
    val refreshToken: String?,
)