package com.ashkan.samplecompose.data.splash.model

import com.ashkan.samplecompose.domain.model.splash.AppConfigModel
import com.squareup.moshi.Json

data class AppConfigResponseDto(
    @Json(name = "title")
    val title: String?,
    @Json(name = "profile_photo")
    val profilePhoto: String?,
    @Json(name = "wallet_balance")
    val walletBalance: Float?
)

internal fun AppConfigResponseDto.mapper(): AppConfigModel =
    AppConfigModel(
        title = title?:"Unknown!",
        profilePhoto = profilePhoto,
        walletBalance = walletBalance?:0.0F
    )