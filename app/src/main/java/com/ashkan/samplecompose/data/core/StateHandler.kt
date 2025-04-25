package com.ashkan.samplecompose.data.core

sealed class ApiState<T>{
    class Success<T>(val data: T?) : ApiState<T>()
    class Failure<T>(val message: String?) : ApiState<T>()
}

fun <T> Result<T>.toApiState(): ApiState<T> {
    if (this.isSuccess) {
        return ApiState.Success(this.getOrNull())
    } else {
        with(this.exceptionOrNull() as NetworkExceptions) {
            return if (this is NetworkExceptions.ServerStatusFalse) {
                ApiState.Failure(this.message)
            } else {
                ApiState.Failure("Failed to connect to the server!")
            }
        }
    }
}