package com.ashkan.samplecompose.data.core

const val defaultErrorMessage = "Failed to connect to the server!"

sealed class ApiState<T>{
    class Success<T>(val data: T) : ApiState<T>()
    class Failure<T>(val message: String) : ApiState<T>()
}

fun <T> Result<T>.toApiState(): ApiState<T> {
    if (this.isSuccess) {
        return this.getOrNull()?.let {
            ApiState.Success(it)
        }?: ApiState.Failure(defaultErrorMessage)
    } else {
        with(this.exceptionOrNull() as NetworkExceptions) {
            return if (this is NetworkExceptions.ServerStatusFalse) {
                ApiState.Failure(this.message?:defaultErrorMessage)
            } else {
                ApiState.Failure(defaultErrorMessage)
            }
        }
    }
}