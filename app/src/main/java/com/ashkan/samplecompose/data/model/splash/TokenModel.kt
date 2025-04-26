package com.ashkan.samplecompose.data.model.splash

import com.squareup.moshi.Json

data class TokenModel(
    @Json(name = "access_token") val accessToken: String?,
    @Json(name = "refresh_token") val refreshToken: String?,
)
