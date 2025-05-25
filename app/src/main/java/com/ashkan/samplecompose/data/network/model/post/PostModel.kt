package com.ashkan.samplecompose.data.network.model.post

import com.squareup.moshi.Json

data class PostModel(
    @Json(name = "userId")
    val userId: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "body")
    val body: String?,
)