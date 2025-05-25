package com.ashkan.samplecompose.data.network.core

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <Right> apiWrapper(request: suspend () -> Right): Flow<Result<Right>> {

    return flow {
        runCatching {
            emit(Result.success(request.invoke()))
        }.onFailure { throwable ->
            emit(
                throwable.toNetworkExceptions()
            )
        }
    }.flowOn(IO)
}
