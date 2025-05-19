package com.ashkan.samplecompose.data.core

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <Right> apiWrapper(request: suspend () -> NetworkResponse<Right>): Flow<Result<Right>> {

    return flow {
        kotlin.runCatching {
            val response = request.invoke()
            if (response.status) {
                emit(Result.success(response.data))
            } else {
                emit(Result.failure(NetworkExceptions.ServerStatusFalse(response.message, null)))
            }
        }.onFailure { throwable ->
            emit(
                throwable.toNetworkExceptions<Right>()
            )
        }
    }.flowOn(IO)
}
