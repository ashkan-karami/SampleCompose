package com.ashkan.samplecompose.data.network.core

data class NetworkResponse<out T> (
    val data: T,
    val message: String,
    val status: Boolean,
    val validationErrors: Any?
)