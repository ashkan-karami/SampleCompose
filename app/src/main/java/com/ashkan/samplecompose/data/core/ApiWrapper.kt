package com.ashkan.samplecompose.data.core

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <Right> apiWrapper(request: suspend () -> Right): Flow<Result<Right>> {

    return flow {
        kotlin.runCatching {
            emit(Result.success(request.invoke()))
        }.onFailure { throwable ->
            emit(
                throwable.toNetworkExceptions()
            )
        }
    }.flowOn(IO)
}
