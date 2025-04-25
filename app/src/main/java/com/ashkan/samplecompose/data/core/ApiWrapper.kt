package com.ashkan.samplecompose.data.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified Output> apiWrapper(noinline request: suspend () -> Output): Flow<Result<Output>> {

    return flow {
        runCatching {
            val response = request.invoke()
            if (response != null) {
                emit(Result.success(response))
            } else {
                emit(
                    Result.failure(
                        NetworkExceptions.ServerStatusFalse(
                            /*Must be replaced with message from server*/"Failed to connect to the server",
                            null
                        )
                    )
                )
            }
        }.onFailure { throwable ->
            emit(
                throwable.toNetworkExceptions()
            )
        }
    }
}
