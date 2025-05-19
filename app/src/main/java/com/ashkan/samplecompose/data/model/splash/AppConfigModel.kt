package com.ashkan.samplecompose.data.model.splash

import com.squareup.moshi.Json

data class AppConfigModel(
    @Json(name = "updateAvailable")
    val updateAvailable: Boolean?,
    @Json(name = "newVersionCode")
    val newVersionCode: String?,
)