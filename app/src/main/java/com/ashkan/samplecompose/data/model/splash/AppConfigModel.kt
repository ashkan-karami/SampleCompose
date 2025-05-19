package com.ashkan.samplecompose.data.model.splash

import com.squareup.moshi.Json

data class AppConfigModel(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "profile_photo")
    val profilePhoto: String?,
    @Json(name = "wallet_balance")
    val walletBalance: Float?
)