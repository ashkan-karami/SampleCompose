package com.ashkan.samplecompose.data.splash.model

import com.squareup.moshi.Json

data class AppConfigResponseDto(
    @Json(name = "title")
    val title: String?,
    @Json(name = "profile_photo")
    val profilePhoto: String?,
    @Json(name = "wallet_balance")
    val walletBalance: Float?
)